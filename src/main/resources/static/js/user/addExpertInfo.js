// 캐러셀
// Grab wrapper nodes
const rootNode = document.querySelector('.embla');
const viewportNode = rootNode.querySelector('.embla__viewport');

// Grab button nodes
const prevButtonNode = rootNode.querySelector('.embla__prev');
const nextButtonNode = rootNode.querySelector('.embla__next');

// Initialize the carousel
const embla = EmblaCarousel(viewportNode);

// Add click listeners
prevButtonNode.addEventListener('click', embla.scrollPrev, false);
nextButtonNode.addEventListener('click', embla.scrollNext, false);


const addExpertiseBtn = document.getElementById('addExpertiseBtn');
const addCareerBtn = document.getElementById('addCareerBtn');
const addAbilityBtn = document.getElementById('addAbilityBtn');
const expertiseInput = document.getElementById('expertiseInput');
const careerInput = document.getElementById('careerInput');
const abilityInput = document.getElementById('abilityInput');
const confirmNicknameDupBtn = document.getElementById('duplicateBtn');
const addressFindBtn = document.getElementById('addressFindBtn');
const zipcodeInput = document.getElementById('zipcodeInput');
const addressInput = document.getElementById('addressInput');
const expertInfoForm = document.querySelector('form');

// 정규식
const CK_nickname = /^[가-힣a-zA-Z0-9]{4,15}$/;

// 폼데이터
const expertises = [];
const careers = [];
const abilities = [];

// 입력 버튼 클릭 시 태그 추가
addExpertiseBtn.addEventListener('click', function() {
    const expertiseTextarea = document.getElementById('expertiseInput').value;
    const expertiseContainer = document.getElementById('expertiseTagsContainer');

    addTags(expertiseTextarea, expertiseContainer, 'expertise');
    expertises.push(expertiseTextarea);

    document.getElementById('expertiseInput').value = '';

    const deleteButton = document.querySelector('#expertiseTagsContainer .tags:last-child .delete-btns');
    deleteButton.addEventListener('click', function() {
        // 삭제 버튼의 부모 요소에서 expertises 배열에서 해당 값을 찾아 삭제
        const tagValue = this.parentElement.textContent.trim(); // 태그의 텍스트 가져오기
        const index = expertises.indexOf(tagValue); // 배열에서 해당 값의 인덱스 찾기
        if (index > -1) {
            expertises.splice(index, 1); // 배열에서 제거
        }
        this.parentElement.remove(); // 태그 요소 삭제
    });
});

addCareerBtn.addEventListener('click', function() {
    const careerTextarea = document.getElementById('careerInput').value;
    const careerContainer = document.getElementById('careerTagsContainer');

    addTags(careerTextarea, careerContainer, 'career');
    careers.push(careerTextarea);
    console.log(careers);

    document.getElementById('careerInput').value = '';

    const deleteButton = document.querySelector('#careerTagsContainer .tags:last-child .delete-btns');
    deleteButton.addEventListener('click', function() {
        const tagValue = this.parentElement.textContent.trim();
        const index = careers.indexOf(tagValue);
        if (index > -1) {
            careers.splice(index, 1);
        }
        this.parentElement.remove();
        console.log(careers);
    });
});

addAbilityBtn.addEventListener('click', function() {
    const abilityTextarea = document.getElementById('abilityInput').value;
    const abilityContainer = document.getElementById('abilityTagsContainer');

    addTags(abilityTextarea, abilityContainer, 'ability');
    abilities.push(abilityTextarea);
    console.log(abilities);

    document.getElementById('abilityInput').value = '';

    const deleteButton = document.querySelector('#abilityTagsContainer .tags:last-child .delete-btns');
    deleteButton.addEventListener('click', function() {
        const tagValue = this.parentElement.textContent.trim();
        const index = abilities.indexOf(tagValue);
        if (index > -1) {
            abilities.splice(index, 1);
        }
        this.parentElement.remove();
        console.log(abilities);
    });
})

// 엔터 시에도 태그 추가
expertiseInput.addEventListener('keydown', function(e){
    if (e.key.toUpperCase() === 'ENTER') {
        e.preventDefault();
        const expertiseTextarea = document.getElementById('expertiseInput').value;
        const expertiseContainer = document.getElementById('expertiseTagsContainer');

        addTags(expertiseTextarea, expertiseContainer, 'expertise');
        expertises.push(expertiseTextarea);

        document.getElementById('expertiseInput').value = '';

        const deleteButton = document.querySelector('#expertiseTagsContainer .tags:last-child .delete-btns');
        deleteButton.addEventListener('click', function() {
            // 삭제 버튼의 부모 요소에서 expertises 배열에서 해당 값을 찾아 삭제
            const tagValue = this.parentElement.textContent.trim(); // 태그의 텍스트 가져오기
            const index = expertises.indexOf(tagValue); // 배열에서 해당 값의 인덱스 찾기
            if (index > -1) {
                expertises.splice(index, 1); // 배열에서 제거
            }
            this.parentElement.remove(); // 태그 요소 삭제
        });
    }
});

careerInput.addEventListener('keydown', function (e) {
    if (e.key.toUpperCase() === 'ENTER') {
        e.preventDefault();
        const careerTextarea = document.getElementById('careerInput').value;
        const careerContainer = document.getElementById('careerTagsContainer');

        addTags(careerTextarea, careerContainer, 'career');
        careers.push(careerTextarea);
        console.log(careers);

        document.getElementById('careerInput').value = '';

        const deleteButton = document.querySelector('#careerTagsContainer .tags:last-child .delete-btns');
        deleteButton.addEventListener('click', function() {
            const tagValue = this.parentElement.textContent.trim();
            const index = careers.indexOf(tagValue);
            if (index > -1) {
                careers.splice(index, 1);
            }
            this.parentElement.remove();
            console.log(careers);
        });
    }
})

abilityInput.addEventListener('keydown', function (e) {
    if (e.key.toUpperCase() === 'ENTER') {
        e.preventDefault()
        const abilityTextarea = document.getElementById('abilityInput').value;
        const abilityContainer = document.getElementById('abilityTagsContainer');

        addTags(abilityTextarea, abilityContainer, 'ability');
        abilities.push(abilityTextarea);
        console.log(abilities);

        document.getElementById('abilityInput').value = '';

        const deleteButton = document.querySelector('#abilityTagsContainer .tags:last-child .delete-btns');
        deleteButton.addEventListener('click', function() {
            const tagValue = this.parentElement.textContent.trim();
            const index = abilities.indexOf(tagValue);
            if (index > -1) {
                abilities.splice(index, 1);
            }
            this.parentElement.remove();
            console.log(abilities);
        });
    }
})

// 태그 추가 메서드
function addTags(textarea, tagsContainer, value) {
    if (textarea.trim() !== '') {
        // 템플릿 문자열 생성
        const template = `<div class="tags"><p value="${value}">${textarea}</p><button type="button" class="delete-btns"><i class="fa-solid fa-xmark"></i></button></div>`;
        tagsContainer.insertAdjacentHTML('beforeend', template);
    } else {
        alert("입력된 내용이 없습니다!");
    }
}


// 체크박스 갯수 제한
const maxChecked = 6; // 최대 체크 수
const checkboxes = document.querySelectorAll('.skills-container input[type="checkbox"]');
const skills = [];

checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', function(e) {
        if (e.target.checked) {
            skills.push(e.target.value);
        } else if(!e.target.checked) {
            skills.pop(e.target.value);
        }

        const checkedCount = Array.from(checkboxes).filter(chk => chk.checked).length;

        if (checkedCount > maxChecked) {
            alert(`최대 ${maxChecked}개까지만 선택할 수 있습니다.`);
            this.checked = false; // 초과 선택 시 체크 해제
        }
    });
});

// 닉네임 중복 확인 버튼
confirmNicknameDupBtn.addEventListener('click', function(e) {
    e.preventDefault();

    if (expertInfoForm['nickname'].value === '') {
        alert('닉네임을 입력해 주세요.');
        return;
    }

    if (!CK_nickname.test(expertInfoForm['nickname'].value)) {
        alert('올바른 닉네임을 입력해 주세요.');
        return;
    }

    e.preventDefault();

    axios.get(`/user/confirmNicknameDup?nickname=${expertInfoForm['nickname'].value}`)
        .then(res => {
            alert(res.data);
            confirmNicknameDupBtn.classList.add('confirmed');
        })
        .catch(err => {
            if (err.response.status === 409) {
                alert(err.response.data);
            } else {
                alert('알 수 없는 이유로 중복 확인에 실패 하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        })
});

// 주소 검색
addressFindBtn.onclick = () => {
    new daum.Postcode({
        oncomplete: function(data) {
            const postcode = data.zonecode; // (우편번호)
            const address = data.address; // (도로명 주소)
            const buildingName = data.buildingName; // 건물명(아파트 이름 등.)
            zipcodeInput.value = postcode;
            addressInput.value = address;
            if(buildingName.trim().length > 0){
                addressInput.value += ` (${buildingName})`;
            }
        }
    }).open();
}

// 폼 제출
expertInfoForm.addEventListener('submit', (e) => {
    e.preventDefault();

    if (expertInfoForm['nickname'].value === '') {
        alert('아이디를 입력해 주세요.');
        return;
    }

    if (!CK_nickname.test(expertInfoForm['nickname'].value)) {
        alert('올바른 아이디를 입력해 주세요.');
        return;
    }

    if (!confirmNicknameDupBtn.classList.contains('confirmed')) {
        alert('닉네임 중복 확인을 해주세요');
        return;
    }

    const formData = new FormData(expertInfoForm);
    formData.append('zipcode', expertInfoForm['zipcode'].value);
    formData.append('address', expertInfoForm['address'].value);
    formData.append('skills', skills);
    formData.append('expertises', expertises);
    formData.append('careers', careers);
    formData.append('abilities', abilities);

    axios.post('/user/addExpertInfo', formData)
        .then(response => {
            alert(response.data);
            location.href = '/expert/myPageMain';
        })
        .catch(error => {
           alert('전문가 정보 등록에 실패하였습니다.');
            console.error('Error : ', error);
        });
})