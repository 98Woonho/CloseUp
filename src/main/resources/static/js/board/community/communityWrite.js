const [cancelBtn, checkBtn] = document.querySelectorAll('button');

const categorySelect = document.querySelector('select');
const form = document.querySelector('form');
const titleInput = document.getElementById('title')
const contentInput = document.querySelector('textarea');


form.addEventListener('submit', function(event) {
    if (categorySelect.value === '카테고리 선택') {
        alert('카테고리를 선택해 주세요.');
        event.preventDefault();

        if (titleInput.value.trim() === '') {
            alert('제목을 입력해 주세요.');
            event.preventDefault(); // Prevent form submission
            return;
        }

        if (contentInput.value.trim() === '') {
            alert('내용을 입력해 주세요.');
            event.preventDefault(); // Prevent form submission
            return;
        }
    }
});

cancelBtn.onclick = () => {
    location.href='/board/community/communityMain'
}
