const searchInput = document.querySelector('input[name=search]');
const keywordBtns = document.getElementById('keywordBtns').getElementsByTagName('button')
const writeBtn = document.querySelector('.faq-write-btn')
const loginBtn = document.getElementById('loginBtn')


// searchInput.onkeydown = (event) => {
//     if (event.key === 'Enter'){
//         keywordSearch(searchInput.value);
//     }
// }
// [...keywordBtns].forEach( keywordBtn => {
//     keywordBtn.onclick = () => {
//         const keyword = keywordBtn.textContent.substring(1);
//         keywordSearch(keyword);
//     }
// });
// // 검색 창에 키워드를 입력했을 떄 이동하는 경로 설정
// function keywordSearch(keyword){
//     location.href = `/board/cs/one_on_one?keyword=${keyword}`
// }

writeBtn.onclick = () => {
    location.href='/board/CSCenter/CSWrite'
}
loginBtn.onclick = () => {
    if (confirm('로그인을 하신 후 이용해 주시기 바랍니다.')){
        location.href='/user/login'
    }
}



