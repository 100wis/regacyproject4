package com.kosa.pro30.member.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.pro30.member.dao.MemberDAO;
import com.kosa.pro30.member.domain.MemberDTO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	//로그인
	public  Map<String, Object> login(MemberDTO memberDTO) {
		System.out.println("로그인 회원 서비스 부분 왔다!");
		System.out.println(memberDTO);
		Map<String, Object> jsonObject = new HashMap<>();
		MemberDTO loginmember = memberDAO.getUser(memberDTO);
		if(loginmember.isEqualsPwd(memberDTO)) {
			jsonObject.put("loginmember", loginmember);
			jsonObject.put("status", true);
			jsonObject.put("message", "로그인 성공");
			
		}else {
			jsonObject.put("status", false);
			jsonObject.put("message", "로그인 실패");
		}
	return jsonObject;
	}

	//회원정보수정
	public Map<String, Object> update(MemberDTO memberDTO) {
		System.out.println("회원정보 수정 서비스 부분 왔다!");
		System.out.println(memberDTO);
		Map<String, Object> jsonObject = new HashMap<>();
		MemberDTO logined_updatemem = memberDAO.update(memberDTO);
		if(logined_updatemem != null) {
			jsonObject.put("logined_updatemem", logined_updatemem);
			jsonObject.put("status", true);
			jsonObject.put("message", "회원정보 수정 성공");
			System.out.println("업데이트 됐나 확인 :" + logined_updatemem );
		}else {
			jsonObject.put("status", false);
			jsonObject.put("message", "회원정보 수정 실패");
		}
		

		return jsonObject;
	}
	
	//회원가입
	public Map<String, Object> insert(MemberDTO memberDTO) {
		System.out.println("회원 가입 서비스 부분 왔다!");
		System.out.println(memberDTO);
		Map<String, Object> jsonObject = new HashMap<>();
		 int insertnum = memberDAO.insert(memberDTO);
		if(insertnum > 0) {
			jsonObject.put("insertnum", insertnum);
			jsonObject.put("status", true);
			jsonObject.put("message", "회원가입 완료되었습니다");
		}else {
			jsonObject.put("status", false);
			jsonObject.put("message", "회원가입중 문제가 발생했습니다.");
		}
		return jsonObject;
	}
	
	//회원 탈퇴
	public  Map<String, Object> delete(MemberDTO memberDTO) {
		System.out.println("회원탈퇴 서비스 부분 왔다!");
		System.out.println(memberDTO);
		Map<String, Object> jsonObject = new HashMap<>();
		MemberDTO deletemem = memberDAO.getUser(memberDTO);
		if(deletemem.isEqualsPwd(deletemem)) {
			//회원삭제
			memberDAO.delete(memberDTO);
			jsonObject.put("status", true);
			jsonObject.put("message", "회원 탈퇴되었습니다");
			
		}else {
			jsonObject.put("status", false);
			jsonObject.put("message", "비밀번호가 다릅니다.");
		}
	return jsonObject;
	}
	
	//회원 탈퇴
		public  Map<String, Object> IDcheck(MemberDTO memberDTO) {
			System.out.println("중복아이디 서비스 부분 왔다!");
			System.out.println(memberDTO);
			Map<String, Object> jsonObject = new HashMap<>();
			MemberDTO checkuid = memberDAO.getUser(memberDTO);
			if(checkuid == null) {
				//중복아이디 없음 회원 가입가능
				memberDAO.delete(memberDTO);
				jsonObject.put("status", true);
				jsonObject.put("message", "사용가능한 아이디입니다.");
				
			}else {
				jsonObject.put("status", false);
				jsonObject.put("message", "이미 존재하는 아이디 입니다.");
			}
		return jsonObject;
		}
	
	

}