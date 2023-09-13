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
	public List<BoardDTO> getBoardList() throws Exception {
		System.out.println("board.service.getBoardList() 함수 호출됨");
	      
		return boardDAO.getBoardList();
		
	} // getBoardList
		
	
	//2. 게시판 상세보기
	public BoardDTO getBoard(int boardid) throws Exception {
		System.out.println("board.service.getBoard() 함수 호출됨");
		
		BoardDTO boardDTO = boardDAO.getBoard(boardid);
		boardDTO.setAttacheFileList(attacheFileDAO.getList(boardDTO));
		
		return boardDTO;
	} // getBoard
	
	
	//3. 게시판 글쓰기
	public boolean boardInsert(BoardDTO board) throws Exception {
		System.out.println("board.service.boardInsert() 함수 호출됨");
		
		return boardDAO.insertBoard(board);
	} // boardInsert
	
	
	//4. 게시판 수정하기
	public boolean boardUpdate(BoardDTO board) throws Exception {
		System.out.println("board.service.boardUpdate() 함수 호출됨");
		
		return boardDAO.updateBoard(board);
	} // updateBoard

	
	//5. 게시판 삭제하기
	public boolean boardDelete(int boardid) throws Exception {
		System.out.println("board.service.boardDelete() 함수 호출됨");

		return boardDAO.deleteBoard(boardid);
	} // deleteBoard

	
	//6. 메인에 TOP5 출력하기
	public List<BoardDTO> boardTop5() throws Exception {
		System.out.println("board.service.boardTop5() 함수 호출됨");
		
		return boardDAO.boardTop5();
	} // boardTop5
	
	
	//7. 조회수 증가
	public int viewCount(int boardid) throws Exception {
		System.out.println("board.service.viewCount() 함수 호출됨");
		
		return boardDAO.viewCount(boardid);
	} // viewCount
	
	
	//8. 체크박스 게시글 삭제하기
	public boolean deleteBoards(String[] boardids) throws Exception {
		System.out.println("boardIds.toString() : " + Arrays.toString(boardids));
		System.out.println("board.service.deleteBoards() 함수 호출됨");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("list", boardids);
		return 0 != boardDAO.deleteBoards(params);
	} // deleteBoards
	
	
	//9. 더보기 출력하기
	public List<BoardDTO> getBoardList2(BoardDTO board) throws Exception {
		System.out.println("board.service.getBoardList2() 함수 호출됨 -> " + board);
		
		return boardDAO.getBoardList2(board);
	} // getBoardList2

	public int reply(BoardDTO board) {
		
		int result = boardDAO.reply(board);
		
		if (board.getAttacheFileList() != null) {
			for (AttacheFileDTO attacheFile : board.getAttacheFileList()) {
				attacheFile.setBoardid(board.getBoardid());
				attacheFileDAO.insert(attacheFile);
			}
		}
		
		return result; 
	}
	
	


} // end class
