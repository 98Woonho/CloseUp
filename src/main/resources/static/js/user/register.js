const userPhoneInput = document.getElementById('userPhone');
const [certificationBtn, verificationBtn, registerBtn] =document.querySelectorAll('button')
// console.log(certificationBtn)
// 포커스 이벤트 처리
userPhoneInput.onfocus  = ()=> {
    this.placeholder = 'ex) 010-1234-5678';
};

// 블러 이벤트 처리
userPhoneInput.onblur = ()=> {
    if (this.value === '') {
        this.placeholder = '';
    }
};

//      //생략 가능
// IMP.init("imp88732243"); // 예: imp00000000
//
// certificationBtn.onclick= () => {
// IMP.certification(
//     {
//         pg: "danal.A010002002",
//         // merchant_uid: "unique_merchant_id_" + new Date().getTime()
//
//     },
//     function (rsp) {
//         // callback
//         if (rsp.success) {
//             console.log(rsp)
//             // 인증 성공 시 로직
//         } else {
//             console.log(rsp)
//             // 인증 실패 시 로직
//         }
//     },
// );
// }




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