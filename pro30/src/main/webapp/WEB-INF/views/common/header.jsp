<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
#deleteMember {
  display: none; /* 초기에는 숨김 상태로 설정 */
}


#lookforIDform {
  display: none; /* 초기에는 숨김 상태로 설정 */
}

#lookforPWDform {
  display: none; /* 초기에는 숨김 상태로 설정 */
}
</style>

<!-- //////////////////////////////////////헤더부분 수정시 주의 필요 /////////////////////////////-->
 <header>
  <div id="logo">
    <a href="<c:url value='/main.do'/>">
      <h1>jihye's style</h1>
    </a>
  </div>
  <nav>
    <ul id="topMenu">
      <li><a href="#">Contents <span>▼</span></a>
        <ul>
          <li><a href="<c:url value='/notice/list.do'/>">공지사항</a></li>
          <li><a href="<c:url value='/board/list.do'/>">자유 게시판</a></li>
        </ul>
      </li>
      <li><a href="#">Category <span>▼</span></a>
        <ul>
          <li><a href="#">Outer</a></li>
          <li><a href="#">Dress</a></li>
          <li><a href="#">Top</a></li>
          <li><a href="#">Bottom</a></li>
        </ul>
      </li>
      <!-- 로그인 O -->
      <c:if test="${loginmember != null}">
        <li id="myPage"><a href="#" id="mypagebutton">my page</a></li>
        <li id="logout"><a href="#" id="logoutbutton" onclick="logout()">logout</a></li>
      </c:if>

      <!-- 로그인 X -->
      <c:if test="${loginmember == null}">
        <li id="login"><a href="#" id="loginButton">login</a></li>
        <li id="insert"><a href="#" id="insertMemButton">회원 가입</a></li>
      </c:if>
    </ul>
  </nav>
<!-- /////////////////////////////////////////헤더부분 수정시 주의 필요////////////////////////////// -->  


  <!-- 모달 다이얼로그 -->
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
  
  
  <div id="myPageDialog" class="modal">
        <h2>My Page</h2>
		<p class="p-text">✉ 빈칸 입력시 기존 정보 유지 ✉</p>
		    <div class="form-item">
		      <label for="userid">아이디</label> ${loginmember.userid }
		      <input type="hidden" id="userid" name="userid" value="${loginmember.userid}">	
		    </div>
		    <div class="form-item">
		      <label for="pwd">비밀번호</label><p>${loginmember.pwd}</p>
		    </div>
		     <div class="form-item">
		      <label for="pw2">변경할 비밀번호</label>
		      <input class="newdata" type="password" id="newpwd" name="newpwd"/>
		    </div>
		    <div class="form-item">
		      <label for="uname" >이름</label>${loginmember.uname}
		    </div>
		    <div class="form-item">
		      <label for="name">변경할 이름</label>
		      <input class="newdata" type="text" id="newuname"  name="newuname"/>
		    </div>
		    <div class="form-item">
		      <label for="phone">생년월일</label>${loginmember.birth}
		    </div>
		    <div class="form-item">
		      <label for="name">변경할 생년월일</label>
		      <input class="newdata" type="text" id="newbirth"  name="newbirth"/>
		    </div>
		    <div class="form-item">
		      <label for="gender" >성별</label>${loginmember.gender}
		    </div>
		    <div class="form-item">
		      <label for="age">변경할 성별</label>
		      <input class="newdata" type="text" id="newgender"  name="newgender"/>
		    </div>
		    <div class="form-item">
		    	<label for="phone">전화번호</label> ${loginmember.phone}
		    </div>
		    <div class="form-item">
		      <label for="age">변경할 전화번호</label>
		      <input class="newdata" type="text" id="newphone"  name="newphone"/>
		    </div>
		    <div class="form-item">
		    	<label for="email">e-mail</label> ${loginmember.email}
		    </div> 
		    <div class="form-item">
		      <label for="email">변경할 email주소</label>
		      <input class="newdata" type="text" id="newemail"  name="newemail"/>
		    </div>   
		    <div class="button">
		    </div>
</div>

<div id="inserDialog">
   <h2>회원 가입</h2>
			<label for="userid"> 아이디 : </label>
			<input id="userid" type="text" name="userid" />
			<input type="button" id="IDcheck" value="중복확인" onclick="IDcheck()"/><br/>
			
			<label for="uid"> 비밀번호 : </label>
			<input id="pwd" type="password" name="pwd"/><br/>
	
			<label for="uid"> 비밀번호 확인 : </label>
			<input id="pwd2" type="password" name="pwd2"/><br/>
			
			<label for="uid"> 이름 : </label>
			<input id="uname" type="text" name="uname"/><br/>
			
			<label for="uid"> 생년월일 : </label>
			<input id="birth" type="text" name="birth"/><br/>
			
			<label for="uid"> 성별: </label>
			<input id="gender" type="text" name="gender"/><br/>
			
			<label for="uid"> 전화 번호 : </label>
			<input id="phone" type="tel" name="phone"/><br/>
			
			<label for="uid"> e-mail : </label>
			<input id="email" type="tel" name="email"/><br/>	
</div>


<div id = "deleteMember">
	<h2>회원 탈퇴</h2>
	<h3>정말 탈퇴하시겠습니까?</h3>
	<h3>탈퇴 시 모든 회원전용 상품을 이용하실 수 없습니다.</h3>
	<h3>탈퇴를 원하시면 비밀번호를 입력해주세요.</h3>
	<input type="hidden" id="userid" name="userid" value="${loginmember.userid}"/>
	<input id="pwd" type="password" name="pwd"/>
</div>

<div id = "lookforIDform">
	<h2>아이디 찾기</h2>
	이름 : <input type="text" id="uname" name="uname"/>
	이메일 : <input type="text" id="email" name="email"/>
</div>

<div id = "lookforPWDform">
	<h2>비밀번호 찾기</h2>
	아이디 : <input type="text" id="userid" name="userid"/>
	이메일 : <input type="text" id="email" name="email"/>
</div>

</header>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script>
 

  // 로그아웃 함수 정의
  function logout() {
    $.ajax({
      url: "<c:url value='/member/logout.do'/>",
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
	      url: "<c:url value='/member/login.do'/>",
	      method: "POST",
	      contentType: "application/json; charset=UTF-8",
	      data: JSON.stringify(data),
	      success: function (json) {
	        alert(json.message);
	        if (json.status) {
	          // 로그인이 성공하면 원하는 동작을 수행 (예: 리디렉션)
	          location.href = "<c:url value='/main.do'/>";
	        }
	      },
	      error: function (error) {
	        console.error("Error:", error);
	      }
	    });
	  
  }
  
  function lookingID() {
	  const uname = $("#lookforIDform #uname").val();
	    const email = $("#lookforIDform #email").val();

	    // 서버로 보낼 데이터 객체 생성
	    const data = {
	      uname: uname,
	      email: email
	    };

	    // 서버로 POST 요청 보내기
	    $.ajax({
	      url: "<c:url value='/member/lookforID.do'/>",
	      method: "POST",
	      contentType: "application/json; charset=UTF-8",
	      data: JSON.stringify(data),
	      success: function (json) {
	        alert(json.message);
	        //아이디 찾기 성공시 두번째 다이얼로그만 닫기되면 좋겠는데
	      },
	      error: function (error) {
	        console.error("Error:", error);
	      }
	    });
	  
  }
  
  function lookingPWD() {
	  const userid = $("#lookforPWDform #userid").val();
	    const email = $("#lookforPWDform #email").val();

	    // 서버로 보낼 데이터 객체 생성
	    const data = {
	      userid: userid,
	      email: email
	    };

	    // 서버로 POST 요청 보내기
	    $.ajax({
	      url: "<c:url value='/member/lookforPWD.do'/>",
	      method: "POST",
	      contentType: "application/json; charset=UTF-8",
	      data: JSON.stringify(data),
	      success: function (json) {
	        alert(json.message);
	        //아이디 찾기 성공시 두번째 다이얼로그만 닫기되면 좋겠는데
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
             "로그인":login,
             "아이디 찾기" : function () {
                 // 저장 버튼을 클릭하면 또 다른 다이얼로그를 열기
                 var lookforID = $("#lookforIDform").dialog({
                   autoOpen: false,
                   height: 300,
                   width: 300,
                   modal: true,
                   buttons: {
                 	  "아이디 찾기" : lookingID,
                     "닫기" : function () {
                       $(this).dialog("close");
                     }
                   }
                 });
                 lookforID.css("display", "block");
                 
                 lookforID.dialog("open");
                 $(this).dialog("close");
               },
               "비밀번호 찾기": function () {
                   // 저장 버튼을 클릭하면 또 다른 다이얼로그를 열기
                   var lookforPWD = $("#lookforPWDform").dialog({
                     autoOpen: false,
                     height: 300,
                     width: 300,
                     modal: true,
                     buttons: {
                   	  "비밀 번호 찾기":lookingPWD,
                       "닫기": function () {
                         $(this).dialog("close");
                       }
                     }
                   });
                   lookforPWD.css("display", "block");
                   
                   lookforPWD.dialog("open");
                   $(this).dialog("close");
                 }
         }
     });

     $("#loginButton").on("click", function () {
    	 loginDialog.dialog("open");
     });

  
  
  
  // 회원정보 수정 함수
  function udate() {

	    // 아이디와 비밀번호 입력값 가져오기
	    const userid = "${loginmember.userid}";
	    const newpwd = $("#newpwd").val() || "${loginmember.pwd}"; // 빈 값이면 이전 비밀번호 사용
	    const newuname = $("#newuname").val() || "${loginmember.uname}"; // 빈 값이면 이전 이름 사용
	    const newbirth = $("#newbirth").val() || "${loginmember.birth}"; // 빈 값이면 이전 생년월일 사용
	    const newgender = $("#newgender").val() || "${loginmember.gender}"; // 빈 값이면 이전 성별 사용
	    const newphone = $("#newphone").val() || "${loginmember.phone}"; // 빈 값이면 이전 전화번호 사용
	    const newemail = $("#newemail").val() || "${loginmember.email}"; // 빈 값이면 이전 이메일 사용
	    
	    console.log("userid:", userid);
	    console.log("newpwd:", newpwd);
	    console.log("newuname:", newuname);
	    console.log("newbirth:", newbirth);
	    console.log("newgender:", newgender);
	    console.log("newphone:", newphone);
	    console.log("newemail:", newemail);

	    // 서버로 보낼 데이터 객체 생성
	    const data = {
	    		userid: userid,
	    		 pwd: newpwd,
	    		 uname: newuname,
	    		 birth: newbirth,
	    		 gender: newgender,
	    		 phone: newphone,
	    		 email: newemail
	    };
	    console.log("data:", data);

	    // 서버로 POST 요청 보내기
	    $.ajax({
	      url: "<c:url value='/member/update.do'/>",
	      method: "POST",
	      contentType: "application/json; charset=UTF-8",
	      data: JSON.stringify(data),
	      success: function (json) {
	        alert(json.message);
	        if (json.status) {
	          // 업데이트 성공하면 원하는 동작을 수행 (예: 리디렉션)
	          location.href = "<c:url value='/main.do'/>";
	        }
	      },
	      error: function (error) {
	        console.error("Error:", error);
	      }
	    });
	  return false
	}
  	
  //회원탈퇴 기능 함수
  	function deletemember() {
  		
  	  const userid = $("#deleteMember #userid").val();
	    const pwd = $("#deleteMember #pwd").val();
	    
	    console.log("userid:", userid);
	    console.log("pwd:", pwd);

	    // 서버로 보낼 데이터 객체 생성
	    const data = {
	      userid: userid,
	      pwd: pwd
	    };
	    
	    $.ajax({
		      url: "<c:url value='/member/delete.do'/>",
		      method: "POST",
		      contentType: "application/json; charset=UTF-8",
		      data: JSON.stringify(data),
		      success: function (json) {
		        alert(json.message);
		        if (json.status) {
		          // 탈퇴에 성공하면 
		          location.href = "<c:url value='/main.do'/>";
		        }
		      },
		      error: function (error) {
		        console.error("Error:", error);
		      }
		    });
  		
  	}

  
	//마이페이지 모달 열기 함수
	 var myPageDialog = $("#myPageDialog").dialog({
            autoOpen: false,
            height: 500,
            width: 500,
            modal: true,
            buttons: {
                "회원 정보 수정": udate,
                "회원 탈퇴" : function () {
                    // 저장 버튼을 클릭하면 또 다른 다이얼로그를 열기
                    var deleteMember = $("#deleteMember").dialog({
                      autoOpen: false,
                      height: 300,
                      width: 480,
                      modal: true,
                      buttons: {
                    	  "회원 탈퇴" : deletemember,
                        "닫기": function () {
                          $(this).dialog("close");
                        }
                      }
                    });
                    deleteMember.css("display", "block");
                    
                    deleteMember.dialog("open");
                    $(this).dialog("close");
                  },
                  "닫기": function () {
                      $(this).dialog("close");
                }
            }
        });

        $("#mypagebutton").on("click", function () {
        	myPageDialog.dialog("open");
        });
        
        
        
     // 회원가입 함수
        function insert() {

      	    // 아이디와 비밀번호 입력값 가져오기
      	    const userid = $("#inserDialog #userid").val()
      	    const pwd = $("#inserDialog #pwd").val()
      	    const uname = $("#uname").val()
      	    const birth = $("#birth").val()
      	    const gender = $("#gender").val()
      	    const phone = $("#phone").val()
      	    const email = $("#email").val()
      	    
      	    console.log("userid:", userid);
      	    console.log("pwd:", pwd);
      	    console.log("pwd2:", pwd2);
      	    console.log("uname:", uname);
      	    console.log("birth:", birth);
      	    console.log("gender:", gender);
      	    console.log("phone:", phone);
      	    console.log("email:", email);

      	    // 서버로 보낼 데이터 객체 생성
      	    const data = {
      	    		 userid: userid,
      	    		pwd: pwd,
      	    		 uname: uname,
      	    		 birth: birth,
      	    		 gender: gender,
      	    		 phone: phone,
      	    		 email: email
      	    };
      	    console.log("data:", data);

      	    // 서버로 POST 요청 보내기
      	    $.ajax({
      	      url: "<c:url value='/member/insert.do'/>",
      	      method: "POST",
      	      contentType: "application/json; charset=UTF-8",
      	      data: JSON.stringify(data),
      	      success: function (json) {
      	        alert(json.message);
      	        if (json.status) {
      	          // 업데이트 성공하면 원하는 동작을 수행 (예: 리디렉션)
      	          location.href = "<c:url value='/main.do'/>";
      	        }
      	      },
      	      error: function (error) {
      	        console.error("Error:", error);
      	      }
      	    });
      	  return false
      	}
     	
     
     //중복아이디 체크
        function IDcheck() {
        	  const userid = $("#inserDialog #userid").val()
        	  
        	  const data = {
   	    		 userid: userid };
        	  
        	  $.ajax({
          	      url: "<c:url value='/member/IDcheck.do'/>",
          	      method: "POST",
          	      contentType: "application/json; charset=UTF-8",
          	      data: JSON.stringify(data),
          	      success: function (json) {
          	        alert(json.message);
          	      },
          	      error: function (error) {
          	        console.error("Error:", error);
          	      }
          	    });
        	
        }
        
        
      	//회원가입 다이얼로그 
      	 var insertDialog = $("#inserDialog").dialog({
                  autoOpen: false,
                  height: 500,
                  width: 500,
                  modal: true,
                  buttons: {
                      "회원 가입": insert,
                      "Cancel": function () {
                          $(this).dialog("close");
                      }
                  }
              });

              $("#insertMemButton").on("click", function () {
            	  insertDialog.dialog("open");
              });
        
  
</script>