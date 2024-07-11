const certificationBtn = document.getElementById('certificationBtn');

certificationBtn.addEventListener('click', function(e) {
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
                axios.get(`/user/auth/${res.imp_uid}`)
                    .then(res => {
                        const { name, phone } = res.data.response;

                        console.log(name);
                        console.log(phone);
                    })
                    .catch(err => {
                        console.log(err);
                    })

                // // 통합인증 정보로 가입되어 있는 유저 찾기
                // axios.get(`/user?name=${name}&birthday=${birthday}&phone=${phone}`)
                //     .then(res => {
                //         // 가입되어 있는 유저가 없으면 회원가입 페이지로 이동
                //         if (res.data.length === 0) {
                //             const state = {
                //                 certificationInfo: {
                //                     name: name,
                //                     birthday: birthday,
                //                     phone: phone
                //                 }
                //             }
                //             navigate('/user/join', { state });
                //             // 가입되어 있는 유저가 있으면 문구 출력 후 로그인 페이지로 이동
                //         } else {
                //             const state = {
                //                 isJoinPage: true
                //             }
                //
                //             alert('이미 해당 정보로 계정이 존재 합니다. 로그인 화면으로 이동합니다.');
                //             navigate('/user/login', { state });
                //         }
                //     })
                //     .catch(err => {
                //         console.log(err);
                //     })
                //
                //

            } else {
                alert('인증에 실패하였습니다. 에러 내용: ' + res.error_msg);
            }
        }
    )
});


// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

const registerForm = document.getElementById('registerForm');

registerForm.onsubmit = (e) => {
    e.preventDefault();

    const CK_space = /\s/g;
    const CK_id = /^[a-zA-Z0-9]{5,15}$/;
    const CK_pw = /^[A-Za-z\\d`~!@#$%^&*()-_=+]{8,20}$/;

    if (!CK_id.test(registerForm['id'].value)) {
        alert('올바른 아이디를 입력해 주세요.');
        return;
    }
}