const userPhoneInput = document.getElementById('userPhone');

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