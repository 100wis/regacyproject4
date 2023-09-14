package com.kosa.pro30.board.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	// 게시판 재정의
	public BoardService() {
	
	}
	
	//1. 게시판 목록
	public List<BoardDTO> getPagingcontents(BoardDTO board) throws Exception {
		System.out.println("board.service.getBoardList() 함수 호출됨");
	      
		return boardDAO.getPagingcontents(board);
		
	} // getBoardList

	//6. 메인에 TOP5 출력하기
		public List<BoardDTO> boardTop5() throws Exception {
			System.out.println("board.service.boardTop5() 함수 호출됨");
			System.out.println();
			
			return boardDAO.boardTop5();
		} // boardTop5

		
		//총 보드 갯수 가져오기
		public int totalcount() {
			return boardDAO.totalcount();
		}
		
	
	
	


} // end class
