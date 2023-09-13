//체크박스 전체 선택
function selectAll(selectAll)  {
    const checkboxes 
    = document.querySelectorAll(".item");

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })

}

// 체크박스 하나라도 취소되면 전체선택 해제 
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



// 체크박스 글 삭제
function checkMultiDelete() {
	  // 선택된 목록 가져오기
	  const query = 'input[name="item"]:checked';
	  const selectedElements = 
	      document.querySelectorAll(query);
	  
	  // 선택된 목록의 갯수 세기
	  const selectedElementsCnt = selectedElements.length;
	  console.log(selectedElements[0].value);
	  if(selectedElementsCnt==0) {
	     console.log("확인");
	     alert("선택된 글이 없습니다.");
	     return false;
	  } else {
	     if (confirm("게시글을 삭제하시겠습니까?")) {
	        return true;
	     } else {
	        return false;
	     }
	  
	  } //if
	  
}


