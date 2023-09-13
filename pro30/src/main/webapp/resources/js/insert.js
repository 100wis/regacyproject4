/*if (result) {
	MemberDB member = new MemberDB();
	memberList.add(new MemberDTO(id, pwd, name, birth, gender, phone));
	
	ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
	oos.writeObject(memberList);
	oos.close();
%>

<script>
    alert("회원가입이 완료되었습니다.");
    // 메인 페이지로 리다이렉트
    window.location.replace("main.jsp");
</script>
<%
} else {
%>	

<script>
    alert("회원가입에 실패하였습니다. 다시 시도해주세요.");
    // 메인 페이지로 리다이렉트
    window.location.replace("loginForm.jsp");
</script>

<%}%>	*/