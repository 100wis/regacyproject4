package com.kosa.pro30.board.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosa.pro30.board.dao.AttacheFileDAO;
import com.kosa.pro30.board.dao.BoardDAO;
import com.kosa.pro30.board.domain.AttacheFileDTO;
import com.kosa.pro30.board.domain.BoardDTO;

@Service
public class BoardService {
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private AttacheFileDAO attacheFileDAO;
	
	@Autowired
	private AttacheFileService attacheFileService;

	// 게시판 재정의
	public BoardService() {
	
	}
	
	//게시판 목록
	public List<BoardDTO> getPagingcontents(BoardDTO board) throws Exception {
		System.out.println("board.service.getBoardList() 함수 호출됨");
		return boardDAO.getPagingcontents(board);	
	}

	//메인에 TOP5 출력하기
		public List<BoardDTO> boardTop5() throws Exception {
			System.out.println("board.service.boardTop5() 함수 호출됨");
			return boardDAO.boardTop5();
		} 

		
	//총 보드 갯수 가져오기
		public int totalcount() {
			return boardDAO.totalcount();
		}
		
	//게시판 상세내용 가져오기 파일 상세내용도 가져오기
		public Map<String, Object> detail(BoardDTO board) throws Exception {
			System.out.println("뷰카운트 증가  서비스");
			boardDAO.plusViewcount(board);
			
			
			System.out.println("보드서비스 : 게시글 상세정보 ");
	
			 Map<String, Object> jsonObject = new HashMap<>();
			 jsonObject.put("boardDetail", boardDAO.getDetail(board));
			 jsonObject.put("fileList", attacheFileService.getAttacheFileList(board));
			 
			 
			
			return jsonObject;
		}
		
	//게시글 등록
		@Transactional
		public boolean insert(BoardDTO board, List<AttacheFileDTO> fileList) {
			System.out.println("보드 서비스 : 게시글 등록 ");
			int boardid = boardDAO.insert(board);
			
			if(fileList != null) {
				for(AttacheFileDTO attachfile : fileList ) {
					attachfile.setBoardid(boardid);
					attacheFileService.insert(attachfile);
				}
			}
			return true;
		}
		
	//게시글 수정하기	
		public int update(BoardDTO board) {
			System.out.println("보드 서비스 : 게시글 수정 ");
			return boardDAO.update(board);
		}
		
	//게시글 삭제
		public int delete(BoardDTO board) {
			System.out.println("보드 서비스 : 게시글 삭제 ");
			return boardDAO.delete(board);
		}
	//검색된 내용 가져오기	
		public List<BoardDTO> SearchTitle(BoardDTO board) throws Exception {
			System.out.println("검색 서비스");
			return boardDAO.SearchTitle(board);	
		}


		public int getLastInsertBoardid() {
			System.out.println("마지막 보드 아이디 가져오기 서비스");
			return boardDAO.getLastInsertBoardid();
		}
	
	
	


} // end class
