// 돋보기 아이콘을 클릭 시, 검색 창이 보여지게 함
const findIcon = document.querySelector('#findIcon >div >  svg');
const findInputCon = document.querySelector('#clickedFind');
console.log(findIcon);
console.log(findInputCon);
findIcon.onclick = () => {
    findIcon.style.display = 'none';
    findInputCon.style.display = 'block';
}