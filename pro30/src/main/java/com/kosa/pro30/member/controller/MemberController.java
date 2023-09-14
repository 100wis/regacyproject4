package com.kosa.pro30.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.pro30.member.domain.MemberDTO;
import com.kosa.pro30.member.service.MemberService;

@Controller
public class MemberController {
	
	//의존성 주입
	@Autowired
	private MemberService memberService;
	

	@RequestMapping("/member/loginForm.do")
	public String main(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("로그인 페이지로 이동!");

		return "member/loginForm";
		
	} 
	
	//로그인 컨트롤러
	@ResponseBody
	@RequestMapping("/member/login.do")
	public Map<String, Object> login(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("로그인 컨트롤러 시작!");
    	
    	HttpSession session = req.getSession();
    	 Map<String, Object> jsonResult = memberService.login(memberDTO);
    	if (jsonResult.containsKey("loginmember")) {
			session.setAttribute("loginmember", jsonResult.get("loginmember"));
			session.setAttribute("isLogined", true);
			
			System.out.println(jsonResult.get("member"));
		}

		return jsonResult;
		
	} 
	
	//로그아웃 컨트롤러
	@ResponseBody
	@RequestMapping("/member/logout.do")
	public Map<String, Object> logout(HttpServletRequest req, HttpServletResponse res) throws Exception {
	    System.out.println("로그아웃 컨트롤러!");

	    HttpSession session = req.getSession();
	    session.invalidate();

	    Map<String, Object> jsonResult = new HashMap<>();
	    jsonResult.put("status", true);
	    jsonResult.put("message", "로그아웃이 완료됐습니다.");

	    return jsonResult;
	}
	
	//회원 수정 컨트롤러
	@ResponseBody
	@RequestMapping("/member/update.do")
	public Map<String, Object> update(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("회원정보 컨트롤러 시작!");
    	
    	HttpSession session = req.getSession();
    	Map<String, Object> jsonResult = memberService.update(memberDTO);
    	if (jsonResult.containsKey("logined_updatemem")) {
 			session.setAttribute("loginmember", jsonResult.get("logined_updatemem"));
 			
 			System.out.println("컨트롤러에서도 확인 : "+jsonResult.get("logined_updatemem"));
 		}

 		return jsonResult;
 		
	} 
	
	//회원가입 컨트롤러
	@ResponseBody
	@RequestMapping("/member/insert.do")
	public Map<String, Object> insert(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("회원 가입 컨트롤러 시작!");
    	Map<String, Object> jsonResult = memberService.insert(memberDTO);
 		return jsonResult;
 		
	} 
	
	//회원탈퇴 컨트롤러
	@ResponseBody
	@RequestMapping("/member/delete.do")
	public Map<String, Object> delete(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("회원탈퇴 컨트롤러 시작!");
    	
    	 Map<String, Object> jsonResult = memberService.delete(memberDTO);
    	 
    	 //세션도 무효화
    	 HttpSession session = req.getSession();
 	     session.invalidate();
    	 
		return jsonResult;	
	} 
	
	//중복아이디 체크 컨트롤러
	@ResponseBody
	@RequestMapping("/member/IDcheck.do")
	public Map<String, Object> IDcheck(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("중복아이디 체크 컨트롤러 시작!");
    	
    	 Map<String, Object> jsonResult = memberService.IDcheck(memberDTO);
    	 
    	 //세션도 무효화
    	 HttpSession session = req.getSession();
 	     session.invalidate();
    	 
		return jsonResult;	
	} 
	
	
	//아이디 찾기 컨트롤러
	@ResponseBody
	@RequestMapping("/member/lookforID.do")
	public Map<String, Object> lookforID(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("아이디 찾기 컨트롤러 시작!");
    	Map<String, Object> jsonResult = memberService.lookforID(memberDTO);
		return jsonResult;		
	} 
	
	
	//패스워드 찾기 컨트롤러
	@ResponseBody
	@RequestMapping("/member/lookforPWD.do")
	public Map<String, Object> lookforPWD(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("비밀번호 찾기 컨트롤러 시작!");
    	Map<String, Object> jsonResult = memberService.lookforPWD(memberDTO);
		return jsonResult;		
	} 
	
} 
