
// 즐겨찾기 하트 버튼 클릭시,
function addWishlist(wishHeartBtn){
    requestWishList(wishHeartBtn.id, 'POST', "즐겨찾기 등록 완료")
}
// 등록된 즐겨찾기 목록에서 하트 버튼을 클릭시 (즐겨찾기 해제)
function deleteWishlist(wishHeartBtn) {
    requestWishList(wishHeartBtn.id, 'DELETE', "즐겨찾기 삭제 완료")
}

function requestWishList(expertId, method, message){
    // const csrfToken = document.querySelector("meta[name=_csrf]").getAttribute("content");
    // user의 아이디와 좋아요 누른 expert의 아이디값을 전달
    fetch(`/myPage/wishlist/${expertId}`,{
        method: method,
        // headers: {
        //     "X-CSRF-TOKEN": csrfToken
        // }
    }).then(response => {
        if(response.status === 401){
            alert("로그인 이후 재시도 해주세요")
        }else{
            alert(message);
            location.reload();

        }
    })
}


