
function selectAll(selectAll)  {
    const checkboxes 
    = document.getElementsByName("item");

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })

}

function checkSelectAll()   {
	// 전체 체크박스
	const checkboxes 
		= document.querySelectorAll('input[name="item"]');
	// 선택된 체크박스
	const checked 
		= document.querySelectorAll('input[name="item"]:checked');
	// select all 체크박스
	const selectAll 
		= document.querySelector('input[name="selectall"]');
	
	// 만약 체크박스가 하나라도 선택해제되면 전체선택 해제 
	if(checkboxes.length === checked.length)  {
		selectAll.checked = true;
	}else {
		selectAll.checked = false;
	}

}

