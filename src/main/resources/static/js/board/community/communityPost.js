const likeBtn = document.querySelector('#likeBtn');
const likeCountSpan = document.querySelector('.likeCount');

    likeBtn.addEventListener('click', function() {
        var articleId = likeBtn.getAttribute('data-article-id');
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/board/community/communityPost/' + articleId + '/like', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
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

    // 파일 업로드 미리보기 기능 (선택적)
    var filesInput = document.querySelector('#files');
    if (filesInput) {
        filesInput.addEventListener('change', function () {
            var fileList = document.querySelector('#fileList');
            fileList.innerHTML = '';
            var files = filesInput.files;
            for (var i = 0; i < files.length; i++) {
                var li = document.createElement('li');
                li.textContent = files[i].name;
                fileList.appendChild(li);
            }
        });
    }