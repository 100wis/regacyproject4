package com.kosa.pro30.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.pro30.board.service.BoardService;
import com.kosa.pro30.board.domain.BoardDTO;
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
	  
			return "board/boardList";
			
		} 
		
		// boardid로 특정 게시글 가져오기
		@ResponseBody
		@RequestMapping("/board/detail.do")
		public BoardDTO getboarddetail(@RequestBody BoardDTO board, HttpServletRequest req, HttpServletResponse res) {

			System.out.println("게시글 조회수 증가");
			boardService.plusViewcount(board);
	
			System.out.println("게시판 상세보기 게시글 가져오기");
			BoardDTO detail = boardService.getDetail(board);
			System.out.println("상세정보 받아왔나 확인 : " + detail);

			return detail;
		}

		//게시글 등록
		@ResponseBody
		@RequestMapping("/board/insert.do")
		public Map<String,Object> insert(@RequestBody BoardDTO board) {
			System.out.println("보드 서비스 : 게시글 등록 ");
			Map<String, Object> jsonObject = new HashMap<>();
			int success = boardService.insert(board);
			if(success !=0) {
				jsonObject.put("status", true);
				jsonObject.put("message", "게시글 등록이 완료되었습니다");
			}else {
				jsonObject.put("status", false);
				jsonObject.put("message", "게시글 등록에 실패하였습니다.");
			}
			return jsonObject;
		}
		
	//게시글 수정하기	
		@ResponseBody
		@RequestMapping("/board/update.do")
		public Map<String,Object> update(@RequestBody BoardDTO board,HttpServletRequest req, HttpServletResponse res) {
			System.out.println("보드 서비스 : 게시글 수정 ");
			System.out.println("수정하기 보드 타이틀  " + board.getTitle());
			System.out.println("수정하기 보드 타이틀  " + board.getContents());
			int success = boardService.update(board);
			Map<String, Object> jsonObject = new HashMap<>();
			if(success !=0) {
				jsonObject.put("status", true);
				jsonObject.put("message", "게시글 수정이 완료되었습니다.");
			}else {
				jsonObject.put("status", false);
				jsonObject.put("message", "게시글 수정에 실패하였습니다..");
			}
			return jsonObject;
		}
		
	//게시글 삭제
		@ResponseBody
		@RequestMapping("/board/delete.do")
		public Map<String,Object> delete(@RequestBody BoardDTO board) {
			System.out.println("보드 서비스 : 게시글 삭제 ");
			int success = boardService.delete(board);
			Map<String, Object> jsonObject = new HashMap<>();
			if(success !=0) {
				jsonObject.put("status", true);
				jsonObject.put("message", "게시글 삭제가 완료되었습니다.");
			}else {
				jsonObject.put("status", false);
				jsonObject.put("message", "게시글 삭제에 실패하였습니다.");
			}
			return jsonObject;
		}	
		
		
		
	//검색 boardList 가져오기
		@RequestMapping("/board/titlesearch.do")
		public String SearchTitle(BoardDTO board, String searchtext, Model model) throws Exception {
	    	System.out.println("제목검색 컨트롤러" + board.getSearchtext());
	    	
	    	board.setSearchtext(searchtext);
	    	List<BoardDTO> searchedList = boardService.SearchTitle(board);
	    	Map<String, Object> jsonObject = new HashMap<>();
	    	
	    	model.addAttribute("boardList", searchedList);
	    	model.addAttribute("isSearched", true);
    
	    	
	    	System.out.println("getPaginglist 쿼리문 걸쳐 받아온 boardlist : " + searchedList);
	  
			return "board/boardList";
			
		} 

}
