// 돋보기 아이콘을 클릭 시, 검색 창이 보여지게 함
const findIcon = document.querySelector('#findIcon >div >  svg');
const findInputCon = document.querySelector('#clickedFind');
const findInput = document.querySelector('#clickedFind > input');
// console.log(findIcon);
// console.log(findInputCon);
findIcon.onclick = () => {
    findIcon.style.display = 'none';
    findInputCon.style.display = 'block';
    findInput.focus();
}

// 토글 버튼 클릭 시 유저 ROLE 변경
const toggleSwitchInput = document.querySelector('input[type=checkbox]');

let currentRole;
// 체크박스 상태 설정

toggleSwitchInput.addEventListener('change', () => {
    const newRole = toggleSwitchInput.checked ? 'ROLE_EXPERT' : 'ROLE_USER';

    fetch('/changeRole', {
        method: "PUT",
        headers: { "Content-Type": "application/string" },
        body: newRole
    }).then(response => response.toString())
        .then(data => {
            currentRole = newRole;
        });

    location.href = "/";
});

