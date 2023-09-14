<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<c:url value='/resources/css/member/memberlist.css' />">

<style>

</style>

    <h1 id="memberlisttitle">전체 회원 목록</h1>
    <div class="pink-button-container">
	        <table border="1">
	            <tr>
	                <th>전체 선택<input type="checkbox" name="selectAll"  onclick="toggleAll(this)"></th>
	                <th>회원 아이디</th>
	                <th>회원 이름</th>
	                <th>회원 생년월일</th>
	                <th>회원 성별</th>
	                <th>회원 전화번호</th>
	                <th>회원 email</th>
	            </tr>
	            <c:forEach var="member"  items="${memberlist}">
				    <tr>
				    	<td><input type="checkbox"  id= "cheked_uid" class="checked_uid" name="selectedBoards" value="${member.userid}"></td>
				     	<td><a href="mypage.do">${member.userid}</a></td> 
				    	<td>${member.uname}</td>
				    	<td>${member.birth}</td>
				    	<td>${member.gender}</td>
				    	<td>${member.phone}</td>
				    	<td>${member.email}</td>
				    </tr>                 
				</c:forEach> 
	        </table>
	        <!-- admin 로그인 O -->
	          <c:if test="${loginadmin != null}">
		        <input class="pink-button" type="button" value="회원 삭제" onclick="deleteMembers()">
		      </c:if>
	</div>
       
       
<script src="<c:url value='/resources/js/check.js'/>"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>

<script type="text/javascript">

//체크박스 선택시 모두 체크
function toggleAll(source) {
    var checkboxes = document.getElementsByName('selectedBoards');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = source.checked;
    }
}

function deleteMembers() {
    var userids = [];
    var items = document.querySelectorAll(".checked_uid:checked");
    
    

    items.forEach(item => {
        userids.push(item.value);
    });

    const param = {
        userids: userids,
    };


    $.ajax({
        url: "<c:url value='/admin/deletemember.do'/>",
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(param),
        success: function (json) {
            alert(json.message);
            if (json.status) {
                location.href = '<c:url value="/admin/list.do"/>';
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
}




</script>
