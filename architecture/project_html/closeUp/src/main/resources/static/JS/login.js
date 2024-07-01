var userId = document.getElementById('userId'); // 사용자 아이디
var userPw = document.getElementById('userPw'); // 사용자 비밀번호

const loginBtn = document.querySelector('.login'); // 로그인 버튼
const idErrorMsg = document.querySelector('.error-id'); // 에러 메세지 부분(아이디)

// 로그인 버튼 click시, 실행되는 부분
loginBtn.addEventListener('click',() => {

});


// 아이디 정규 표현식 공식(대/소문자영어, 숫자, 공백X, 5~15자 이내)
const CK_id = /^[a-zA-Z][0-9]{5,15}$/;
// 공백 확인 정규 표현식
const CK_space = /\s/g;


// 아이디 입력값 정규 표현식 확인
userId.addEventListener('blur', ()=> {
    // 입력값이 없을때
    if (userId.value.length === 0) {
        idErrorMsg.textContent = '대/소문자 영어, 숫자를 포함한 5-15자이내로 입력해주세요.';
        idErrorMsg.style.color = 'red';
        idErrorMsg.style.fontSize = '13px';
        return false;
    }
    // 입력 값이 존재할 때
    else {
        if (!CK_id.test(userId.value)) {
            errorId.style.display = 'block';
            userId.value = "";
            return false;
            }
        // 공백 포함시 공백 제거
        else if (CK_space.test(userId.value)){
            userId.value = userId.value.replace(CK_space,"");
            console.log(userId.value);
        }
    }
});

// 비밀번호 정규 표현식 공식
const CK_pw = /^[A-Za-z\\d`~!@#$%^&*()-_=+]{8,20}$/;

// 비밀번호 입력값 정규 표현식 확인 (대/소문자 영어, 숫자, 특수기호를 포함한 8-20자 이내)
userPw.addEventListener('blur', ()=> {
    // 입력값이 없을때
    if (userPw.value.length === 0) {
        userPw.value = "";
        return false;
    }

});

