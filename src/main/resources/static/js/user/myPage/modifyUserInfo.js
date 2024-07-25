const modifyInfoForm = document.getElementById('modifyInfoForm');
const errMsgs = document.querySelectorAll('.err-msg');
const password1 = document.getElementById('password');
const password2 = document.getElementById('password2');
// 공백 확인 정규 표현식
const CK_space = /\s/g;
// 비밀번호 정규 표현식 공식
const CK_pw = /^[A-Za-z\\d`~!@#$%^&*()-_=+]{8,20}$/;

modifyInfoForm.onsubmit = (e) => {
    e.preventDefault();

    errMsgs.forEach(errMsg => {
        errMsg.style.display = 'none';
    })

    if (CK_space.test(modifyInfoForm['password'].value)) {
        errMsgs[0].style.display = 'block';
        modifyInfoForm['pw'].focus();
        modifyInfoForm['pw'].select();
        return;
    }

    if (!CK_pw.test(modifyInfoForm['password'].value)) {
        errMsgs[1].style.display = 'block';
        modifyInfoForm['pw'].focus();
        modifyInfoForm['pw'].select();
        return;
    }

    if (password1.value !== password2.value) {
        errMsgs[2].style.display = 'block';
        modifyInfoForm['pw'].focus();
        modifyInfoForm['pw'].select();
        return;
    }

    modifyInfoForm.submit();
}