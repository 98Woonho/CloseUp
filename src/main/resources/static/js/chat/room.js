const chatRoomId = document.getElementById('chatRoomId').value;
const userId = document.getElementById('userId').value;
const chatMsgSec = document.getElementById('chatMsgSec');
const sendBtn = document.getElementById('button-send');

const sockJs = new SockJS("/stomp/chat");
//1. SockJS를 내부에 들고있는 stomp를 내어줌
const stomp = Stomp.over(sockJs);

//2. connection이 맺어지면 실행
stomp.connect({}, function () {
    //4. subscribe(path, callback)으로 메세지를 받을 수 있음
    stomp.subscribe("/sub/chat/room/" + chatRoomId, function (messageInfo) {
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

        chatMsgSec.append('');

        chatMsgSec.scrollTop = chatMsgSec.scrollHeight;
    });
});

sendBtn.addEventListener("click", function () {
    const msg = document.getElementById("msg");

    stomp.send('/pub/chat/message', {}, JSON.stringify({chatRoomId: chatRoomId, content: msg.value, userId: userId}));
    msg.value = '';
});