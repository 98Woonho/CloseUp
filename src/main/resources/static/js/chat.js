const chatDialog = document.getElementById('chatDialog');
const chatIcon = document.getElementById('chatIcon');
const chatListSec = document.getElementById('chatListSec');
const toggleChatListSecIcons = document.querySelectorAll('.toggle-chat-list-sec-icon');
const infoSec = document.getElementById('infoSec');
const chatList = document.getElementById('chatList');
const chatMsgSec = document.getElementById('chatMsgSec');
const topProfileName = document.getElementById('topProfileName');
const nonSelecteds = document.querySelectorAll('.non-selected');
const selecteds = document.querySelectorAll('.selected');
const profileImg = document.querySelector('#profileImg > img');
const categoryBtn = document.querySelectorAll('#category > button');
const mapAddr = document.getElementById('mapAddr');
const chatInput = document.getElementById('chatInput');
const topProfileContainer = document.getElementById('topProfileContainer');
const closeChatDialogIcon = document.getElementById('closeChatDialogIcon');
const profileNickname = document.getElementById('profileNickname');
const searchChat = document.getElementById('searchChat');

let userId; // 현재 로그인 한 유저의 아이디
let role; // 현재 로그인 한 유저의 역할
let name; // 현재 로그인 한 유저의 이름
let selectedChatRoomId; // 선택한 채팅방의 id
let chatRoomDtoList; // 채팅방 리스트
let stomp;

// 현재 로그인 한 유저 조회
axios.get('/user')
    .then(res => {
        userId = res.data.id;
        role = res.data.role;
        name = res.data.name;
    })
    .catch(err => {
        console.log(err);
    })

if (window.location.pathname === '/myPage/chats') {
    // 현재 경로가 /myPage/chats인 경우 우측 하단 챗 아이콘 숨기기
    chatIcon.style.display = 'none';
} else {
    // 고정 아이콘 - 채팅 아이콘 클릭 event
    chatIcon.addEventListener('click', function () {
        chatDialog.classList.toggle('visible');
    })

    // 채팅 dialog 닫기 아이콘 클릭 event
    closeChatDialogIcon.addEventListener('click', function (e) {
        e.preventDefault();

        chatDialog.classList.remove('visible');
    })

    // 채팅창 상단 프로필 클릭 event

    topProfileContainer.addEventListener('click', function (e) {
        e.preventDefault();
        if (role === 'ROLE_USER') {
            infoSec.classList.toggle('visible');
        }
    })

    // 채팅 리스트 아이콘 토글
    toggleChatListSecIcons.forEach(toggleChatListSecIcon => {
        toggleChatListSecIcon.addEventListener('click', function () {
            chatListSec.classList.toggle('visible');
        })
    })
}

// 채팅 리스트 가져오기
axios.get('/chat/list')
    .then(res => {
        if (res.data !== '') {
            chatRoomDtoList = res.data;
            const ul = document.createElement('ul');

            // sockJs setting
            const sockJs = new SockJS("/stomp/chat");
            stomp = Stomp.over(sockJs);

            // 하나의 WebSocket 연결을 사용하여 Stomp 연결 설정
            stomp.connect({}, function () {

                chatRoomDtoList.forEach(chatRoomDto => {
                    const li = new DOMParser().parseFromString(`
                        <li class="chat-li" data-id="${chatRoomDto.id}" onClick="clickChatLi(this)">
                            <img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" alt="">
                            <div class="spring">
                                <div class="nickname"><b>${role === 'ROLE_USER' ? chatRoomDto.expertNickname : chatRoomDto.userName}</b></div>
                                <div class="content">${chatRoomDto.lastChatMessage === null ? '' : chatRoomDto.lastChatMessage}</div>
                            </div>
                            <div class="message-count-container">
                                <div class="message-count">${role === 'ROLE_USER' ? chatRoomDto.notReadExpertMessageCount : chatRoomDto.notReadUserMessageCount}</div>
                            </div>
                        </li>
                    `, 'text/html').querySelector('.chat-li');

                    ul.appendChild(li);

                    const messageCount = li.querySelector('.message-count');

                    // 읽지 않은 메세지가 있으면 count 표시 및 스타일 적용
                    if (messageCount.innerText !== '0') {
                        messageCount.classList.add('visible');
                    }

                    // 읽지 않은 메세지가 없으면 공백 및 스타일 적용 x
                    if (messageCount.innerText === '0') {
                        messageCount.innerText = '';
                    }


                    // 각 채팅방에 대한 subscribe 설정
                    stomp.subscribe("/sub/chat/room/" + chatRoomDto.id, function (messageInfo) {
                        const chatLi = chatList.querySelector(`.chat-li[data-id="${chatRoomDto.id}"]`);
                        const content = chatLi.querySelector('.content');
                        const message = JSON.parse(messageInfo.body);
                        let messageCount = chatLi.querySelector('.message-count');

                        // 메세지가 전송 되었을 때, 해당 채팅방의 읽지 않은 메세지 개수를 가져와서 count 적용
                        axios.get(`/chat/room/${chatRoomDto.id}`)
                            .then(res => {
                                const notReadExpertMessageCount = res.data.notReadExpertMessageCount;
                                const notReadUserMessageCount = res.data.notReadUserMessageCount;

                                // 만약 메세지가 온 채팅방을 선택 했으면
                                if (chatLi.classList.contains('choice')) {
                                    // 카운트 표시 x
                                    messageCount.innerText = '';
                                } else {
                                    // 역할에 따라 count 증가
                                    messageCount.innerText = role === 'ROLE_USER' ? notReadExpertMessageCount + 1 : notReadUserMessageCount + 1;

                                    // 안 읽은 메세지 수 DB 저장
                                    axios.patch('/chat/room', {
                                        id: message.chatRoomId,
                                        action: 'increment'
                                    })
                                }

                                // 메세지가 전송되고 카운트가 증가 했으면, 스타일 적용
                                if (messageCount.innerText !== '') {
                                    messageCount.classList.add('visible');
                                }
                            })
                            .catch(err => {
                                console.log(err);
                            })

                        // 양방향 통신이라 메세지를 한 번 보내면 subscribe가 2번 실행 되어 메세지가 2번 저장되는 것을 방지
                        if (message.userId === userId) {
                            // DB에 메세지 저장
                            axios.post('/chat/message', {
                                chatRoomId: message.chatRoomId,
                                content: message.content,
                                userId: message.userId
                            })
                                .then(res => {
                                })
                                .catch(err => {
                                    console.log(err);
                                });
                        }

                        if (chatLi.classList.contains('choice')) {
                            const chat = new DOMParser().parseFromString(`
                            <div class="chat ${message.userId === userId ? 'user1' : 'user2'}">
                                <div class="chat-msg">${message.content}</div>
                            </div>`, "text/html").querySelector('.chat');

                            chatMsgSec.appendChild(chat);
                        }

                        // 채팅 목록에 가장 마지막 content를 표시
                        content.innerText = message.content;

                        chatMsgSec.append('');
                        chatInput.value = '';

                        // 채팅이 올 때마다 스크롤을 맨 아래로 내림
                        chatMsgSec.scrollTop = chatMsgSec.scrollHeight;
                    });
                });
                chatList.appendChild(ul);
            });
        }
    })
    .catch(err => {
        console.log(err);
    });


searchChat.addEventListener('input', function (e) {
    chatList.innerHTML = '';

    const newChatRoomDtoList = e.target.value === ''
        ? chatRoomDtoList
        : chatRoomDtoList.filter(chatRoomDto => {
            const name = role === 'ROLE_USER' ? chatRoomDto.expertNickname : chatRoomDto.userName;
            return name.includes(e.target.value);
        });


    const ul = document.createElement('ul');

    newChatRoomDtoList.forEach(chatRoomDto => {
        const li = new DOMParser().parseFromString(`
                    <li class="chat-li" data-id="${chatRoomDto.id}"
                    onClick="clickChatLi(this)">
                        <img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" alt="">
                        <div class="spring">
                            <div class="nickname"><b>${role === 'ROLE_USER' ? chatRoomDto.expertNickname : chatRoomDto.userName}</b></div>
                            <div class="content"><p>${chatRoomDto.lastChatMessage === null ? '' : chatRoomDto.lastChatMessage}</p></div>
                        </div>
                        <div class="message-count-container">
                            <div class="message-count">${role === 'ROLE_USER' ? chatRoomDto.notReadExpertMessageCount : chatRoomDto.notReadUserMessageCount}</div>
                        </div>
                    </li>
                `, 'text/html').querySelector('.chat-li');

        ul.appendChild(li);

        const messageCount = li.querySelector('.message-count');

        if (messageCount.innerText !== '0') {
            messageCount.classList.add('visible');
        }

        if (messageCount.innerText === '0') {
            messageCount.innerText = '';
        }
    })

    chatList.appendChild(ul);
})

// 채팅 목록에서 채팅을 클릭 했을 때의 function
function clickChatLi(chat) {
    const chatLies = document.querySelectorAll('.chat-li');
    let selectedNickname = chat.querySelector('.nickname').innerText;
    selectedChatRoomId = chat.dataset.id;

    chatListSec.classList.toggle('visible');


    // 어떤 채팅방을 선택했는지에 대한 style을 위한 class 추가/제거
    chatLies.forEach(chatLi => {
        if (chatLi === chat) {
            chatLi.classList.add('choice');
        } else {
            chatLi.classList.remove('choice');
        }
    })

    // 채팅방을 선택 했을 때, 읽지 않은 메세지 카운트 초기화
    chat.querySelector('.message-count').innerText = '';
    chat.querySelector('.message-count').classList.remove('visible');

    // chat_room DB에 읽지 않은 메세지 0으로 초기화
    axios.patch('/chat/room', {
        id: chat.dataset.id,
        action: 'reset'
    })

    // 선택된 채팅방의 채팅목록 가져오기
    axios.get(`/chat/messages/${selectedChatRoomId}`)
        .then(res => {
            const chatMessageDtoList = res.data;

            chatMessageDtoList.forEach(chatMessageDto => {
                const message = new DOMParser().parseFromString(`
                        <div class="chat ${userId === chatMessageDto.userId ? 'user1' : 'user2'}">
                            <div class="chat-msg">${chatMessageDto.content}</div>
                        </div>
                    `, 'text/html').querySelector('.chat');

                chatMsgSec.appendChild(message);
            })

            chatMsgSec.scrollTop = chatMsgSec.scrollHeight;
        })
        .catch(err => {
            console.log(err);
        })

    nonSelecteds.forEach(nonSelected => {
        nonSelected.style.display = 'none';
    })

    selecteds.forEach(selected => {
        selected.style.display = 'unset';
    })

    if (role === 'ROLE_USER') {
        getExpertInfo(selectedNickname);
    }

    if (role === 'ROLE_EXPERT') {
        topProfileName.innerText = selectedNickname;
    }

    chatMsgSec.innerHTML = '';
    chatInput.value = '';
}


// 채팅방 메세지 input keydown event 함수
function keydownEvent(e) {
    if (e.keyCode === 13) {
        if (chatInput.value === '') {
            alert('메세지를 입력해 주세요.');
            return;
        }

        e.preventDefault();

        // stomp send
        stomp.send('/pub/chat/message', {}, JSON.stringify({
            chatRoomId: selectedChatRoomId,
            content: chatInput.value,
            userId: userId
        }));
    }
}

// 전문 분야 및 정보 가져오는 함수
function getExpertInfo(selectedExpertNickname) {
    // 전문가 정보 가져오기
    axios.get(`/expert/${selectedExpertNickname}`)
        .then(res => {
            topProfileName.innerText = res.data.nickname;
            mapAddr.innerText = `(${res.data.zipcode}) ${res.data.address} ${res.data.addressDetail}`;
            profileNickname.innerText = res.data.nickname;
            /** TODO
             * 전문가 등록 및 수정에서 profileImg 구현되면 profileImg 넣어야 함.
             */
        })
        .catch(err => {
            console.log(err);
        })

    // 전문가 상세 정보 중 expertise(전문지식) 가져오기
    axios.get(`/expert/detail/${selectedExpertNickname}/expertise`)
        .then(res => {
            categoryBtn.forEach((categoryBtn, index) => {
                categoryBtn.innerText = res.data[index].information;
            })
        })
        .catch(err => {
            console.log(err);
        })
}


