const CK_pw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;

const passwordForm = document.querySelector('form');
const userNewPw = document.getElementById('userNewPw');
const userNewPwChk = document.getElementById('userNewPwChk');
const errorMessages = document.querySelector('.error');

passwordForm.addEventListener('submit', (event) => {
    event.preventDefault(); // 항상 기본 제출을 방지합니다.

    let isValid = true;
    let error = '';

    // 공백이 포함되어 있는지 확인
    if (userNewPw.value.includes(' ') || userNewPwChk.value.includes(' ')) {
        isValid = false;
        error = '공백이 포함되었습니다.';
    }

    // 비밀번호가 정규 표현식에 맞는지 확인
    else if (!CK_pw.test(userNewPw.value)) {
        isValid = false;
        error = '비밀번호는 8~15자 사이여야 하며, 알파벳, 숫자 및 특수문자를 포함해야 합니다.';
    }

    // 비밀번호와 확인 비밀번호가 일치하는지 확인
    else if (userNewPw.value !== userNewPwChk.value) {
        isValid = false;
        error = '입력한 비밀번호가 서로 일치하지 않습니다.';
    }

    // 유효성 검사 결과에 따라 처리
    if (!isValid) {
        errorMessages.innerHTML = `<p>${error}</p>`;
        errorMessages.style.display = 'block'; // 오류 메시지 보이기
    } else {
        errorMessages.style.display = 'none'; // 오류 메시지 숨기기
        passwordForm.submit(); // 폼 제출
    }
});