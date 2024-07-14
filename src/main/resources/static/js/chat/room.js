var id = '[[${chatRoomDto.id}]]';
var userId = '[[${#authentication.Principal.userDto.id}]]';

const msgArea = document.getElementById('msgArea');
const sendBtn = document.getElementById('button-send');

var sockJs = new SockJS("/stomp/chat");
//1. SockJS를 내부에 들고있는 stomp를 내어줌
var stomp = Stomp.over(sockJs);

//2. connection이 맺어지면 실행
stomp.connect({}, function (){

    //4. subscribe(path, callback)으로 메세지를 받을 수 있음
    stomp.subscribe("/sub/chat/room/" + id, function (chat) {
        var message = JSON.parse(chat.body);

        /**
         * 2024-07-12 채팅
         * ROLE_USER / ROLE_EXPERT 구분 필요
         * DB 저장 구분 필요
         **/
        console.log(message);

        axios.post('/chat/message', message)
            .then(res => {
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            })


        var content = message.content;
        var writer = message.userId;
        var str = '';

        if(writer === userId){
            const div = document.createElement('div');
            div.innerText = `${content}`;
            // str = "<div class='col-6'>";
            // str += "<div class='alert alert-secondary'>";
            // str += "<b>" + writer + " : " + message + "</b>";
            // str += "</div></div>";
            msgArea.append(div);
        }
        else{
            const div = document.createElement('div');
            div.innerText = `${content}`;
            // str = "<div class='col-6'>";
            // str += "<div class='alert alert-secondary'>";
            // str += "<b>" + writer + " : " + message + "</b>";
            // str += "</div></div>";
            msgArea.append(div);
        }

        msgArea.append(str);
    });

    //3. send(path, header, message)로 메세지를 보낼 수 있음
    stomp.send('/pub/chat/enter', {}, JSON.stringify({chatRoomId: id, userId: userId}))
});

sendBtn.addEventListener("click", function () {
    var msg = document.getElementById("msg");

    stomp.send('/pub/chat/message', {}, JSON.stringify({chatRoomId: id, content: msg.value, userId: userId}));
    msg.value = '';
});