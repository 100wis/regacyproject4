<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<link rel="stylesheet" href="<c:url value='/resources/css/member/loginpage.css'/>">

	<div id="loginbox">
	  <div id="logintext">
	    <h1>♡로그인♡</h1>
	  </div>
	  <div id="logininput">
	    <form id="login"  method="post" >
	      <label for="uid">아이디 : </label>
	      <input id="userid" type="text" name="userid" /><br />
	      
	      <label for="uid">비밀번호 : </label>
	      <input id="pwd" type="password" name="pwd" /><br />
	      
	      <input type="submit"  value="로그인"  onclick="return validataNull()" />
	      <input type="reset" value="ID또는 Password 찾기"  onclick="window.location.href='FindPage.do'" />
	      <input type="reset" value="회원가입" onclick="window.location.href='registerForm.do'">
	      
	    </form>
	  </div>
	</div>

<script src="<c:url value='/resources/js/member/login.js'/>"></script>
