// const [writeBtn, loginBtn] = document.querySelectorAll('button');
const writeBtn = document.querySelector('button[type=button]')
const loginBtn = document.getElementById('loginBtn')
console.log(writeBtn);
console.log(loginBtn);

writeBtn.onclick = () => {
    location.href='/board/community/communityWrite'
}
loginBtn.onclick = () => {
    if (confirm('로그인을 하신 후 이용해 주시기 바랍니다.')){
        location.href='/user/login'
    }
}
