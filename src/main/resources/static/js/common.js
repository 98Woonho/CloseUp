const chatDialog = document.getElementById('chatDialog');
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
const topProfileImg = topProfileContainer.querySelector('img');
const closeChatDialogIcon = document.getElementById('closeChatDialogIcon');
const profileNickname = document.getElementById('profileNickname');
const searchChat = document.getElementById('searchChat');
const dot = document.getElementById('dot');
const profileBtn = document.getElementById('profileBtn');

let chatIcon = document.getElementById('chatIcon');
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
    })

profileBtn.addEventListener('click', function(e) {
    e.preventDefault();

    location.href = `/user/expertDetail/${topProfileName.innerText}`;
})

// 경로가 /myPage/chats면 chatIcon을 숨기고 변수를 null로 초기화
if (window.location.pathname === '/myPage/chats') {
    chatIcon.style.display = 'none';
    chatIcon = null;
}

if (chatIcon) {
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
            let allNotReadUserMessageCount = 0;
            let allNotReadExpertMessageCount = 0;

            // sockJs setting
            const sockJs = new SockJS("/stomp/chat");
            stomp = Stomp.over(sockJs);

            // 하나의 WebSocket 연결을 사용하여 Stomp 연결 설정
            stomp.connect({}, function () {
                chatRoomDtoList.forEach(chatRoomDto => {
                    const li = new DOMParser().parseFromString(`
                        <li class="chat-li" data-id="${chatRoomDto.id}" data-userId="${chatRoomDto.userId}" onClick="clickChatLi(this)">
                            <img src="${role === 'ROLE_USER' ? `/expert/profileImage?expertNickname=${chatRoomDto.expertNickname}` : `/myPage/profileImage?userId=${chatRoomDto.userId}`}" alt="">
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

                        if (message.userId !== userId && !chatLi.classList.contains('choice')) {
                            dot.classList.add('visible');
                        }

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

                        if (chatLi.classList.contains('choice')) {
                            getMessages(chatLi.dataset.id);
                        }

                        // 채팅 목록에 가장 마지막 content를 표시
                        content.innerText = message.content;

                        chatInput.value = '';

                        // 채팅이 올 때마다 스크롤을 맨 아래로 내림
                        chatMsgSec.scrollTop = chatMsgSec.scrollHeight;
                    });

                    allNotReadExpertMessageCount += chatRoomDto.notReadExpertMessageCount;
                    allNotReadUserMessageCount += chatRoomDto.notReadUserMessageCount;
                });
                chatList.appendChild(ul);

                if (role === 'ROLE_USER') {
                    if (allNotReadExpertMessageCount !== 0) {
                        dot.classList.add('visible');
                    }
                }

                if (role === 'ROLE_EXPERT') {
                    if (allNotReadUserMessageCount !== 0) {
                        dot.classList.add('visible');
                    }
                }
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
                    <li class="chat-li" data-id="${chatRoomDto.id}" data-userId="${chatRoomDto.userId}"
                    onClick="clickChatLi(this)">
                         <img src="${role === 'ROLE_USER' ? `/expert/profileImage?expertNickname=${chatRoomDto.expertNickname}` : `/myPage/profileImage?userId=${chatRoomDto.userId}`}" alt="">
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

// 채팅방의 메세지를 가져오는 함수
function getMessages(chatRoomId) {
    chatMsgSec.innerHTML = '';
    // 선택된 채팅방의 채팅목록 가져오기
    axios.get(`/chat/messages/${chatRoomId}`)
        .then(res => {
            const chatMessageDtoList = res.data;

            chatMessageDtoList.forEach(chatMessageDto => {
                const writtenAt = chatMessageDto.writtenAt !== null ? new Date(chatMessageDto.writtenAt) : '';
                let time;

                if (writtenAt === '') {
                    time = '';
                } else {
                    const hour = writtenAt.getHours().toString().padStart(2, '0');
                    const minute = writtenAt.getMinutes().toString().padStart(2, '0');

                    time = `${hour}:${minute}`
                }

                const message = chatMessageDto.userId !== null
                    ? new DOMParser().parseFromString(`
                        <div class="chat ${userId === chatMessageDto.userId ? 'user1' : 'user2'}">
                            <span class="${userId === chatMessageDto.userId ? 'time' : ''}">${userId === chatMessageDto.userId ? time : ''}</span>
                            <div class="chat-msg">${chatMessageDto.content}</div>
                            <span class="${userId !== chatMessageDto.userId ? 'time' : ''}">${userId !== chatMessageDto.userId ? time : ''}</span>
                        </div>
                    `, 'text/html').querySelector('.chat')
                    : new DOMParser().parseFromString(`
                        <div class="chat date">
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
}

// 채팅 목록에서 채팅을 클릭 했을 때의 function
function clickChatLi(chat) {
    const chatLies = document.querySelectorAll('.chat-li');
    const userId = chat.dataset.userid;
    let selectedNickname = chat.querySelector('.nickname').innerText;

    let messageCount = 0;
    selectedChatRoomId = chat.dataset.id;

    chatListSec.classList.toggle('visible');

    // 상단 프로필 이미지
    topProfileImg.src = role === 'ROLE_USER' ? `/expert/profileImage?expertNickname=${selectedNickname}` : `/myPage/profileImage?userId=${userId}`;

    // 채팅방을 선택 했을 때, 읽지 않은 메세지 카운트 초기화
    chat.querySelector('.message-count').innerText = '';
    chat.querySelector('.message-count').classList.remove('visible');

    // 어떤 채팅방을 선택했는지에 대한 style을 위한 class 추가/제거
    chatLies.forEach(chatLi => {
        if (chatLi === chat) {
            chatLi.classList.add('choice');
        } else {
            chatLi.classList.remove('choice');
        }

        messageCount += Number(chatLi.querySelector('.message-count').innerText);
    })

    // 메세지 카운트가 0이면 아이콘에 메세지 알림 dot 제거
    if (messageCount === 0) {
        dot.classList.remove('visible');
    }

    getMessages(selectedChatRoomId);

    // chat_room DB에 읽지 않은 메세지 0으로 초기화
    axios.patch('/chat/room', {
        id: chat.dataset.id,
        action: 'reset'
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

        axios.post('/chat/message', {
            chatRoomId: selectedChatRoomId,
            content: chatInput.value,
            userId: userId
        })
            .then(res => {
                // stomp send
                stomp.send('/pub/chat/message', {}, JSON.stringify({
                    chatRoomId: selectedChatRoomId,
                    content: chatInput.value,
                    userId: userId
                }));
            })
            .catch(err => {
                console.log(err);
            })

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


            // 전문가 위치 표시하는 지도
            getExpertLocation(`${res.data.address}`);

            profileImg.src = `/expert/profileImage?expertNickname=${selectedExpertNickname}`;

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

function getExpertLocation(address) {

    naver.maps.Service.geocode({
        query: address
    }, function(status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            return alert('잘못된 주소를 입력하셨습니다.');
        }

        var result = response.v2, // 검색 결과의 컨테이너
            items = result.addresses; // 검색 결과의 배열

        let addrLat = items[0].y;
        let addrLng = items[0].x;

        var map = new naver.maps.Map('map', {
            center: new naver.maps.LatLng(addrLat, addrLng),
            zoom: 15,
            minZoom: 14, //지도의 최소 줌 레벨
            draggable: true,
            pinchZoom: true,
            scrollWheel: true,
            disableKineticPan: false, // 관성드래깅
            scaleControl: false, // 스케일 컨트롤러
            logoControl: true, // 로고 컨트롤러
            logoControlOptions: {
                position: naver.maps.Position.TOP_RIGHT
            },
            mapDataControl: false
        });

        var marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(addrLat, addrLng),
            map: map
        });
    });
}
