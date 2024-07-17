const chatLies = document.querySelectorAll('.chat-li');
const chatMsgSec = document.getElementById('chatMsgSec');
const chatViewTop = document.getElementById('chatViewTop');
const userId = document.getElementById('userId').value;
const topProfileName = document.getElementById('topProfileName');
const nonSelecteds = document.querySelectorAll('.non-selected');
const selecteds = document.querySelectorAll('.selected');

function clickChatLi(chat) {
    nonSelecteds.forEach(nonSelected => {
        nonSelected.style.display = 'none';
    })

    selecteds.forEach(selected => {
        selected.style.display = 'unset';
    })

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

    const expertUserId = chat.querySelector('.user-name').innerText;

    axios.get(`/expert/${expertUserId}`)
        .then(res => {
            topProfileName.innerText = res.data.userId;
        })
        .catch(err => {
            console.log(err);
        })
}