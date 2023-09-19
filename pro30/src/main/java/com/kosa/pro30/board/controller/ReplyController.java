package com.kosa.pro30.board.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.pro30.board.domain.BoardDTO;
import com.kosa.pro30.board.domain.ReplyDTO;
import com.kosa.pro30.board.service.BoardService;
import com.kosa.pro30.board.service.ReplyService;
import com.kosa.pro30.member.domain.MemberDTO;
import com.kosa.pro30.member.service.MemberService;


@Controller
public class ReplyController{
	@Autowired 
	ReplyService replyService;
	
	@Autowired 
	private BoardService boardService;
	
	@Autowired 
	private MemberService memberService;
	

	
	@ResponseBody
	@RequestMapping("/reply/replylist.do")
	public  Map<String,Object> getPaginglist(@RequestBody ReplyDTO reply, Model model) throws Exception {
    	System.out.println("댓글 리스트 컨트롤러 진입");
    	System.out.println("strartnum : " + reply.getStartnum());
    	
    	Map<String, Object> jsonObject = new HashMap<>();
    	
    		if(reply.getStartnum()== 0){
    			reply.setStartnum(1);
    			reply.setEndnum(10);
    			jsonObject.put("replyList", replyService.getReplyList(reply));
    		}else {
    			jsonObject.put("replyList",  replyService.getReplyList(reply));
    		}
    		
    		//model.addAttribute("totalcount", replyService.totalcount());

  
		return jsonObject;
		
	} 
	
		// 댓글 등록
		@ResponseBody
		@RequestMapping("/reply/insert.do")
		public  Map<String,Object> insert(@RequestBody ReplyDTO reply,  HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	System.out.println("댓글 등록 컨트롤러 진입");
	    	System.out.println("컨트롤러에 넘어간 객체 확인 reply: " + reply);
	    	
	    	Map<String, Object> jsonObject = replyService.insert(reply);
	    	System.out.println(jsonObject);
	    	
			
	    	// 댓글이 달린 게시판 번호가져오기
	    	BoardDTO board = BoardDTO.builder().boardid(reply.getBoardid()).build();  
	    	// 그 게시판을 작성한 유저 아이디 가저오기
	    	String to_mail_userid = boardService.getDetail(board).getWriter_uid();
	    	
	    	//게시물쓴 유저의 이메일 가져오기
	    	MemberDTO member = MemberDTO.builder().userid(to_mail_userid).build();
	    	String member_email = memberService.findemailaddress(member);
	    	
	    	System.out.println("email");
	    	request.setCharacterEncoding("utf-8");
	    	response.setContentType("text/html;charset=utf-8");
	    	
	    	replyService.sendMail(member_email,"알람이왔습니다","댓글이 달렸습니다 확인해주세요!");
	    	
	    	
			return jsonObject;
			
		} 
		
		//댓글 삭제
		@ResponseBody
		@RequestMapping("/reply/delete.do")
		public Map<String,Object> delete(@RequestBody ReplyDTO reply) {
			System.out.println("댓글 삭제 컨트롤러");
			int success = replyService.delete(reply);
			Map<String, Object> jsonObject = new HashMap<>();
			if(success !=0) {
				jsonObject.put("status", true);
				jsonObject.put("message", "댓글 삭제가 완료되었습니다.");
			}else {
				jsonObject.put("status", false);
				jsonObject.put("message", "댓글 삭제에 실패하였습니다.");
			}
			return jsonObject;
		}	
		
		
		//댓글 수정
		@ResponseBody
		@RequestMapping("/reply/update.do")
		public Map<String,Object> update(@RequestBody ReplyDTO reply) {
			System.out.println("댓글 수정 컨트롤러");
			ReplyDTO updated_reply = replyService.update(reply);
			Map<String, Object> jsonObject = new HashMap<>();
			if(updated_reply != null) {
				jsonObject.put("status", true);
				jsonObject.put("message", "댓글 수정이 완료되었습니다.");
				jsonObject.put("updated_reply", updated_reply);
				System.out.println(updated_reply);
			}else {
				jsonObject.put("status", false);
				jsonObject.put("message", "댓글 수정에 실패하였습니다.");
			}
			return jsonObject;
		}	
	
	
}
