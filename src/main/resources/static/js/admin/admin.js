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

});

const logoSecDiv = document.querySelector('.logo-sec > div');
logoSecDiv.onclick = () => {
    location.href='/admin/main'
}
