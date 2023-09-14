package com.kosa.pro30.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.pro30.board.domain.BoardDTO;
import com.kosa.pro30.board.service.BoardService;

/*
 <더보기 만들기>
 ajax -> 서버에서 HTML 생성해서 클라이언트에서 출력
 	  -> 순수 DATA만 서버에 전달하고 클라이언트에서 HTML생성 (서버에 부담이 적기때문에 이 방법으로 하기)

 1. DB에서 자료를 얻는 방법
 1) 처음 10건 추출하는 방법
 2) 다음 10건을 얻는 방법
 
 2. 순수 DATA만 서버에 전달
 
 3. 클라이언트에서 전달받는 DATA를 이용해서 HTML 출력
 
*/

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
//	게시판 목록 =====================================================================================
	

	
	// 1. 게시판 페이징  10개씩 가져오기
	@RequestMapping("/board/list.do")
	public String list(@RequestBody BoardDTO board, Model model) throws Exception {
    	System.out.println("페이징된 게시판 가져오기");
    	System.out.println("컨트롤러에 넘어간 객체 확인: " + board);

    	if(board.getBoardid() == 0) {
    		board.setStartnum(1);
    		board.setEndnum(10);
    		model.addAttribute("boardList", boardService.getPagingcontents(board));
    		model.addAttribute("totalcount", boardService.totalcount());
    	}else {
    		model.addAttribute("boardList", boardService.getPagingcontents(board));
    		model.addAttribute("totalcount", boardService.totalcount());
    	}
    	
    	System.out.println("쿼리문 걸쳐 받아온 boardlist : " + boardService.getPagingcontents(board));
  
		return "board/boardList";
		
	} 
}
