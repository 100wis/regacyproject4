<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link rel="stylesheet" href="<c:url value='/resources/css/board/boardList.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/board/dialogboardlist.css' />"> 

  <style>
 
  </style>

<body>
		<h5 id="text">* 관리자 페이지 입니다*</h5>
          <h1 id="text">공지사항</h1>
 
           <form name="mForm" id="mForm" action="<c:url value='/admin/notice/list.do'/>" method="post" >
           		<input type="hidden" id="pageNo" name="pageNo" />  
           	</form>
           <%-- 
			<input type="hidden" name="pageNo" id="pageNo" value="${result.notice.pageNo}" />
				<div style="margin:0px auto;">
					<div style="display: flex; margin:0px auto; width:70%; justify-align: center">
						<label>건수: </label>
						<select name="pageLength" id="pageLength" >
							<option value="10" ${result.notice.pageLength == 10 ? 'selected="selected"' : ''} >10건</option>
							<option value="20" ${result.notice.pageLength == 20 ? 'selected="selected"' : ''} >20건</option>
							<option value="50" ${result.notice.pageLength == 50 ? 'selected="selected"' : ''} >50건</option>
							<option value="100" ${result.notice.pageLength == 100 ? 'selected="selected"' : ''} >100건</option>
						</select> --%>

	<!-- 검색 -->
          	<div id="search">
          		<form id="Searchform" method="post", action="<c:url value='/notice/titlesearch.do'/>" >
	          		<span>
		          		<input type="button" id="Searchbutton"  value="검색" onclick="titleSearch()">
		          		<input type="text" name="searchTitle" id="searchTitle"/>
	          		</span>
          		</form>
          	</div>
           <table >
	
	<!-- 공지사항 목록 표시 -->
	            <tbody id="boardList">
	            	<tr>
	            	  	<th>전체 선택<input type="checkbox" name="selectAll"  onclick="toggleAll(this)"></th>
	            		 <th>글번호</th>
		                 <th>제목</th>
		                 <th>작성자</th>
		                 <th>작성날짜</th>
		                 <th>고정여부</th>
	               	</tr>
				   	<c:forEach items="${result.list}" var="notice">
	                 <tr>
	                 	<td><input type="checkbox"  id= "cheked_num" class="checked_num" name="selectedBoards" value="${notice.noticeid}" onclick=" handleCheckboxChange()"></td>
		                <td>${notice.noticeid}</td>
						<td><a href="#" onclick="dialogDetail(${notice.noticeid})">${notice.title}</a>
							<input type="hidden" name="noticeid" id="noticeid" value="${notice.noticeid}"/></td>
		                <td>${notice.writer_uid}</td>
		                <td>${notice.reg_date}</td>
		                <td id="fixed_yn">${notice.fixed_yn}</td>
	                  </tr>
	                </c:forEach>
				</tbody>        
           	</table>
           	
  <!-- 페이징 바 -->
  		<div style="text-align: center;margin-top:20px;">
           	<c:if test="${result.notice.navStart != 1}">
           		<a href="javascript:jsPageNo(${result.notice.navStart-1})"> &lt; </a> 
           	</c:if>
           	<c:forEach var="item" begin="${result.notice.navStart}" end="${result.notice.navEnd}">
           		<c:choose>
           			<c:when test="${result.notice.pageNo != item }">
           				<a id="pagenums"  href="javascript:jsPageNo(${item})">${item}</a>
           			</c:when>
           			<c:otherwise>
           				<strong id="strong" >${item}</strong>   
           			</c:otherwise>
           		</c:choose>
           	</c:forEach>
           	<c:if test="${result.notice.navEnd != result.notice.totalPageSize}">
           		<a href="javascript:jsPageNo(${result.notice.navEnd+1})"> &gt; </a> 
           	</c:if>
          	
        </div>
		
		<c:if test="${isSearched == true}" >    
			<a href="<c:url value='/board/list.do'/>">전체 게시판 목록으로 돌아가기</a>
		</c:if>

 <!-- CRUD 버튼 -->		
		<div id="admin_button">
			<input type="button" id ="insert_button" class="noticeCRUDbutton" value="추가">
			<input type="button" id ="delete_button" value="삭제" class="noticeCRUDbutton"  onclick="deleteCheck()">
			<input type="button" id ="fix_button" value="고정" class="noticeCRUDbutton"   style="display: none;" onclick="fixes()">
			<input type="button" id ="none_fix_button" value="고정해제" class="noticeCRUDbutton"   style="display: none;" onclick="none_fixes()">
		</div>
		
		
		
		
<!-- 공지사항 상세보기 다이얼로그  -->
        <div id="detailcontent" style="display: none;">
        	<div id = titleinfo>
        		<h5> 글번호 : <span id="noticeid2"></span></h5>
			  	<h2 id="title2"></h2>
			  	<p> 
			  		작성일 : <span id="reg_date2"></span>
				  	작성자 : <span id="writer_uid2"></span>
				</p>	
			</div>
			<div id = contents2><p>${notice.contents}</p></div>
			<div id="radio">
			<label for="fixed_yn">고정 여부:</label>
			y : <input type="radio" id="fixed_y" name="fixed_yn" value="y">
			n : <input type="radio" id="fixed_n" name="fixed_yn" value="n">
			</div>
		</div>	
		
<!-- 공지사항 추가 다이얼로그  -->
  		<div id="insert_content_form" title="공지사항 등록" style="display: none;" >
	        <input type="hidden" id="login_id" name ="writer_uid" value="${loginmember.userid}"/>
		    <input type="text" id="title1" name="title" placeholder="공지사항 제목">
		    <textarea id="contents1" name="contents" placeholder="공지사항 내용"></textarea>
		    <label for="fixed_y">고정여부: </label>
			<input type="radio" id="fixed_y" name="fixed_yn" value="Y">
			<label for="fixed_y">Y</label>
			<input type="radio" id="fixed_n" name="fixed_yn" value="N">
			<label for="fixed_n">N</label>
       	</div>
   
 <!-- 공지사항 수정하기   -->
        <div id="notice_update" title="공지사항 수정하기" style="display: none;">
	        <input type="text" id="title4" placeholder="제목">
	        <textarea id="contents4" placeholder="내용"></textarea>
	        <label for="fixed_yn">고정 여부:</label>
			y : <input type="radio" id="fixed_y_update" name="fixed_yn_update" value="y">
			n : <input type="radio" id="fixed_n_update" name="fixed_yn_update" value="n">
       	</div>
		
		
<script>

//공지사항 상세보기 다이얼로그
$("#detailcontent").dialog({
    autoOpen: false,
    modal: true,
    width: 800,
    height: 500,
    buttons: {
    	"수정" : function(){   		
	    		const fixed_y = $("#fixed_y");
	            const fixed_n = $("#fixed_n");
    			 	
    			
    			 const fixed_y_update = $("#fixed_y_update");
    	            const fixed_n_update = $("#fixed_n_update");

    	            if (fixed_y.prop("checked")) {
    	                fixed_y_update.prop("checked", true);
    	            } else if (fixed_n.prop("checked")) {
    	                fixed_n_update.prop("checked", true);
    	            }
    	            // 수정 폼 열기만 진행
    	            $("#notice_update").dialog("open");  
    	},
    	"삭제": deleteOne,
        Close: function() {
            $(this).dialog("close");
        }
    }
});

//공지사항 등록 다이얼로그   
$("#insert_content_form").dialog({
    autoOpen: false,
    modal: true,
    width: 500,
    height: 400,
    buttons: {
    	"등록": Insert,
        Close: function() {
            $(this).dialog("close");
	        }
	    }
	});

//공지사항 등록 오픈								
$("#insert_button").on("click", function () {
	$("#insert_content_form").dialog("open");
});


//공지사항 수정 다이얼로그
$("#notice_update").dialog({
    autoOpen: false,
    modal: true,
    width: 800,
    height: 500,
    buttons: {
        "수정": Update,
        Close: function () {
            $(this).dialog("close");
        }
    }
});


//전체 선택 체크박스
function toggleAll(source) {
    var checkboxes = document.getElementsByName('selectedBoards');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = source.checked;
    }
}

// 고정된 공지사항 배경색 바꾸기
window.addEventListener("load", function(){
	var fixedYNcells = document.querySelectorAll("#fixed_yn");
	
	fixedYNcells.forEach(function(fixedYNcell){
		if(fixedYNcell.textContent.trim() ==="Y"){
			var row = fixedYNcell.parentNode;
			row.style.backgroundColor = "Mistyrose";
		}
	});
});


/* 페이지 누르면 해당 페이지로 이동  */
function jsPageNo(pageNo) {
	
	console.log(pageNo);
		
   document.querySelector("#pageNo").value = pageNo;
   document.querySelector("#mForm").submit(); 
}

//검색
function titleSearch() {  
	
	const searchtext = $("#searchTitle").val() ;
	console.log("searchtext : " + searchtext);

	// 검색할 제목을 폼에 업데이트
	document.getElementById('searchTitle').value = searchtext;
	// 폼을 제출하는 기본 동작을 막고, 원하는 동작을 수행
	document.getElementById('Searchform').submit();
}

//공지사항 상세글 가져오기
function dialogDetail(noticeid) {
	
	
	 	const noticeid2 = document.querySelector("#noticeid2");
		const title2 = document.querySelector("#title2");
		const contents2 = document.querySelector("#contents2");
		const writer_uid2 = document.querySelector("#writer_uid2");
		const reg_date2 = document.querySelector("#reg_date2");


		console.log("noticeid2:", noticeid2);
		console.log("title2:", title2);
		console.log("contents2:", contents2);
		console.log("writer_uid2:", writer_uid2);
		console.log("reg_date2:", reg_date2);
		
		const data = {
				noticeid: noticeid 
		      };
		
		console.log("data:" , data);

		 $.ajax({
		      url: "<c:url value='/notice/detail.do'/>",
		      method: "POST",
		      contentType: "application/json; charset=UTF-8",
		      data: JSON.stringify(data),
		      success: function (json) {
		      
		           noticeid2.innerText = json.noticeid;  
		    	   title2.innerText = json.title;  
		    	   contents2.innerText = json.contents;  
		    	   writer_uid2.innerText = json.writer_uid;  
		    	   reg_date2.innerText = json.reg_date;  

		    	   
		    	   if (json.fixed_yn === "Y") {
		    		    $("#fixed_y").prop("checked", true);
		    		} else if (json.fixed_yn === "N") {
		    		    $("#fixed_n").prop("checked", true);
		    		}
		    	   
		    	   $("#detailcontent").dialog("open");
		        
		      },
		      error: function (error) {
		        console.error("Error:", error);
		      }
		    });
		
		return false;		
	}	

//공지사항 등록
function Insert() {
	const writer_uid = '${loginmember.userid}'
	const contents = document.getElementById("contents1").value;
	const title = document.getElementById("title1").value;
	const fixed_yn =document.querySelector('input[name="fixed_yn"]:checked').value;
	
	console.log("writer : ", writer_uid);
	console.log("contents : ", contents);
	console.log("title : ", title);
	console.log("fixed_yn : ", fixed_yn);
	
	const data = {
			title : title,
			contents: contents,
			writer_uid: writer_uid,
			fixed_yn: fixed_yn
	}
	
	 $.ajax({
	      url: "<c:url value='/notice/insert.do'/>",
	      method: "POST",
	      contentType: "application/json; charset=UTF-8",
	      data: JSON.stringify(data),
	      success: function (json) {
	    	  if(json.status == true){
	    		  alert(json.message)	
	    		   // 등록 성공 시 다이얼로그를 닫습니다.
	               	 $("#insert_content_form").dialog("close");
		    		  location.href = "<c:url value='/admin/notice/list.do'/>";
	    	  }else{
	    		  alert(json.message)	 
	    		
	    	  }

	      },
	      error: function (error) {
	        console.error("Error:", error);
	      }
	    });
	
	 
	 return false;
}	

// 공지사항 수정
function Update() {
		const noticeid = $("#noticeid2").text()
		const contents = document.getElementById("contents4").value;
		const title = document.getElementById("title4").value;
		const fixed_yn_ee =document.querySelector('input[name="fixed_yn_update"]:checked').value;
		const fixed_yn = fixed_yn_ee.toUpperCase();
		
		console.log("noticeid : ", noticeid);
		console.log("contents : ", contents);
		console.log("title : ", title);
		console.log("fixed_yn : ", fixed_yn);
		
		const data = {
				noticeid : noticeid,
				title : title,
				contents: contents,
				fixed_yn: fixed_yn
		}
		
		 $.ajax({
		      url: "<c:url value='/notice/update.do'/>",
		      method: "POST",
		      contentType: "application/json; charset=UTF-8",
		      data: JSON.stringify(data),
		      success: function (json) {
		    	  if(json.status == true){
		    		  alert(json.message)	
		    		   // 수정 성공 시 다이얼로그를 닫습니다.
		               	 $("#notice_update").dialog("close");
		    		  	dialogDetail(noticeid)
		    	  }else{
		    		  alert(json.message)	 
		    		
		    	  }

		      },
		      error: function (error) {
		        console.error("Error:", error);
		      }
		    });	
}

function deleteOne(){
	const noticeid = $('#noticeid2').text();
	
	const data ={
			noticeid: noticeid
	};
	
	 $.ajax({
	      url: "<c:url value='/notice/deleteOne.do'/>",
	      method: "POST",
	      contentType: "application/json; charset=UTF-8",
	      data: JSON.stringify(data),
	      success: function (json) {
	    	  if(json.status == true){
	    		  alert(json.message)	
	    		   // 삭제 성공시 다이얼로그를 닫습니다.
	               	 $("#notice_update").dialog("close");
	    	  }else{
	    		  alert(json.message)	 
	    		
	    	  }

	      },
	      error: function (error) {
	        console.error("Error:", error);
	      }
	    });	
}



function deleteCheck() {
	  const ids = [];
	  const items = document.querySelectorAll(".checked_num:checked");

	  items.forEach(item => {
	    ids.push(item.value); // 배열에 체크된 항목의 값을 추가
	  });

	  console.log("ids: ", ids); // ids 배열을 확인하기 위한 디버깅 메시지

	  const param = {
	    ids: ids
	  };

	  fetch("<c:url value='/notice/deleteCheck.do'/>", {
	    method: "POST",
	    headers: {
	      "Content-Type": "application/json; charset=UTF-8",
	    },
	    body: JSON.stringify(param),
	  })
	  .then((response) => response.json())
	  .then((json) => {
		  console.log(json.status);
		  console.log(json)
	    if (json.status) {
	    	alert(json.message)
	      location.href = "<c:url value='/admin/notice/list.do'/>";
	    }
	  });
	}
	
// 고저 여부에 따라 고정 , 고정해제 버튼 표시	
function handleCheckboxChange() {
	
	const deleteButton = document.getElementById("delete_button");
	const fixButton = document.getElementById("fix_button");
	const noneFixButton = document.getElementById("none_fix_button");

	// '고정' 버튼과 '고정해제' 버튼 초기화
/* 	fixButton.style.display = "none";
	noneFixButton.style.display = "none"; */
	
	
	  const checkedRows = document.querySelectorAll(".checked_num:checked");

	  // 선택된 체크박스 중에서 '고정여부'가 'Y'인 것과 'N'인 것을 구분하여 처리
	  let hasFixed = false;
	  let hasNoneFixed = false;

	  checkedRows.forEach((row) => {
	    const fixedYN = row.closest("tr").querySelector("#fixed_yn").textContent;
	    if (fixedYN === "Y") {
	      hasFixed = true;
	    } else if (fixedYN === "N") {
	      hasNoneFixed = true;
	    }
	  });

	  // '고정' 버튼과 '고정해제' 버튼 활성화 여부 결정
	  if (hasFixed && !hasNoneFixed) {
	    noneFixButton.style.display = "inline-block"; // '고정해제' 버튼 활성화
	    fixButton.style.display = "none"; // '고정' 버튼 비활성화
	  } else if (!hasFixed && hasNoneFixed) {
	    noneFixButton.style.display = "none"; // '고정해제' 버튼 비활성화
	    fixButton.style.display = "inline-block"; // '고정' 버튼 활성화
	  } else {
	    noneFixButton.style.display = "none"; // 둘 다 비활성화
	    fixButton.style.display = "none";
	  }
	}
	
//여러건 고정	
function fixes() {
	  const ids = [];
	  const items = document.querySelectorAll(".checked_num:checked");

	  items.forEach(item => {
	    ids.push(item.value); // 배열에 체크된 항목의 값을 추가
	  });

	  console.log("ids: ", ids); // ids 배열을 확인하기 위한 디버깅 메시지

	  const param = {
	    ids: ids
	  };

	  fetch("<c:url value='/notice/fixes.do'/>", {
	    method: "POST",
	    headers: {
	      "Content-Type": "application/json; charset=UTF-8",
	    },
	    body: JSON.stringify(param),
	  })
	  .then((response) => response.json())
	  .then((json) => {
		  console.log(json.status);
		  console.log(json)
	    if (json.status) {
	    	alert(json.message)
	      location.href = "<c:url value='/admin/notice/list.do'/>";
	    }
	  });
	}
	
	
//여러건 해제	
function none_fixes() {
	  const ids = [];
	  const items = document.querySelectorAll(".checked_num:checked");

	  items.forEach(item => {
	    ids.push(item.value); // 배열에 체크된 항목의 값을 추가
	  });

	  console.log("ids: ", ids); // ids 배열을 확인하기 위한 디버깅 메시지

	  const param = {
	    ids: ids
	  };

	  fetch("<c:url value='/notice/none_fixes.do'/>", {
	    method: "POST",
	    headers: {
	      "Content-Type": "application/json; charset=UTF-8",
	    },
	    body: JSON.stringify(param),
	  })
	  .then((response) => response.json())
	  .then((json) => {
		  console.log(json.status);
		  console.log(json)
	    if (json.status) {
	    	alert(json.message)
	      location.href = "<c:url value='/admin/notice/list.do'/>";
	    }
	  });
	}


</script>		
			
</body>   
</html>
