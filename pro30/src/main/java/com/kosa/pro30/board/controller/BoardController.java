package com.kosa.pro30.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.kosa.pro30.board.domain.AttacheFileDTO;
import com.kosa.pro30.board.domain.BoardDTO;
import com.kosa.pro30.board.service.AttacheFileService;
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
import com.kosa.pro30.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	

	private static final String CURR_IMAGE_REPO_PATH = "C:\\file_repo";
	
	
	
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
		
		// boardid로 특정 게시글 가져오기 파일도 가져오기
		@ResponseBody
		@RequestMapping("/board/detail.do")
		public Map<String,Object> getboarddetail(@RequestBody BoardDTO board, HttpServletRequest req, HttpServletResponse res) throws Exception {
			System.out.println("게시판 상세보기 파일 상세보기 가져오기");
			//boardDetail 보드 상세보기 키값
			//fileList 파일 상세보기 키값
			return boardService.detail(board);
		}

		//게시글 등록
		@ResponseBody
		@RequestMapping("/board/insert.do")
		public Map<String,Object> insert(BoardDTO board, MultipartHttpServletRequest multipartRequest) throws Exception {
			System.out.println("보드 컨트롤러 : 게시글 등록 ");
			Map<String, Object> jsonObject = new HashMap<>();
			 List fileList = fileProcess(multipartRequest);
			 boardService.insert (board, fileList);

				jsonObject.put("status", true);
				jsonObject.put("message", "게시글 등록이 완료되었습니다");

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
		
	//파일 업로드
		@ResponseBody
		private List<AttacheFileDTO> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
			System.out.println("파일 업로드 컨트롤러");
			
			List<AttacheFileDTO> fileList = new ArrayList<>();
			Iterator<String> fileNames = multipartRequest.getFileNames();
			Calendar now = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("\\yyyy\\MM\\dd");
			
			while(fileNames.hasNext()){
				String fileName = fileNames.next();
				MultipartFile mFile = multipartRequest.getFile(fileName);
				String fileNameOrg = mFile.getOriginalFilename();
				String realFolder = sdf.format(now.getTime());
				
				File file = new File(CURR_IMAGE_REPO_PATH + realFolder);
				if (file.exists() == false) {
					file.mkdirs();
				}

				String fileNameReal = UUID.randomUUID().toString();
				
				//파일 저장 
				mFile.transferTo(new File(file, fileNameReal)); //임시로 저장된 multipartFile을 실제 파일로 전송

				
				fileList.add(AttacheFileDTO.builder()
						.fileNameOrg(fileNameOrg)
						.fileNameReal(realFolder + "\\" + fileNameReal)
						.length((int) mFile.getSize())
						.contentType(mFile.getContentType())
						.build()); 
				
			}
			
			System.out.println(fileList);
			return fileList;
		}	

}
