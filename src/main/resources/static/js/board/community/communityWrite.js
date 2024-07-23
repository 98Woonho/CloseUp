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

const fileInput = document.querySelector('#files');
const fileInfo = document.querySelector('#fileSection p');
const contentTextArea = document.querySelector('textarea[name="content"]');
const contentInfo = document.querySelector('#contentInfo');

    // 파일 선택 시
    fileInput.addEventListener('change', () => {
        const files = fileInput.files;
        if (files.length > 0) {
            const fileNames = Array.from(files).map(file => file.name).join(', ');
            fileInfo.textContent = `선택된 파일: ${fileNames}`;
        } else {
            fileInfo.textContent = '파일이 선택되지 않았습니다.';
        }
    });

    // 내용 입력 시
    contentTextArea.addEventListener('input', () => {
        contentInfo.textContent = `내용 미리보기: ${contentTextArea.value}`;
    });

