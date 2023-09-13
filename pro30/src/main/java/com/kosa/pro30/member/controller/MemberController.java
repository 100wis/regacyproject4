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
	
	@Autowired
	private MemberService memberService;
	

	@RequestMapping("/member/loginForm.do")
	public String main(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("로그인 페이지로 이동!");

		return "member/loginForm";
		
	} 
	
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
	
	@ResponseBody
	@RequestMapping("/member/insert.do")
	public Map<String, Object> insert(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("회원 가입 컨트롤러 시작!");
    	Map<String, Object> jsonResult = memberService.insert(memberDTO);
 		return jsonResult;
 		
	} 
	
	
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
	
} 
