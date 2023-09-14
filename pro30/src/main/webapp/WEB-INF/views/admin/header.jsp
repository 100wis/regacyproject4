<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
</style>

<!-- //////////////////////////////////////헤더부분 수정시 주의 필요 /////////////////////////////-->
 <header>
  <div id="logo">
    <a href="<c:url value='/admin.do'/>">
      <h1>jihye's style</h1>
    </a>
  </div>
  <nav>
    <ul id="topMenu">
      <!-- 로그인 O -->
      <c:if test="${loginadmin != null}">
        <li id="myPage"><a href="<c:url value='/admin/list.do'/>">회원관리</a></li>
        <li id="myPage"><a href="<c:url value='/notice/list.do'/>">공지사항 관리</a></li>
        <li id="myPage"><a href="<c:url value='/board/list.do'/>">게시판 관리</a></li>
        <li id="logout"><a href="#" id="logoutbutton" onclick="logout()">로그아웃</a></li>
      </c:if>

      <!-- 로그인 X -->
      <c:if test="${loginadmin == null}">
        <li id="login"><a href="#" id="loginButton">관리자 로그인</a></li>
      </c:if>
    </ul>
  </nav>
<!-- /////////////////////////////////////////헤더부분 수정시 주의 필요////////////////////////////// -->  


  <!-- 로그인 다이얼로그 -->
  <div id="loginDialog" class="modal">
    <div class="modal-content">
      <span class="close" id="closeBtn">&times;</span>
      <h2>Login</h2>
      <form id="loginForm" method="post" >
        <label for="userid">아이디:</label>
        <input id="userid" type="text" name="userid" /><br />

        <label for="pwd">비밀번호:</label>
        <input id="pwd" type="password" name="pwd" /><br />

      </form>
    </div>
  </div>
  
  
  


</header>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script>
 
  
  //로그인 함수
  function login() {
	  const userid = $("#loginDialog #userid").val();
	    const pwd = $("#loginDialog #pwd").val();
	    
	    console.log("userid:", userid);
	    console.log("pwd:", pwd);

	    // 서버로 보낼 데이터 객체 생성
	    const data = {
	      userid: userid,
	      pwd: pwd
	    };

	    // 서버로 POST 요청 보내기
	    $.ajax({
	      url: "<c:url value='/admin/login.do'/>",
	      method: "POST",
	      contentType: "application/json; charset=UTF-8",
	      data: JSON.stringify(data),
	      success: function (json) {
	        alert(json.message);
	        if (json.status) {
	          // 로그인이 성공하면 원하는 동작을 수행 (예: 리디렉션)
	          location.href = "<c:url value='/admin.do'/>";
	        }
	      },
	      error: function (error) {
	        console.error("Error:", error);
	      }
	    });
	  
  }
  
  
  
//로그인 모달 열기 함수
	 var loginDialog = $("#loginDialog").dialog({
         autoOpen: false,
         height: 300,
         width: 400,
         modal: true,
         buttons: {
             "관리자 로그인":login
         }
	 });

     $("#loginButton").on("click", function () {
    	 loginDialog.dialog("open");
     });

  // 로그아웃 함수 정의
     function logout() {
       $.ajax({
         url: "<c:url value='/admin/logout.do'/>",
         method: "POST",
         contentType: "application/json; charset=UTF-8",
         success: function (json) {
           alert(json.message);
           if (json.status) {
             location.href = "<c:url value='/main.do'/>";
           }
         },
         error: function (error) {
           console.error("Error:", error);
         }
       });
     } 

</script>