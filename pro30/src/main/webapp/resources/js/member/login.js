// login.js

function validataNull(){
	var userid = document.querySelector('#userid');
	var pwd = document.querySelector('#pwd');

	if (userid.value === "" || pwd.value === "" ) {
	    alert("입력되지 않은 정보가 있습니다.");
	    return false;
	}
	
	return true;
}

// jquery의 $.ajax()
$(document).ready(function () {
	  $('#login').on('submit', function (e) {
	    e.preventDefault();

	    // 사용자가 입력한 아이디와 비밀번호 가져오기
	    var userid = $('#userid').val();
	    var pwd = $('#pwd').val(); 

	    // 로그인 요청을 보낼 데이터
	    var requestData = {
	      userid: userid,
	      pwd: pwd 
	    };

	    // Ajax 요청 보내기
	    $.ajax({
	      type: 'POST',
	      url: '/member/login.do', // 로그인 처리를 수행하는 서버 URL로 변경해야 합니다.
	      data: JSON.stringify(requestData),
	      contentType: 'application/json; charset=UTF-8',
	      success: function (response) {
	        alert(response.message);

	        if (response.status) {
	          // 로그인 성공 시 리다이렉트
	          window.location.href = "<c:url value='main.do'>"; 
	        }
	      },
	      error: function (error) {
	        console.error('Ajax 요청 실패:', error);
	      }
	    });
	  });
	});