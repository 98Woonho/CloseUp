const changeProfileImgBtn = document.getElementById('changeProfileImgBtn');

// 프로필 사진 변경 버튼
changeProfileImgBtn.onclick = () => {
    if(confirm("프로필 사진을 변경하시겠습니까?")) {
        document.getElementById('fileInput').click();
    } else {

    }
}

// 프로필 사진 변경 함수
function changeProfileImage(file) {
    // 이미지 변경 로직
    const profileImage = document.getElementById('profileImage');
    // 선택한 파일을 이미지 소스로 설정
    const fileReader = new FileReader();
    fileReader.readAsArrayBuffer(file);
    fileReader.onload = () => {
        profileImage.src = URL.createObjectURL(file);
        const data = fileReader.result;
        fetch(`/imgChangeExpert`, {
            method: "PUT",
            headers: { "Content-Type": "application/octet-stream" },
            body: data
        });
    }
}