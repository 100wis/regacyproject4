package com.kosa.pro30.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.pro30.board.domain.BoardDTO;
import com.kosa.pro30.board.service.BoardService;
import com.kosa.pro30.member.domain.MemberDTO;
import com.kosa.pro30.member.service.MemberService;
import com.kosa.pro30.notice.domain.NoticeDTO;
import com.kosa.pro30.notice.service.NoticeService;

@Controller
public class AdminController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private MemberService memberService;
	
	//admin 메인페이지 띄우기
	@RequestMapping("admin.do")
	public String main(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("admin관리");
		
		try { 
			List<NoticeDTO> noticeTop5 = noticeService.noticeTop5();
        	req.setAttribute("noticeTop5", noticeTop5);
        	
        	List<BoardDTO> boardTop5 = boardService.boardTop5();
        	req.setAttribute("boardTop5", boardTop5);
        	
        } catch (Exception e) { 
        	e.printStackTrace();
        }

		return "admin/adminMain";
		
	} 
	

	//로그인 컨트롤러
		@ResponseBody
		@RequestMapping("/admin/login.do")
		public Map<String, Object> login(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
	    	System.out.println("관리자 로그인 컨트롤러 시작!");
	    	
	    	HttpSession session = req.getSession();
	    	 Map<String, Object> jsonResult = memberService.login(memberDTO);
	    	 System.out.println("관리자에 로그인 멤버 정보 왔는지 : "+jsonResult.get("loginmember"));
	    	if (jsonResult.containsKey("loginmember")) {
				session.setAttribute("loginadmin", jsonResult.get("loginmember"));
			}

			return jsonResult;
	} 
	//로그아웃 컨트롤러
		@ResponseBody
		@RequestMapping("/admin/logout.do")
		public Map<String, Object> logout(HttpServletRequest req, HttpServletResponse res) throws Exception {
		    System.out.println("관리자 로그아웃 컨트롤러!");

		    HttpSession session = req.getSession();
		    session.removeAttribute("loginadmin");


		    Map<String, Object> jsonResult = new HashMap<>();
		    jsonResult.put("status", true);
		    jsonResult.put("message", "관리자 로그아웃이 완료됐습니다.");

		    return jsonResult;
		}
		
		
		//전체회원목록으로 이동	
		@RequestMapping("/admin/list.do")
		public String list(HttpServletRequest req, HttpServletResponse res) throws Exception {
	    	System.out.println("admin관리");
			
			try { 
				List<MemberDTO> memberlist = memberService.list();
	        	req.setAttribute("memberlist", memberlist);
  	
	        } catch (Exception e) { 
	        	e.printStackTrace();
	        }

			return "admin/memberlist";
			
		} 		
		
		//관리자의 회원 삭제
		@ResponseBody
		@RequestMapping("/admin/deletemember.do")
		public Map<String, Object> deletemember(@RequestBody MemberDTO memberDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
	    	System.out.println("관리자 회원삭제 컨트롤러 시작!");
	    	System.out.println(memberDTO);
	    	
	    	 int num = memberService.deletemembers(memberDTO);
	    	 Map<String, Object> jsonResult = new HashMap<>();
	    	 if(num>0) {
	    		 jsonResult.put("status", true);
	 		    jsonResult.put("message", "총 "+num+"건 삭제가 완료되었습니다");
	    	 }else {
	    		 jsonResult.put("status", false);
		 		 jsonResult.put("message", "회원삭제 실패");
	    	 }


		return jsonResult;
	} 
		
		
		
		
//공지사항 리스트 가져오면서 페이지로 로드
		@RequestMapping("/admin/notice/list.do")
		public String list(NoticeDTO notice, Model model) throws Exception {
			System.out.println("공지사항 리스트 출력");

			System.out.println("현재페이지 : " + notice.getPageNo());

			notice.getStartNo();
			notice.getEndNo();
			// model.addAttribute("noticelist",noticeService.getNoticeList(notice) );
			model.addAttribute("result", noticeService.getNoticeList(notice));

			System.out.println("notice리스트제대로 왔는지: " + noticeService.getNoticeList(notice));

			return "admin/noticeList";
		}
		
		
//게시판 어드민계정 가져오기
		@RequestMapping("/admin/board/list.do")
		public String getPaginglist(BoardDTO board, Integer startnum, Integer endnum, Model model) throws Exception {
	    	System.out.println("페이징된 게시판 가져오기");
	    	System.out.println("컨트롤러에 넘어간 객체 확인 startnum: " + startnum);
	    	System.out.println("컨트롤러에 넘어간 객체 확인 endnum: " + endnum);
	    	
	    		if(startnum == null){
	    			board.setStartnum(1);
	    			board.setEndnum(10);
	    			model.addAttribute("boardList", boardService.getPagingcontents(board));
	    			model.addAttribute("isSearched", false);
	    		}else {
	    			board.setStartnum(startnum);
	    			board.setEndnum(endnum);
	    			model.addAttribute("boardList", boardService.getPagingcontents(board));
	    			model.addAttribute("isSearched", false);
	    		}
	    		
	    		model.addAttribute("totalcount", boardService.totalcount());
	    
	    	
	    	System.out.println("getPaginglist 쿼리문 걸쳐 받아온 boardlist : " + boardService.getPagingcontents(board));
	  
			return "admin/boardList";
			
		} 

		
		
		

		
		

} // end class
