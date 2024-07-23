const chatDialog = document.getElementById('chatDialog');
const chatIcon = document.getElementById('chatIcon');
const chatListSec = document.getElementById('chatListSec');
const toggleChatListSecIcons = document.querySelectorAll('.toggle-chat-list-sec-icon');
const infoSec = document.getElementById('infoSec');
const chatList = document.getElementById('chatList');
const chatLies = document.querySelectorAll('.chat-li');
const chatMsgSec = document.getElementById('chatMsgSec');
const chatViewSec = document.getElementById('chatViewSec');
const chatViewTop = document.getElementById('chatViewTop');
const topProfileName = document.getElementById('topProfileName');
const nonSelecteds = document.querySelectorAll('.non-selected');
const selecteds = document.querySelectorAll('.selected');
const profileImg = document.querySelector('#profileImg > img');
const categoryBtn = document.querySelectorAll('#category > button');
const mapAddr = document.getElementById('mapAddr');
const chatInput = document.getElementById('chatInput');
const topProfileContainer = document.getElementById('topProfileContainer');
const closeChatDialogIcon = document.getElementById('closeChatDialogIcon');
const emojiIcon = document.getElementById('emojiIcon');
const profileNickname = document.getElementById('profileNickname');
const searchChat = document.getElementById('searchChat');

let chatRoomDtoList;
let userId;

// 유저 id 조회
axios.get('/user/id')
    .then(res => {
        userId = res.data;
    })
    .catch(err => {
        console.log(err);
    })


// 채팅 dialog 닫기 아이콘 클릭 event
closeChatDialogIcon.addEventListener('click', function(e) {
    e.preventDefault();

    chatDialog.classList.remove('visible');
})

// 채팅창 상단 프로필 클릭 event
topProfileContainer.addEventListener('click', function(e) {
    e.preventDefault();
    infoSec.classList.toggle('visible');
})

// 고정 아이콘 - 채팅 아이콘 클릭 event
chatIcon.addEventListener('click', function () {
    chatDialog.classList.toggle('visible');
})

// 채팅 리스트 아이콘 토글
toggleChatListSecIcons.forEach(toggleChatListSecIcon => {
    toggleChatListSecIcon.addEventListener('click', function () {
        chatListSec.classList.toggle('visible');
    })
})

axios.get('/chat/list')
    .then(res => {
        if (res.data !== '') {
            chatRoomDtoList = res.data;

            const ul = document.createElement('ul');

            chatRoomDtoList.forEach(chatRoomDto => {
                const li = new DOMParser().parseFromString(`
                    <li class="chat-li" data-id="${chatRoomDto.id}"
                    onClick="clickChatLi(this)">
                        <img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" alt="">
                        <div>
                            <div class="nickname"><b>${chatRoomDto.expertNickname}</b></div>
                            <div class="content"><p>${chatRoomDto.lastChatMessage}</p></div>
                        </div>
                    </li>
                `, 'text/html').querySelector('.chat-li');

                ul.appendChild(li);
            })

            chatList.appendChild(ul);
        }
    })
    .catch(err => {
        console.log(err);
    })


searchChat.addEventListener('input', function(e) {
    chatList.innerHTML = '';

    if (e.target.value === '') {
        const ul = document.createElement('ul');

        chatRoomDtoList.forEach(chatRoomDto => {
            const li = new DOMParser().parseFromString(`
                    <li class="chat-li" data-id="${chatRoomDto.id}"
                    onClick="clickChatLi(this)">
                        <img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" alt="">
                        <div>
                            <div class="nickname"><b>${chatRoomDto.expertNickname}</b></div>
                            <div class="content"><p>${chatRoomDto.lastChatMessage}</p></div>
                        </div>
                    </li>
                `, 'text/html').querySelector('.chat-li');

            ul.appendChild(li);
        })

        chatList.appendChild(ul);
    }


    if (e.target.value !== '') {
        const filteredChatRoomDtoList = chatRoomDtoList.filter(chatRoomDto =>
            chatRoomDto.expertNickname.includes(e.target.value)
        );

        const ul = document.createElement('ul');

        filteredChatRoomDtoList.forEach(chatRoomDto => {
            const li = new DOMParser().parseFromString(`
                    <li class="chat-li" data-id="${chatRoomDto.id}"
                    onClick="clickChatLi(this)">
                        <img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" alt="">
                        <div>
                            <div class="nickname"><b>${chatRoomDto.expertNickname}</b></div>
                            <div class="content"><p>${chatRoomDto.lastChatMessage}</p></div>
                        </div>
                    </li>
                `, 'text/html').querySelector('.chat-li');

            ul.appendChild(li);
        })

        chatList.appendChild(ul);
    }
})










let stomp;

// 채팅 목록에서 채팅을 클릭 했을 때의 function
function clickChatLi(chat) {
    chatListSec.classList.toggle('visible');
    selectedChatRoomId = chat.dataset.id;
    selectedExpertNickname = chat.querySelector('.nickname').innerText;

    // 어떤 채팅방을 선택했는지에 대한 style을 위한 class 추가/제거
    chatLies.forEach(chatLi => {
        if (chatLi === chat) {
            chatLi.classList.add('choice');
        } else {
            chatLi.classList.remove('choice');
        }
    })

    setChat(chat);
    getExpertInfo(selectedChatRoomId, selectedExpertNickname);

    chatMsgSec.innerHTML = '';
    chatInput.value = '';
}

// chatting setting 함수
function setChat(chatLi) {
    const content = chatLi.querySelector('.content');

    // 이전 채팅방 연결 해제
    if (stomp) {
        stomp.disconnect(function() {
            console.log('Disconnected from previous chat room');
        });
    }

    // sockJs, stomp를 이용하여 채팅 기능 활성화
    const sockJs = new SockJS("/stomp/chat");
    //1. SockJS를 내부에 들고있는 stomp를 내어줌
    stomp = Stomp.over(sockJs);

    //2. connection이 맺어지면 실행
    stomp.connect({}, function () {
        //4. subscribe(path, callback)으로 메세지를 받을 수 있음
        stomp.subscribe("/sub/chat/room/" + chatLi.dataset.id, function (messageInfo) {
            const message = JSON.parse(messageInfo.body);

            axios.post('/chat/message', message)
                .then(res => {
                })
                .catch(err => {
                    alert('알 수 없는 이유로 메세지 전송에 실패 하였습니다. 잠시 후 다시 시도해 주세요');
                })

            const chat = new DOMParser().parseFromString(`
            <div class="chat ${message.userId === userId ? 'user1' : 'user2'}">
                <div class="chat-msg">${message.content}</div>
            </div>
        `, "text/html").querySelector('.chat');

            chatMsgSec.appendChild(chat);

            // 채팅 목록에 가장 마지막 content를 표시
            content.innerText = message.content;

            chatMsgSec.append('');
            chatInput.value = '';

            chatMsgSec.scrollTop = chatMsgSec.scrollHeight;
        });
    });


    nonSelecteds.forEach(nonSelected => {
        nonSelected.style.display = 'none';
    })

    selecteds.forEach(selected => {
        selected.style.display = 'unset';
    })
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

// 전문가 채팅 리스트, 전문 분야 및 정보 가져오는 함수
function getExpertInfo(selectedChatRoomId, selectedExpertNickname) {
    // 채팅 리스트 가져오기
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

    // 전문가 정보 가져오기
    axios.get(`/expert/${selectedExpertNickname}`)
        .then(res => {
            console.log(res);
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