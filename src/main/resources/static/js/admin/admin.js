const selectAllCheckbox = document.getElementById('selectAll');
const userCheckboxes = document.querySelectorAll('.userCheckbox');

document.querySelectorAll('input[type="radio"]').forEach(radio => {
// 검색 폼 제출 이벤트 리스너 (기존 코드)
    document.querySelector('form').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            this.submit();
        }
    });

    radio.addEventListener('click', function(e) {
        // 이미 선택된 라디오 버튼을 클릭하면 선택 해제
        if (this.checked) {
            e.preventDefault();
            this.checked = false;
        }
    });
});

// 전체 선택 라디오 버튼
const selectAllRadio = document.querySelector('thead input[type="radio"]');
const userRadios = document.querySelectorAll('tbody input[type="radio"]');

selectAllRadio.addEventListener('click', function() {
    const isChecked = this.checked;
    userRadios.forEach(radio => {
        radio.checked = isChecked;
    });
});

userRadios.forEach(radio => {
    radio.addEventListener('click', function() {
        const allChecked = Array.from(userRadios).every(r => r.checked);
        selectAllRadio.checked = allChecked;
    });
});


const logoSecDiv = document.querySelector('.logo-sec > div');
logoSecDiv.onclick = () => {
    location.href='/admin/main'
}
