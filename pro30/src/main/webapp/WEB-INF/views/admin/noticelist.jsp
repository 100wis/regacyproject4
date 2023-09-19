<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link rel="stylesheet" href="<c:url value='/resources/css/board/boardList.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/board/dialogboardlist.css' />"> 

  <style>
 
  </style>

<body>
          <h1 id="text">공지사항</h1>
 
           <form name="mForm" id="mForm" action="<c:url value='/notice/list.do'/>" method="post" >
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
	               	</tr>
				   	<c:forEach items="${result.list}" var="notice">
	                 <tr>
	                 	<td><input type="checkbox"  id= "cheked_num" class="checked_num" name="selectedBoards" value="${notice.noticeid}"></td>
		                <td>${notice.noticeid}</td>
						<td><a href="#" onclick="dialogDetail(${notice.noticeid})">${notice.title}</a>
							<input type="hidden" name="noticeid" id="noticeid" value="${notice.noticeid}"/></td>
		                <td>${notice.writer_uid}</td>
		                <td>${notice.reg_date}</td>
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
		
		
		
		
<!-- 공지사항 상세보기 다이얼로그  -->
        <div id="detailcontent">
        	<div id = titleinfo>
        		<h5> 글번호 : <span id="noticeid2"></span></h5>
			  	<h2 id="title2"></h2>
			  	<p> 
			  		작성일 : <span id="reg_date2"></span>
				  	작성자 : <span id="writer_uid2"></span>
				</p>	
			</div>
			<div id = contents2><p>${notice.contents}</p></div>
		</div>	
		
		
<script>
//전체 선택
function toggleAll(source) {
    var checkboxes = document.getElementsByName('selectedBoards');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = source.checked;
    }
}


/* 페이지 누르면 해당 페이지로 이동  */
function jsPageNo(pageNo) {
	
	console.log(pageNo);
		
   document.querySelector("#pageNo").value = pageNo;
   document.querySelector("#mForm").submit(); 
}

function titleSearch() {  
	
	const searchtext = $("#searchTitle").val() ;
	console.log("searchtext : " + searchtext);

	// 검색할 제목을 폼에 업데이트
	document.getElementById('searchTitle').value = searchtext;
	// 폼을 제출하는 기본 동작을 막고, 원하는 동작을 수행
	document.getElementById('Searchform').submit();
}


//공지사항 상세보기 다이얼로그
$("#detailcontent").dialog({
    autoOpen: false,
    modal: true,
    width: 800,
    height: 500,
    buttons: {
        Close: function() {
            $(this).dialog("close");
        }
    },
    close: function() {
        // 다이얼로그가 닫힐 때도 removeAllReplies() 함수 호출
    }

});

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
		    	   
		    	   $("#detailcontent").dialog("open");
		        
		      },
		      error: function (error) {
		        console.error("Error:", error);
		      }
		    });
		
		return false;
		
	}	

</script>		
			
</body>   
</html>
