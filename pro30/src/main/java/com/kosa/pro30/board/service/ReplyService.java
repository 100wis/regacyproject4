package com.kosa.pro30.board.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.pro30.board.dao.ReplyDAO;
import com.kosa.pro30.board.domain.ReplyDTO;



@Service
public class ReplyService {
	
	@Autowired
	private ReplyDAO replyDAO;
	
	// 댓글리스트 불러오기
	public List<ReplyDTO> getReplyList(ReplyDTO reply){
		System.out.println("replyList 서비스 왔다 ");
		List<ReplyDTO> replyList = replyDAO.getReplyList(reply);
		System.out.println("service replyList 잘 받아왔는지 확인: "+ replyList);
		return replyList;
	}
	
	// 댓글 추가하기
	public Map<String, Object> insert(ReplyDTO reply) {
		System.out.println("댓글 등록 서비스 왔당");
		
		Map<String, Object> jsonObject = new HashMap<>();
		//insert를 하고
		int success = replyDAO.insert(reply);
		if(success !=0) {// 성공하면
			jsonObject.put("status", true);
			jsonObject.put("message", "댓글 등록 완료");
			//다시 게시물을 가져와야함 어떻게...? reply번호가 젤 큰거?
			jsonObject.put("inserted_reply",replyDAO.getLastreply());
			
		}else {
			jsonObject.put("status", false);
			jsonObject.put("message", "댓글 등록 실패");
		}
		return jsonObject;
	}

	public int delete(ReplyDTO reply) {
		System.out.println("댓글 삭제 서비스");
		return replyDAO.delete(reply);
	}
	
	
	//댓글 수정하기
	public ReplyDTO update(ReplyDTO reply) {
		System.out.println("댓글 수정 서비스");
		int num = replyDAO.update(reply);
		if(num >0) {
			ReplyDTO updateReply= replyDAO.getreply(reply);
			return updateReply;
		}
		return reply;
	}
	
	

}
