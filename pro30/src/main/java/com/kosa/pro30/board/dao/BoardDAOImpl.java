package com.kosa.pro30.board.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosa.pro30.board.domain.BoardDTO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private  SqlSession sqlSession;

	
//1. 게시판 목록
	@Override
	public List<BoardDTO> getPagingcontents(BoardDTO board) {
		return sqlSession.selectList("mapper.board.getPagingcontents", board);
	}
//top5출력하기
	@Override
	public List<BoardDTO> boardTop5() {

			return sqlSession.selectList("mapper.board.boardTop5");
		
	}
	@Override
	public int totalcount() {
		return sqlSession.selectOne("mapper.board.totalcount");
	}
	
	



} // end class