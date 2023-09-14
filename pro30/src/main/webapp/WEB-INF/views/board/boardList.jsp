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
           <!-- 게시판 목록 표시 -->
           
           <form action="<c:url value='/board/deletes.do' />" method="post" onsubmit="return checkMultiDelete()">
           
           <table >
           	
	           	<tr id="boardItem" style="display:none">
	                <td id="boardid"></td>
	                <td ><a href="boardGet?boardid=${boardid}" id="title"></a></td>
	                <td id="writer_uid"></td>
	                <td id="reg_date"></td>
	                <td id="view_count"></td>
	            </tr>
	            
	            <tbody id="boardList">
	            	<tr>
	            		 <th>글번호</th>
		                 <th>제목</th>
		                 <th>작성자</th>
		                 <th>작성날짜</th>
		                 <th>조회수</th>
	               	</tr>
				   	<c:forEach items="${boardList}" var="board">
	                  <tr>
		                <td>${board.boardid}</td>
						<td style="text-align: left">
							<span style="padding-left:${(board.level-1)*20}px"></span>
							${board.level != 1 ? "[답변] " : ""}
							<a href="<c:url value='/board/detail.do?boardid=${board.boardid }'/>">${board.title}</a>
						</td>
		                <td>${board.writer_uid}</td>
		                <td>${board.reg_date}</td>
		                <td>${board.view_count}</td>
	                  </tr>
	                </c:forEach>
				</tbody>
				                
           	</table>
           	
           	<div id="pagination" style="text-align: center;"></div>
           	
<%--            	<!-- 로그인한 사람만 작성 -->
           	<c:if test="${loginMember != null}">
	           	<div class="buttons">
		           	<button class="button"><a href="<c:url value='/board/insertForm.do'/>">글 작성</a></button>
		           	<button class="button" id="insert_form">글 작성</button>
		           	<!-- 로그인한 사람만 작성 -->
		            <c:if test="${loginMember.id=='admin'}">
			       	 	<button class="button" type="submit">글 삭제</button>
			        </c:if>
	            </div>
	        </c:if>
			</form>
            </div> --%>
       
       
<script src="<c:url value='/resources/js/check.js'/>"></script>

<script type="text/javascript">

//페이지당 항목 수와 현재 페이지 번호를 설정합니다.
var itemsPerPage = 10;
var currentPage = 1;

//전체 항목 수를 JavaScript 변수로 가져옵니다.
var totalItems = parseInt("${totalcount}");
console.log(totalItems);

//총 페이지 수를 계산합니다.
var totalPages = Math.ceil(totalItems / itemsPerPage);
console.log(totalPages);


//페이징 번호를 생성하는 함수를 정의합니다.
function generatePagination() {
 var paginationHtml = '';
 for (var i = 1; i <= totalPages; i++) {
     // 각 페이지 번호를 클릭할 때 해당 페이지로 이동하도록 설정합니다.
     paginationHtml += '<input type="button" id="pagenum" onclick="changePage(' + i + ')" value="' + i + '" />';
 }
 // 생성된 페이지 번호를 pagination div에 추가합니다.
 document.getElementById('pagination').innerHTML = paginationHtml;
}


window.onload = function() {
    generatePagination();
};


function changePage(pageNumber) {
    // pageNumber를 컨트롤러로 보내고 새로운 보드 리스트를 가져오는 AJAX 요청을 수행합니다.
	const startnum = pageNumber *10 ;
    const endnum =startnum +10;
    
    console.log("startnum:", startnum);
    console.log("endnum:", endnum);

    // 서버로 보낼 데이터 객체 생성
    const data = {
    		startnum: startnum,
    		endnum: endnum
    };
    
    console.log("data:", data);
    
    $.ajax({
	      url: "<c:url value='/board/list.do'/>",
	      method: "POST",
	      contentType: "application/json; charset=UTF-8",
	      data: JSON.stringify(data),
	      success: function (json) { 
	      },
	      error: function (error) {
	        console.error("Error:", error);
	      }
	    });
}

</script>
