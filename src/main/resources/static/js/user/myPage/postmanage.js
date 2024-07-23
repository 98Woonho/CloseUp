// checkbox 전체선택
function selectAll(selectAll)  {
    const checkboxes= document.querySelectorAll('input[type="checkbox"]');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked
    })
}

