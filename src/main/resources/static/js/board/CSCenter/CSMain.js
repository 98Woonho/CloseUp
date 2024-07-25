const searchInput = document.querySelector('input[name=search]');
const keywordBtns = document.getElementById('keywordBtns').getElementsByTagName('button')

searchInput.onkeydown = (event) => {
    if (event.key === 'Enter'){
        keywordSearch(searchInput.value);
    }
}
[...keywordBtns].forEach( keywordBtn => {
    keywordBtn.onclick = () => {
        const keyword = keywordBtn.textContent.substring(1);
        keywordSearch(keyword);
    }
});
// 검색 창에 키워드를 입력했을 떄 이동하는 경로 설정
function keywordSearch(keyword){
    location.href = `/board/cs/keyword=${keyword}`
}


