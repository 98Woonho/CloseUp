/* input 입력값 정규식 */
// 아이디 정규 표현식 공식(대/소문자영어, 숫자, 공백X, 5~15자 이내)
const CK_id = /^[a-zA-Z0-9]{5,15}$/;
// 공백 확인 정규 표현식
const CK_space = /\s/g;
// 비밀번호 정규 표현식 공식
const CK_pw = /^[A-Za-z\\d`~!@#$%^&*()-_=+]{8,20}$/;


/*
    JS
    1. 아이디, 비밀번호 정규식 확인
    (오류시 html의 .error p 문구 display="block";)
    (오류시, 잘못입력한 input에 focus시키고 작성한 input값 = ''으로 내용 비우기)

    2. 아이디 저장 checked하면 그 value값 저장

*/

const loginForm = document.getElementById('loginForm');
const errMsgs = document.querySelectorAll('.err-msg');

loginForm.onsubmit = (e) => {
    e.preventDefault();

    errMsgs.forEach(errMsg => {
        errMsg.style.display = 'none';
    })

    if (CK_space.test(loginForm['id'].value)) {
        errMsgs[0].style.display = 'block';
        loginForm['id'].focus();
        loginForm['id'].select();
        return;
    }

    if (!CK_id.test(loginForm['id'].value)) {
        errMsgs[1].style.display = 'block';
        loginForm['id'].focus();
        loginForm['id'].select();
        return;
    }

    if (CK_space.test(loginForm['password'].value)) {
        errMsgs[0].style.display = 'block';
        loginForm['pw'].focus();
        loginForm['pw'].select();
        return;
    }

    if (!CK_pw.test(loginForm['password'].value)) {
        errMsgs[1].style.display = 'block';
        loginForm['pw'].focus();
        loginForm['pw'].select();
        return;
    }

    loginForm.submit();
}