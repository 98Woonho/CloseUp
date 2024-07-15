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

function keywordSearch(keyword){
    location.href = `/customerService/one_on_one?keyword=${keyword}`
}
