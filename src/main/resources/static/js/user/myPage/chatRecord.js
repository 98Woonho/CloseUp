const chatLies = document.querySelectorAll('.chat-li');
const chatMsgSec = document.getElementById('chatMsgSec');
const chatViewTop = document.getElementById('chatViewTop');
const userId = document.getElementById('userId').value;
const topProfileName = document.getElementById('topProfileName');
const nonSelecteds = document.querySelectorAll('.non-selected');
const selecteds = document.querySelectorAll('.selected');

const profileImg = document.querySelector('#profileImg > img');
const categoryBtn = document.querySelectorAll('#category > button');
const mapAddr = document.getElementById('mapAddr');
const chatInput = document.getElementById('chatInput');
let selectedChatRoomId = document.getElementById('selectedChatRoomId');
let selectedExpertNickname = document.getElementById('selectedExpertNickname');

let stomp;

if (selectedChatRoomId) {
    selectedChatRoomId = document.getElementById('selectedChatRoomId').value;
    selectedExpertNickname = document.getElementById('selectedExpertNickname').value;

    let currentChatLi;

    chatLies.forEach(chatLi => {
        if (chatLi.dataset.id === selectedChatRoomId) {
            chatLi.classList.add('choice');

            currentChatLi = chatLi;
        }
    })

    setChat(currentChatLi);
    getExpertInfo(selectedChatRoomId, selectedExpertNickname);
}

function clickChatLi(chat) {

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

    /** TODO
     * chat-li를 클릭 할 때 마다, 이벤트 중첩되는 거 해결해야 함.
     */


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


    chatLies.forEach(chatLi => {
        if (chatLi === chat) {
            chatLi.classList.add('selected');
        } else {
            chatLi.classList.remove('selected');
        }
    })

    chatMsgSec.innerHTML = '';
    chat.classList.add('selected');

    axios.get(`/chat/messages/${chat.dataset.id}`)

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
        })
        .catch(err => {
            console.log(err);
        })


    // 전문가 정보 가져오기
    axios.get(`/expert/${selectedExpertNickname}`)
        .then(res => {
            topProfileName.innerText = res.data.nickname;
            mapAddr.innerText = `(${res.data.zipcode}) ${res.data.address} ${res.data.address_detail}`;
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

    const expertUserId = chat.querySelector('.user-name').innerText;

    axios.get(`/expert/${expertUserId}`)
        .then(res => {
            topProfileName.innerText = res.data.userId;

        })
        .catch(err => {
            console.log(err);
        })
}