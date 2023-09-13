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
	
	// 1. 게시판 전체 목록 페이지
//	public String list(HttpServletRequest req, HttpServletResponse res) throws Exception {
//    	System.out.println("board.controller.list() invoked.");
//
//    	try { 
//        	req.setAttribute("board", boardService.getBoardList());
//        } catch (Exception e) { 
//        	e.printStackTrace();
//        }
//		
//		return "board/boardList.jsp";
//		
//	} // list
	
	// 1. 게시판 전체 목록 페이지[더보기]
	@RequestMapping("/board/list.do")
	public String list(BoardDTO board, Model model) throws Exception {
    	System.out.println("board.controller.list() invoked.");

    	try { 
    		model.addAttribute("boardList", boardService.getBoardList2(board));
        } catch (Exception e) { 
        	e.printStackTrace();
        }
		
		return "board/boardList";
		
	} // list
	
	
	// 1-2. 게시판 전체 목록 페이지 [ajax로 더보기 출력]
	@ResponseBody
	@RequestMapping(value="/board/ajaxList.do", method = RequestMethod.POST)
	public Map<String, Object> ajaxList(@RequestBody BoardDTO board, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("board.controller.ajaxlist()-> ajax invoked.");
    	Map<String, Object> result = new HashMap<String, Object>();
    	
		try { 
			List<BoardDTO> boardList = boardService.getBoardList2(board);
        	result.put("status", true);
        	result.put("list", boardList);
        } catch (Exception e) { 
        	result.put("status", false);
        	result.put("message", "서버에 오류 발생");
        	e.printStackTrace();
        }
		System.out.println(result);
		return result;
		
	} // list
	
	
	
	
//	게시판 상세 =====================================================================================
	
	// 2. 게시판 상세 페이지
	@RequestMapping(value="/board/detail.do", method = RequestMethod.GET)
	public String detail(BoardDTO board, Model model) throws Exception {
		System.out.println("board.controller.detail() invoked.");
		System.out.println("board " + board);
		
		try {
			model.addAttribute("status", true);
			BoardDTO boardView = boardService.getBoard(board.getBoardid());
			System.out.println("bordView = " + boardView);
			model.addAttribute("board", boardView);
        } catch (Exception e) { 
        	model.addAttribute("status", false);
        	model.addAttribute("message", "서버에 오류 발생");
        	e.printStackTrace();
        }

		return "board/detail";
	} // detail
	
	// 2. 게시판 상세 페이지
	@RequestMapping(value="/board/replyForm.do", method = RequestMethod.POST)
	public String replyForm(BoardDTO board, Model model) throws Exception {
		System.out.println("board.controller.detail() invoked.");
		System.out.println("board " + board);
		
		try {
			model.addAttribute("status", true);
			BoardDTO boardView = boardService.getBoard(board.getBoardid());
			System.out.println("bordView = " + boardView);
			model.addAttribute("board", boardView);
        } catch (Exception e) { 
        	model.addAttribute("status", false);
        	model.addAttribute("message", "서버에 오류 발생");
        	e.printStackTrace();
        }

		return "board/replyForm";
	} // detail
	
	@RequestMapping(value="/board/reply.do", method = RequestMethod.POST)
	public String reply(BoardDTO board, Model model) throws Exception {
		System.out.println("board.controller.detail() invoked.");
		System.out.println("board " + board);
		
		try {
			model.addAttribute("status", true);
			board.setWriter_uid("bbb");
			boardService.reply(board);
        } catch (Exception e) { 
        	model.addAttribute("status", false);
        	model.addAttribute("message", "서버에 오류 발생");
        	e.printStackTrace();
        }

		return "redirect:list.do";
	} // detail
	
//	게시판 글쓰기 =====================================================================================

	// 3-1. 게시판 글쓰기 페이지
	public String insertForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("board.controller.insertForm() invoked.");
		
		return "board/boardInsert.jsp"; 
	
	} // insertForm
	
	
	// 3-2. 게시판 글쓰기
	public String insert(BoardDTO board, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("board.controller.insert() invoked.");
		JSONObject jsonResult = new JSONObject();
		boolean status = boardService.boardInsert(board);
		
		jsonResult.put("status", status);
		jsonResult.put("message", status ? "글이 등록되었습니다" : "오류가 발생하였습니다. 다시 시도해주세요.");
		
		return jsonResult.toString();
	
	} // insert

	
//	게시판 글 삭제 =====================================================================================

	// 4-1. 게시판 글 삭제
	public String delete(BoardDTO board, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("board.controller.delete() invoked.");
		JSONObject jsonResult = new JSONObject();
		boolean status = boardService.boardDelete(board.getBoardid());
		
		jsonResult.put("status", status);
		jsonResult.put("message", status ? "글이 삭제되었습니다" : "오류가 발생하였습니다. 다시 시도해주세요.");
		
		return jsonResult.toString();
		
	} // delete
	
	
	// 4-2. 체크박스 글 삭제
	public String deleteBoards(String[] boardids, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("board.controller.deleteBoards() invoked.");
		JSONObject jsonResult = new JSONObject();
		boolean status = boardService.deleteBoards(boardids);
		
		jsonResult.put("status", status);
		jsonResult.put("message", status ? "글이 삭제되었습니다" : "오류가 발생하였습니다. 다시 시도해주세요.");
		
		return jsonResult.toString();
	
	}

	
//	게시판 글 수정 =====================================================================================
	
	// 5. 게시판 글 수정 페이지
	public String updateForm(BoardDTO board, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("board.controller.updateForm() invoked.");
		
		return "board/boardUpdate.jsp";
		
	} // updateForm
	
	// 5-1. 게시판 글 수정
	public String update(BoardDTO board, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("board.controller.update() invoked.");
		
		JSONObject jsonResult = new JSONObject();
		boolean status = boardService.boardUpdate(board);
		
		jsonResult.put("status", status);
		jsonResult.put("message", status ? "글이 수정되었습니다." : "오류가 발생하였습니다. 다시 시도해주세요.");
		
		return jsonResult.toString();
		
	} // update

	
}
