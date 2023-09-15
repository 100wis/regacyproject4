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
           <!-- 게시판 목록 표시 -->
          	<div id="search">
          		검색 : <input type="text" name ="searchtitle" id="searchtitle"/>
          		<c:if test="${loginmember != null}" >        		
          		<span><input type="button" id="insert_button" value="글쓰기"></span>
          		</c:if>
          	</div>
           <table >
	
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
							<a href="#" onclick="dialogDetail(${board.boardid})">${board.title}</a>
							<input type="hidden" name="boardid" id="boardid" value="${board.boardid}"/> 
						</td>
		                <td>${board.writer_uid}</td>
		                <td>${board.reg_date}</td>
		                <td>${board.view_count}</td>
	                  </tr>
	                </c:forEach>
				</tbody>
				                
           	</table>
         

		<!-- 페이징 바 -->
           	<form id="pageForm" method="post" action="<c:url value='/board/list.do'/>" onsubmit="return false">
	           	<input type="hidden" name="startnum" id="startnum" />
	           	<input type="hidden" name="endnum" id="endnum" />
    			<div id="pagination" style="text-align: center;"></div>
			</form>
          
        <!-- 게시판 상세보기 다이얼로그  -->
        <div id="detailcontent">
        	<div id = titleinfo>
        		<h5> 글번호 : <span id="boardid2"></span></h5>
			  	<h2 id="title2"></h2>
			  	<p> 
			  		작성일 : <span id="reg_date2"></span>
				  	작성자 : <span id="writer_uid2"></span>
				  	조회수 : <span id="view_count2"></span>
				</p>	
			</div>
			<div id = contents2><p>${board.contents}</p></div>	
        </div>  
        

  <!-- 게시글 등록   -->
        <div id="insert_content_form" title="게시판 등록">
        	<input type="hidden" id="login_id" value="${loginmember.userid }"/>
	           <input type="text" id="title1" placeholder="제목">
	           <textarea id="contents1" placeholder="내용"></textarea>
       		</div>
       </div>	
        	

           	

       
<script src="<c:url value='/resources/js/check.js'/>"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>

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
     paginationHtml += '<input type="submit" id="pagenum" onclick="changePage('+ i + ')" value="' + i + '" /> ';
 }
 // 생성된 페이지 번호를 pagination div에 추가합니다.
 document.getElementById('pagination').innerHTML = paginationHtml;
}


window.onload = function() {
    generatePagination();
};


function changePage(pageNumber) {
	
    const startnum = (pageNumber - 1) * 10 + 1;
    const endnum = pageNumber * 10;
    
    console.log("startnum : " + startnum);
    console.log("endnum : " + endnum);

    // startnum과 endnum을 폼에 업데이트
    document.getElementById('startnum').value = startnum;
    document.getElementById('endnum').value = endnum;

    // 폼을 제출하는 기본 동작을 막고, 원하는 동작을 수행
    document.getElementById('pageForm').submit();
}



//게시판 내용 상세보기 다이얼로그



						$("#detailcontent").dialog({
						    autoOpen: false,
						    modal: true,
						    width: 800,
						    height: 500,
						    buttons: {
						    	"답글 작성하기" : Comment,
						    	"수정" : Update,
						        "삭제" : Delete,
						        Close: function() {
						            $(this).dialog("close");
						        }
						    }
						});
    
//게시글 등록 다이얼로그   

									$("#insert_content_form").dialog({
									    autoOpen: false,
									    modal: true,
									    width: 500,
									    height: 400,
									    buttons: {
									    	"등록" : Insert,
									        Close: function() {
									            $(this).dialog("close");
										        }
										    }
										});
							
									
	$("#insert_button").on("click", function () {
		$("#insert_content_form").dialog("open");
	});
    
    
//게시판 상세글 가져오기
function dialogDetail(boardid) {
	 	const boardid2 = document.querySelector("#boardid2");
		const title2 = document.querySelector("#title2");
		const contents2 = document.querySelector("#contents2");
		const writer_uid2 = document.querySelector("#writer_uid2");
		const reg_date2 = document.querySelector("#reg_date2");
		const view_count2 = document.querySelector("#view_count2");
		
		console.log("boardid2:", boardid2);
	    console.log("title2:", title2);
	    console.log("contents2:", contents2);
	    console.log("writer_uid2:", writer_uid2);
	    console.log("reg_date2:", reg_date2);
	    console.log("view_count2:", view_count2);
		
	
		console.log("boardid:" + boardid);
		
		const data = {
				boardid: boardid 
		      };
		
		console.log("data:" + data);

		 $.ajax({
		      url: "<c:url value='/board/detail.do'/>",
		      method: "POST",
		      contentType: "application/json; charset=UTF-8",
		      data: JSON.stringify(data),
		      success: function (data) {
		      
		           boardid2.innerText = data.boardid;  
		    	   title2.innerText = data.title;  
		    	   contents2.innerText = data.contents;  
		    	   writer_uid2.innerText = data.writer_uid;  
		    	   reg_date2.innerText = data.reg_date;  
		    	   view_count2.innerText = data.view_count;  
		    	   $("#detailcontent").dialog("open");
		        
		      },
		      error: function (error) {
		        console.error("Error:", error);
		      }
		    });
		
		return false;
		
	}	

function Insert() {
	const boardtitle =$("#insert_content_form #title1").val()
	const boardcontents =$("#insert_content_form #contents1").val()
	const writer_uid = $("#insert_content_form #login_id").val()
	
	console.log("boardtitle:" + boardtitle);
	console.log("boardcontents:" + boardcontents);
	console.log("writer_uid:" + writer_uid);
	
	const data = {
			title: boardtitle,
			contents: boardcontents,
			writer_uid: writer_uid	
	    };
	
	console.log("data:" + data);
	
	 $.ajax({
 	      url: "<c:url value='/board/insert.do'/>",
 	      method: "POST",
 	      contentType: "application/json; charset=UTF-8",
 	      data: JSON.stringify(data),
 	      success: function (json) {
 	        alert(json.message);
 	        location.href = "<c:url value='/board/list.do'/>";
 	      },
 	      error: function (error) {
 	        console.error("Error:", error);
 	      }
 	    });
}

function Comment(boardid) {}
function Update(boardid) {}
function Delete(boardid) {}


</script>
