function changeProfileImage(file) {
    // 이미지 변경 로직
    const profileImage = document.getElementById('profileImage');
    console.log(file)
    // 선택한 파일을 이미지 소스로 설정
    const fileReader = new FileReader();
    fileReader.readAsArrayBuffer(file);

    fileReader.onload = (e) => {
        console.log(fileReader);
        profileImage.src = URL.createObjectURL(file);
        fetch(`/imgChange`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: profileImage
        })
    }
}