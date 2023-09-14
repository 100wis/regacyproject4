<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <div id="slideShow">
      <div id="slides">
        <img src= "<c:url value = "/resources/images/main1.png"/>" alt="" > 
        <img src="<c:url value = "/resources/images/main2.png"/>" alt="">
        <img src="<c:url value = "/resources/images/main3.jpg"/>" alt="">
        <button id="prev">&lang;</button>
        <button id="next">&rang;</button>
      </div>
    </div>
    <div id="contents">
      <div id="tabMenu">
        <input type="radio" id="tab1" name="tabs" checked>
        <label for="tab1">공지사항</label>
        <input type="radio" id="tab2" name="tabs">
        <label for="tab2">자유게시판</label>      
        <div id="notice" class="tabContent">
          <h2>공지사항</h2>
          <ul>
           <c:forEach var="notice" items="${noticeTop5}">
			 <li><a href="#" onclick="dialogDetail(${notice.noticeid})">${notice.title}</a></li>
		   </c:forEach>
          </ul>
          <a class="morebutton"  href="<c:url value ="/notice/list.do"/>"> 더보기</a>    
        </div>
        <div id="gallery" class="tabContent">
      	 <h2>자유 게시판</h2>
          <ul>
          	 <c:forEach var="board" items="${boardTop5}">
			    <li><a href="<c:url value='board?boardid=${boardid}'/>">${board.title}</a></li>
			 </c:forEach>
          </ul>
          <a class="morebutton" href="<c:url value ="/board/list.do"/>"> 더보기</a>
        </div>        
      </div>
      <div id="links">
        <ul>
          <li>
            <a href="#">
              <span id="quick-icon1"></span>
              <p>패션 매거진</p>
            </a>
          </li>
          <li>
            <a href="#">
              <span id="quick-icon2"></span>
              <p>이달의 후기왕</p>
            </a>            
          </li>
          <li>
            <a href="#">
              <span id="quick-icon3"></span>
              <p>문의사항</p>
            </a>            
          </li>
        </ul>
      </div>
    </div>  
        
        
	<div id="detail" title="글 상세보기" >
			<div class="detail">
			  	<input type="hidden" name="noticeid" id="noticeid2" value="${notice.noticeid}"/> 
		      	<h3 id="title2">[  ] </h3>
		      	<hr>
			      <br>
			      <div class="meta-info" id="info2"">
			  		작성자 :  <span id="writer_uid2">${notice.writer_uid}</span>  |　 작성날짜 :  <span id="reg_date2">${notice.reg_date}</span>　 |　 조회수 :  <span id="view_count2">${notice.view_count}</span>
				  </div>
			      <div class="contents" id="contents2">
			    	${notice.contentsHTML}
				  </div>
			</div>
		</div> 
		
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>	
<script src="<c:url value='/resources/js/slideshow.js'/>"></script>
<script type="text/javascript">
	/* 공지사항 상세 */
	$(document).ready(function() {
		$("#detail").dialog({
		    autoOpen: false,
		    modal: true,
		    width: 800,
		    height: 500,
		    buttons: {
		        "수정": function() {
		        	
		        	const title = $("#title2").text();
		        	const contents = $("#contents2").text();
		        	
		        	$("#title3").val(title);
		        	$("#contents3").val(contents);
		        	console.log(title);
		        	console.log(contents);
		        	
		            $("#update").dialog("open");
		            
		        },
		        "삭제": function() {
		            dialogDelete(); // noticeid를 함수에 전달
		        },
		        Close: function() {
		            $(this).dialog("close");
		        }
		    }
		});
	});
	
	/* 글 상세 패치 코드 */
	function dialogDetail(noticeid) {
		alert("asdasdas");
	 	const noticeid2 = document.querySelector("#noticeid2");
		const title2 = document.querySelector("#title2");
		const contents2 = document.querySelector("#contents2");
		const writer_uid2 = document.querySelector("#writer_uid2");
		const reg_date2 = document.querySelector("#reg_date2");
		const view_count2 = document.querySelector("#view_count2");

		
		const param = {
		        noticeid: noticeid
		      };

		      fetch("<c:url value='/notice/detail.do'/>", {
		        method: "POST",
		        headers: {
		          "Content-Type": "application/json; charset=UTF-8",
		        },
		        body: JSON.stringify(param),
		      })
		      .then((response) => response.json())
		      .then((json) => {
		    	   noticeid2.innerText = json.noticeid;  
		    	   title2.innerText = json.title;  
		    	   contents2.innerText = json.contents;  
		    	   writer_uid2.innerText = json.writer_uid;  
		    	   reg_date2.innerText = json.reg_date;  
		    	   view_count2.innerText = json.view_count;  
		       	   $("#detail").dialog("open");
		      });
		
		return false;
		
	}	
	</script>		