
const chatListSec = document.getElementById('chatListSec');
const chatList = document.getElementById('chatList');
const chatMsgSec = document.getElementById('chatMsgSec');
const topProfileName = document.getElementById('topProfileName');
const nonSelecteds = document.querySelectorAll('.non-selected');
const selecteds = document.querySelectorAll('.selected');
const profileImg = document.querySelector('#profileImg > img');
const categoryBtn = document.querySelectorAll('#category > button');
const mapAddr = document.getElementById('mapAddr');
const chatInput = document.getElementById('chatInput');
const profileNickname = document.getElementById('profileNickname');

let userId; // 현재 로그인 한 유저의 아이디
let role; // 현재 로그인 한 유저의 역할
let name; // 현재 로그인 한 유저의 이름
let selectedChatRoomId;
let chatRoomDtoList;
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


axios.get('/chat/list')
    .then(res => {
        if (res.data !== '') {
            chatRoomDtoList = res.data;
            const ul = document.createElement('ul');
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

                    if (messageCount.innerText !== '0') {
                        messageCount.classList.add('visible');
                    }

                    if (messageCount.innerText === '0') {
                        messageCount.innerText = '';
                    }


                    // 각 채팅방에 대한 subscribe 설정
                    stomp.subscribe("/sub/chat/room/" + chatRoomDto.id, function (messageInfo) {
                        const chatLi = chatList.querySelector(`.chat-li[data-id="${chatRoomDto.id}"]`);
                        const content = chatLi.querySelector('.content');
                        const message = JSON.parse(messageInfo.body);
                        let messageCount = chatLi.querySelector('.message-count');

                        if (message.userId !== userId) {
                            if (messageCount.innerText !== '') {
                                messageCount.classList.add('visible');
                            }
                        }

                        axios.get(`/chat/room/${chatRoomDto.id}`)
                            .then(res => {
                                const notReadExpertMessageCount = res.data.notReadExpertMessageCount;
                                const notReadUserMessageCount = res.data.notReadUserMessageCount;

                                if (!chatLi.classList.contains('choice')) {
                                    messageCount.innerText = role === 'ROLE_USER' ? notReadExpertMessageCount + 1 : notReadUserMessageCount + 1;
                                } else {
                                    messageCount.innerText = '';
                                }
                            })
                            .catch(err => {
                                console.log(err);
                            })

                        const chat = new DOMParser().parseFromString(`
                            <div class="chat ${message.userId === userId ? 'user1' : 'user2'}">
                                <div class="chat-msg">${message.content}</div>
                            </div>`, "text/html").querySelector('.chat');

                        chatMsgSec.appendChild(chat);

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

        // DB에 메세지 저장
        axios.post('/chat/message', {
            chatRoomId: selectedChatRoomId,
            content: chatInput.value,
            userId: userId
        })
            .then(res => {
                axios.patch('/chat/room', {
                    id: selectedChatRoomId,
                    action: 'increment'
                })
                    .then(res => {
                        // 상대방에게 메세지가 왔을 때, 메세지 카운트 표시
                        console.log(res);
                    })
                    .catch(err => {
                        console.log(err);
                    })
            })
            .catch(err => {
                alert('알 수 없는 이유로 메세지 전송에 실패 하였습니다. 잠시 후 다시 시도해 주세요');
            });
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
















// const chatLies = document.querySelectorAll('.chat-li');
// const chatMsgSec = document.getElementById('chatMsgSec');
// const chatViewTop = document.getElementById('chatViewTop');
// const topProfileName = document.getElementById('topProfileName');
// const nonSelecteds = document.querySelectorAll('.non-selected');
// const selecteds = document.querySelectorAll('.selected');
// const profileImg = document.querySelector('#profileImg > img');
// const categoryBtn = document.querySelectorAll('#category > button');
// const mapAddr = document.getElementById('mapAddr');
// const chatInput = document.getElementById('chatInput');
// let selectedExpertNickname = document.getElementById('selectedExpertNickname');
//
// let userId; // 현재 로그인 한 유저의 아이디
// let role; // 현재 로그인 한 유저의 역할
// let name; // 현재 로그인 한 유저의 이름
// let selectedChatRoomId;
// let chatRoomDtoList;
// let stomp;
//
// // 현재 로그인 한 유저 조회
// axios.get('/user')
//     .then(res => {
//         userId = res.data.id;
//         role = res.data.role;
//         name = res.data.name;
//     })
//     .catch(err => {
//         console.log(err);
//     })
//
// if (selectedChatRoomId) {
//     selectedChatRoomId = document.getElementById('selectedChatRoomId').value;
//     selectedExpertNickname = document.getElementById('selectedExpertNickname').value;
//
//     let currentChatLi;
//
//     chatLies.forEach(chatLi => {
//         if (chatLi.dataset.id === selectedChatRoomId) {
//             chatLi.classList.add('choice');
//
//             currentChatLi = chatLi;
//         }
//     })
//
//     setChat(currentChatLi);
//     getExpertInfo(selectedChatRoomId, selectedExpertNickname);
// }
//
// // 채팅 목록에서 채팅을 클릭 했을 때의 function
// function clickChatLi(chat) {
//     selectedChatRoomId = chat.dataset.id;
//     selectedExpertNickname = chat.querySelector('.nickname').innerText;
//
//     // 어떤 채팅방을 선택했는지에 대한 style을 위한 class 추가/제거
//     chatLies.forEach(chatLi => {
//         if (chatLi === chat) {
//             chatLi.classList.add('choice');
//         } else {
//             chatLi.classList.remove('choice');
//         }
//     })
//
//     setChat(chat);
//     getExpertInfo(selectedChatRoomId, selectedExpertNickname);
//
//     chatMsgSec.innerHTML = '';
//     chatInput.value = '';
// }
//
// // chatting setting 함수
// function setChat(chatLi) {
//     const content = chatLi.querySelector('.content');
//
//     // 이전 채팅방 연결 해제
//     if (stomp) {
//         stomp.disconnect(function() {
//             console.log('Disconnected from previous chat room');
//         });
//     }
//
//     // sockJs, stomp를 이용하여 채팅 기능 활성화
//     const sockJs = new SockJS("/stomp/chat");
//     //1. SockJS를 내부에 들고있는 stomp를 내어줌
//     stomp = Stomp.over(sockJs);
//
//     //2. connection이 맺어지면 실행
//     stomp.connect({}, function () {
//         //4. subscribe(path, callback)으로 메세지를 받을 수 있음
//         stomp.subscribe("/sub/chat/room/" + chatLi.dataset.id, function (messageInfo) {
//             const message = JSON.parse(messageInfo.body);
//
//             axios.post('/chat/message', message)
//                 .then(res => {
//                 })
//                 .catch(err => {
//                     alert('알 수 없는 이유로 메세지 전송에 실패 하였습니다. 잠시 후 다시 시도해 주세요');
//                 })
//
//             const chat = new DOMParser().parseFromString(`
//             <div class="chat ${message.userId === userId ? 'user1' : 'user2'}">
//                 <div class="chat-msg">${message.content}</div>
//             </div>
//         `, "text/html").querySelector('.chat');
//
//             chatMsgSec.appendChild(chat);
//
//             // 채팅 목록에 가장 마지막 content를 표시
//             content.innerText = message.content;
//
//             chatMsgSec.append('');
//             chatInput.value = '';
//
//             chatMsgSec.scrollTop = chatMsgSec.scrollHeight;
//         });
//     });
//
//
//     nonSelecteds.forEach(nonSelected => {
//         nonSelected.style.display = 'none';
//     })
//
//     selecteds.forEach(selected => {
//         selected.style.display = 'unset';
//     })
// }
//
//
// // 채팅방 메세지 input keydown event 함수
// function keydownEvent(e) {
//         if (e.keyCode === 13) {
//             if (chatInput.value === '') {
//                 alert('메세지를 입력해 주세요.');
//                 return;
//             }
//
//             e.preventDefault();
//
//             // stomp send
//             stomp.send('/pub/chat/message', {}, JSON.stringify({
//                 chatRoomId: selectedChatRoomId,
//                 content: chatInput.value,
//                 userId: userId
//             }));
//         }
// }
//
// // 전문가 채팅 리스트, 전문 분야 및 정보 가져오는 함수
// function getExpertInfo(selectedChatRoomId, selectedExpertNickname) {
//     // 채팅 리스트 가져오기
//     axios.get(`/chat/messages/${selectedChatRoomId}`)
//         .then(res => {
//             const chatMessageDtoList = res.data;
//
//             chatMessageDtoList.forEach(chatMessageDto => {
//                 const message = new DOMParser().parseFromString(`
//                         <div class="chat ${userId === chatMessageDto.userId ? 'user1' : 'user2'}">
//                             <div class="chat-msg">${chatMessageDto.content}</div>
//                         </div>
//                     `, 'text/html').querySelector('.chat');
//
//                 chatMsgSec.appendChild(message);
//             })
//
//             chatMsgSec.scrollTop = chatMsgSec.scrollHeight;
//         })
//         .catch(err => {
//             console.log(err);
//         })
//
//     // 전문가 정보 가져오기
//     axios.get(`/expert/${selectedExpertNickname}`)
//         .then(res => {
//             topProfileName.innerText = res.data.nickname;
//             mapAddr.innerText = `(${res.data.zipcode}) ${res.data.address} ${res.data.address_detail}`;
//             /** TODO
//              * 전문가 등록 및 수정에서 profileImg 구현되면 profileImg 넣어야 함.
//              */
//         })
//         .catch(err => {
//             console.log(err);
//         })
//
//     // 전문가 상세 정보 중 expertise(전문지식) 가져오기
//     axios.get(`/expert/detail/${selectedExpertNickname}/expertise`)
//         .then(res => {
//             categoryBtn.forEach((categoryBtn, index) => {
//                 categoryBtn.innerText = res.data[index].information;
//             })
//         })
//         .catch(err => {
//             console.log(err);
//         })
// }