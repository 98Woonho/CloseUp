const likeBtn = document.querySelector('#likeBtn');
const likeCountSpan = document.querySelector('.likeCount');
const inputLoginRequest = document.querySelector('#commentInputCon input[type="text"][placeholder="댓글을 작성하려면 로그인 해주세요"]');


    likeBtn.addEventListener('click', function() {
        var articleId = likeBtn.getAttribute('data-article-id');
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/board/community/communityPost/' + articleId + '/like', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
            console.log('Sending request for article ID:', articleId);
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                likeCountSpan.textContent = response.likeCount;
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

    // // 파일 업로드 미리보기 기능 (선택적)
    // var filesInput = document.querySelector('#files');
    // if (filesInput) {
    //     filesInput.addEventListener('change', function () {
    //         var fileList = document.querySelector('#fileList');
    //         fileList.innerHTML = '';
    //         var files = filesInput.files;
    //         for (var i = 0; i < files.length; i++) {
    //             var li = document.createElement('li');
    //             li.textContent = files[i].name;
    //             fileList.appendChild(li);
    //         }
    //     });
    // }
// 댓글 삭제
document.querySelectorAll('.comment-delete').forEach(deleteIcon => {
    deleteIcon.addEventListener('click', function() {
        const commentId = this.getAttribute('data-comment-id');
        if (confirm('정말로 이 댓글을 삭제하시겠습니까?')) {
            fetch(`/board/community/comment/${commentId}`, {
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

    inputLoginRequest.onclick = () => {
    if(confirm('로그인을 하신 후 이용해 주시기 바랍니다.')){
        location.href="/user/login";
    }
}