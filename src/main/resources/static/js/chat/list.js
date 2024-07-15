const createRoomForm = document.getElementById('createRoomForm');

createRoomForm.onsubmit = (e) => {
    e.preventDefault();

    const formData = new FormData(createRoomForm);

    axios.post('/chat/room', formData)
        .then(res => {

        })
        .catch(err => {
            console.log(err);
        })
}