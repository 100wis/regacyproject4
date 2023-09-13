package com.kosa.pro30.main.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosa.pro30.board.domain.BoardDTO;
import com.kosa.pro30.board.service.BoardService;
import com.kosa.pro30.notice.domain.NoticeDTO;
import com.kosa.pro30.notice.service.NoticeService;

@Controller
public class MainController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private NoticeService noticeService;

	@RequestMapping("main.do")
	public String main(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("main() invoked. ");
		
		try { 
			List<NoticeDTO> noticeTop5 = noticeService.noticeTop5();
        	req.setAttribute("noticeTop5", noticeTop5);
        	
        	List<BoardDTO> boardTop5 = boardService.boardTop5();
        	req.setAttribute("boardTop5", boardTop5);
        	
        } catch (Exception e) { 
        	e.printStackTrace();
        }

		return "main";
		
	} // list

} // end class
