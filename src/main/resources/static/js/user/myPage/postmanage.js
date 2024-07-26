// checkbox 전체선택
function selectAll(selectAll)  {
    const checkboxes= document.querySelectorAll('tbody input[type="checkbox"]');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked
    })
}

const deleteBtn = document.querySelector('.delete-btn');
const manageRows = document.querySelectorAll('.manage-row');

const articleIds = [];



deleteBtn.onclick = () => {
    manageRows.forEach(manageRow => {
        if (manageRow.querySelector('td:first-child > input').checked) {
            articleIds.push(manageRow.dataset.id);
        }
    })

    fetch(`/myPage/postmanage`, {
        method : 'DELETE',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(articleIds)

    }).then(response => {
        if (response.status === 401){
            alert('로그인 이후 재시도 해주세요')
        }else {
            alert('게시글 삭제 완료!')
            location.reload();
        }
    })
}
