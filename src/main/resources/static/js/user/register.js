const certBtn = document.getElementById('certBtn');
const main = document.getElementById('main');
const registerForm = document.getElementById('registerForm');
const confirmIdDupBtn = document.getElementById('confirmIdDupBtn');

// 정규식
const CK_id = /^[a-zA-Z0-9]{5,15}$/;
const CK_pw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;

// 통합인증 버튼
certBtn.addEventListener('click', function(e) {
    e.preventDefault();

    IMP.init('imp82217082');

    IMP.certification(
        {
            pg: 'inicis_unified',
            merchant_uid: `mid_${new Date().getTime()}`,
        },
        function (res) {
            if (res.success) {
                // 통합인증 정보 가져오기
                axios.get(`/user/cert/${res.imp_uid}`)
                    .then(res => {
                        if (res.status === 200) {
                            main.dataset.step = '2';
                            registerForm['name'].value = res.data.name;
                            registerForm['phone'].value = res.data.phone;
                        }
                    })
                    .catch(err => {
                        if (err.response.status === 409) {
                            alert(err.response.data.msg);
                            location.href = '/user/login';
                        } else {
                            alert('알 수 없는 이유로 본인인증에 실패 하였습니다. 잠시 후 다시 시도해 주세요.');
                        }
                    })
            } else {
                alert('인증에 실패하였습니다.\n사유 : ' + res.error_msg);
            }
        }
    )
});

registerForm['id'].addEventListener('input', function() {
    if (confirmIdDupBtn.classList.contains('confirmed')) {
        confirmIdDupBtn.classList.remove('confirmed');
    }
})



// 아이디 중복 확인 버튼
confirmIdDupBtn.addEventListener('click', function(e) {
    e.preventDefault();

    if (registerForm['id'].value === '') {
        alert('아이디를 입력해 주세요.');
        return;
    }

    if (!CK_id.test(registerForm['id'].value)) {
        alert('올바른 아이디를 입력해 주세요.');
        return;
    }

    e.preventDefault();

    axios.get(`/user/confirmIdDup?id=${registerForm['id'].value}`)
        .then(res => {
            alert(res.data);
            confirmIdDupBtn.classList.add('confirmed');
        })
        .catch(err => {
            if (err.response.status === 409) {
                alert(err.response.data);
            } else {
                alert('알 수 없는 이유로 중복 확인에 실패 하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        })
})


// 회원가입 form 제출
registerForm.onsubmit = (e) => {
    e.preventDefault();

    if (registerForm['id'].value === '') {
        alert('아이디를 입력해 주세요.');
        return;
    }

    if (!CK_id.test(registerForm['id'].value)) {
        alert('올바른 아이디를 입력해 주세요.');
        return;
    }

    if (!confirmIdDupBtn.classList.contains('confirmed')) {
        alert('아이디 중복 확인을 해주세요');
        return;
    }

    if (registerForm['password'].value === '') {
        alert('비밀번호를 입력해 주세요.');
        return;
    }

    if (!CK_pw.test(registerForm['password'].value)) {
        alert('올바른 비밀번호를 입력해 주세요.');
        return;
    }

    const formData = new FormData(registerForm);

    axios.post('/user/register', formData)
        .then(res => {
            alert(res.data);
            location.href = '/user/login';
        })
        .catch(err => {
            alert('알 수 없는 이유로 회원가입에 실패 하였습니다. 잠시 후 다시 시도해 주세요.');
        })
}