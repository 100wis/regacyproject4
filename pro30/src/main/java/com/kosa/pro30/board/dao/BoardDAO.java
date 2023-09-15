package com.kosa.pro30.board.dao;

import java.util.List;
import java.util.Map;

import com.kosa.pro30.board.domain.BoardDTO;

public interface BoardDAO {
	
	//1. 게시판 목록

	public List<BoardDTO> boardTop5();
	
	public int totalcount();

	public List<BoardDTO> getPagingcontents(BoardDTO board);
	
	public BoardDTO getDetail(BoardDTO board);
	
	public int insert(BoardDTO board);
	
	public int update(BoardDTO board);
	
	public int delete(BoardDTO board);



	
} // end class
