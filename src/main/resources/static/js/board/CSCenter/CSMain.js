const searchInput = document.querySelector('input[name=search]');
const keywordBtns = document.getElementById('keywordBtns').getElementsByTagName('button')
const writeBtn = document.querySelector('.faq-write-btn')
const loginBtn = document.getElementById('loginBtn')
const titleSec = document.querySelector('.title-sec > p')
const keywordButtons = document.querySelectorAll('.keyword-btn');
const keywordInput = document.querySelector('input[name="keyword"]');
const searchForm = document.querySelector('form');

keywordButtons.forEach(button => {
    button.addEventListener('click', function() {
        const keyword = this.getAttribute('data-keyword');
        keywordInput.value = keyword;
        searchForm.submit();
    });
});

titleSec.onclick = () => {
    location.href='/board/CSCenter/CSMain'

}
writeBtn.onclick = () => {
    location.href='/board/CSCenter/CSWrite'
}

loginBtn.onclick = () => {
    if (confirm('로그인을 하신 후 이용해 주시기 바랍니다.')){
        location.href='/user/login'
    }
}

