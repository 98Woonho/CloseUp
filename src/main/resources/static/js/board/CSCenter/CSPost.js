const titleSec = document.querySelector('.title-sec > p')

// 댓글 좋아요 기능
document.querySelectorAll('.commentLikeBtn').forEach(likeBtn => {
    likeBtn.addEventListener('click', function() {
        var commentId = this.getAttribute('data-comment-id');
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/board/CSCenter/comment/' + commentId + '/like', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                likeBtn.querySelector('.commentLikeCount').textContent = response.likeCount;
                if (response.liked) {
                    likeBtn.querySelector('i').classList.remove('fa-regular');
                    likeBtn.querySelector('i').classList.add('fa-solid');
                } else {
                    likeBtn.querySelector('i').classList.remove('fa-solid');
                    likeBtn.querySelector('i').classList.add('fa-regular');
                }
            }
        };
        xhr.send();
    });
});



document.querySelectorAll('.comment-delete').forEach(deleteIcon => {
    deleteIcon.addEventListener('click', function () {
        const commentId = this.getAttribute('data-comment-id');
        if (confirm('정말로 이 댓글을 삭제하시겠습니까?')) {
            fetch(`/board/CSCenter/comment/${commentId}`, {
                method: 'DELETE',
                credentials: 'same-origin'
            }).then(response => {
                if (response.ok) {
                    this.closest('.commentCon').remove();
                    location.reload();
                } else if (response.status === 403) {
                    alert('댓글을 삭제할 권한이 없습니다.');
                } else {
                    alert('댓글 삭제에 실패했습니다.');
                }
            });
        }
    });
});

titleSec.onclick = () => {
    location.href='/board/CSCenter/CSMain'

}