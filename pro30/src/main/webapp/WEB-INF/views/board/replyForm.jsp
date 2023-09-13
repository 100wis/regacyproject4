<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<c:url value='/resources/css/board/boardList.css' />">

<style>
    /* 링크 스타일 */
    a {
      text-decoration: none;
    }

    a:link { text-decoration: black;}
 	a:visited { text-decoration: black;}
</style>

	<h1 id="text">회원 게시판</h1>
	<div id="container">
	    <div id="insert_form" title="게시판 상세보기">
	    	<form name="mForm" method="post" action="<c:url value='/board/reply.do'/>" enctype="multipart/form-data">
	    		<input type="hidden" name="pid" value="${board.boardid}">
	    		<input type="text" name="title" ><br/>
	    		<textarea name="contents"></textarea>
	    		
				<input type="button"  value="파일추가" onClick="fn_addFile()"/><br>
				<div id="d_file"></div>
								   
				<div style="display: flex; justify-content: flex-end;">
			       <button type="submit">저장</button>
				</div>
	       </form>
	    </div>
	</div>       
       
<script src="<c:url value='/resources/js/check.js'/>"></script>

<script type="text/javascript">

var cnt=1;
function fn_addFile(){
	$("#d_file").append("<br>"+"<input  type='file' name='file"+cnt+"' />");
	cnt++;
}


document.querySelector("#deleteBtn").addEventListener("click", e => {
	//이벤트 핸들러의 기본 동작을 취소한다
	e.preventDefault();
	
	if (!confirm(" 삭제하시겠습니까?")) return false;
	
	const param = {
		noticeid: document.querySelector("#boardid").value,
	};

    fetch("<c:url value='/board/delete.do'/>", {
      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify(param),
    })
    .then((response) => response.json())
    .then((json) => {
  	  alert(json.message);
        if (json.status) {
      	  location.href = "<c:url value='/board/list.do'/>"; 
        }
    });
	
	return false;
})


document.querySelector("#moreBtn").addEventListener("click", e => {
	//이벤트 핸들러의 기본 동작을 취소한다
	e.preventDefault();
	
	const param = {
	        boardid: document.querySelector("#boardList tr:last-child td:first-child").innerText,
	      };

	      fetch("<c:url value='/board/ajaxList.do'/>", {
	        method: "POST",
	        headers: {
	          "Content-Type": "application/json; charset=UTF-8",
	        },
	        body: JSON.stringify(param),
	      })
	      .then((response) => response.json())
	      .then((json) => {
	    	  console.log(json.list);
	    	  let html = "";
	          if (json.status) {
	        	  const boardList = json.list;
	        	  const boardItem = document.querySelector("#boardItem");
	        	  const boardListHTML = document.querySelector("#boardList");
	        	  
	        	  for (let i=0;i<boardList.length;i++) {
		        	  const board = boardList[i];
		        	  const newBoardItem = boardItem.cloneNode(true);
		        	  
		        	  newBoardItem.querySelector("#boardid").innerText = board.boardid; 
		        	  newBoardItem.querySelector("#title").innerText = board.title; 
		        	  newBoardItem.querySelector("#writer_uid").innerText = board.writer_uid; 
		        	  newBoardItem.querySelector("#reg_date").innerText = board.reg_date; 
		        	  newBoardItem.querySelector("#view_count").innerText = board.view_count; 

		        	  newBoardItem.style.display = "";
		        	  boardListHTML.appendChild(newBoardItem);
	        	  }


	          }
	      });
	
	return false;
	
})



// 다이얼로그

$( function() {
    var dialog, editDialog, insertDialog;
    boardid = $("#boardid"),
    title = $("#title"),
    contents = $("#contents");
    
function updateTips(t){
	tips
	.text(t)
	.addClass("ui-state-highlight");
	setTimeout(function(){
		tisp.removeClass("ui-state-highlight", 1500);
	}, 800);
}

//입력확인
function checkLength( o, n, min, max){
	if( o.val().length > max || o.val().length < min ){
		o.addClass("ui-state-error");
		updateTips("Length of " + n + "must be between"+
				min + "and" +max + ".");
		return false;
		
		} else {
			
			return true;
		}
	}

function checkRegexp(o, regexp, n){
	if(!(regexp.test(o.val()))){
		o.addClass("ui-state-error");
		updateTips(n);
		return false;
	}else{
		return true;
	}
}

function addUser(){
	var valid = true;
	allFields.removeClass("ui-state-error");
	
	valid = valid && checkLength(boardid, "boardid");
	valid = valid && checkLength(title, "title");
	valid = valid && checkLength(contents, "contents");
	
	if(valid){
		$("#board tbody").append("<tr>"+
				"<td>" + boardid.val() + "</td>" +
				"<td>" + title.val() + "</td>" +
				"<td>" + contents.val() + "</td>" +
				"</tr>");
		dialog.dialog("close");
	}
	return valid;
}

  //팝업창
  $(document).ready(function() {
    var dialog, form;

    dialog = $("#dialog_form").dialog({
      autoOpen: false,
      height: 500,
      width: 800,
      modal: true,
      buttons: {
        Close: function() {
          dialog.dialog("close");
        }
      }
    });

    editDialog = $("#update_form").dialog({
    	autoOpen: false,
    	height: 500,
    	width: 800,
    	model: true,
    	buttons: {
    		Close: function(){
    			editDialog.dialog("close");
    		}
    	}
    });
    
    insertDialog = $("#insert_form").dialog({
    	autoOpen: false,
    	height: 500,
    	width: 800,
    	model: true,
    	buttons: {
    		Close: function(){
    			editDialog.dialog("close");
    		}
    	}
    });
    
    $(document).on("click", ".viewpopup", function(event) {
      event.preventDefault();

      var boardid = $(this).data("boardid");
      const param = {
        boardid: boardid
      };

      
      fetch("<c:url value='/board/ajaxList.do'/>", {
        method: "POST",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(param)
      })
      .then(response => response.json())
      .then(json => {
          $("#boardid").text(json.boardid);
          $("#title").text(json.title);
          $("#contents").text(json.contents);
          $("#writer_uid").text(json.writer_uid);
          $("#reg_date").text(json.reg_date);
          $("#view_count").text(json.view_count);
        dialog.dialog("open");
      });
      
    });
    
    
    //수정 버튼 클릭시
    $(document).on("click", "#update", function(){
    	var title = $("#title").text();
    	var contents = $("#contents").text();
    	
    	$("#title").val(title);
    	$("#contents").val(contents);
    	
    	editDialog.dialog("open");
    });
    
    
    //저장 버튼 클릭 시
    $(document).on("click", "#saveBtn", function(){
    	var editedTitle = $("#title").val();
    	var editedContents = $("#contents").val();
    	var boardid = $("#boardid").text();
    	
    	var postData = {
    			boardid: boardid,
    			title: editedTitle,
    			contents: editedContents
    	};
    	
    	fetch("<c:url value='/board/update.do'/>",{
    		 method: "POST",
    	        headers: {
    	          "Content-Type": "application/json; charset=UTF-8",
    	        },
    	        body: JSON.stringify(postData)
    	      })
    	      .then(response => response.json())
    	      .then(data =>{
    	    	  if(data.status){
    	    	  alert(data.message);
    	    	  $("#title").text(editedTitle);
    	    	  $("#contents").text(editedContents);
    	    	  }
    	      });
    	
    	editDialog.dialog("close");
    	
    });
    
    // 등록하기
    $(document).on("click", "#insert", function(){
    	insertDialog.dialog("open");
    });
    
    // 등록 창 닫기 버튼 클릭 시
    $(document).on("click", "#closeBtn", function() {
        insertDialog.dialog("close");
    });
    	
    
    // 등록 저장하기
    $(document).on("click", "#saveBtn", function(){
    	var insertTitle = $("#title").val();
    	var insertContents = $("#contents").val();
    	var boardid = $("#boardid").text();
    	
    	var postData = {
    			boardid : boardid,
    			title : insertTitle,
    			contents : insertContents
    	};
    	
    	fetch("<c:url value='/board/insert.do'/>",{
    		 method: "POST",
    	        headers: {
    	          "Content-Type": "application/json; charset=UTF-8",
    	        },
    	        body: JSON.stringify(postData)
    	      })
    	      .then(response => response.json())
    	      .then(data =>{
    	    	  if(data.status){
    	    	  alert(data.message);
    	    	  $("#boardid").text(boardid);
    	    	  $("#title").text(insertTitle);
    	    	  $("#contents").text(insertContents);
    	    	  location.href = '<c:url value="/board/list.do"/>';
	   	    	  }
    	      });
    	
    	insertDialog.dialog("close");
    	
    	});
    	
    });

});

</script>
