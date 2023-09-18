package com.kosa.pro30.board.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
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
		System.out.println("DAOimpl온 보드 객체 : " + board);
	    return sqlSession.selectList("mapper.board.getPagingcontents",board);
	}
//top5출력하기
	@Override
	public List<BoardDTO> boardTop5() {
			return sqlSession.selectList("mapper.board.boardTop5");
	}
	
//총 게시글 갯수 
	@Override
	public int totalcount() {
		return sqlSession.selectOne("mapper.board.totalcount");
	}
	
//게시판 상세내용 
	@Override
	public BoardDTO getDetail(BoardDTO board) {
		System.out.println("DAOimpl__getDetail : " + board);
		return sqlSession.selectOne("mapper.board.getDetail", board);
	}
	
//게시글 등록
	@Override
	public int insert(BoardDTO board) {
		System.out.println("DAOimpl insert에  온 보드 객체 : " + board);
		int result = sqlSession.insert("mapper.board.insert",board);
		return result;
	}
	
//게시글 수정하기	
	@Override
	public int update(BoardDTO board) {
		System.out.println("DAOimpl update에  온 보드 객체 : " + board);
		int result = sqlSession.update("mapper.board.update",board);
		return result;
	}
	
//게시글 삭제
	@Override
	public int delete(BoardDTO board) {
		System.out.println("DAOimpl delete에  온 보드 객체 : " + board);
		int result = sqlSession.update("mapper.board.delete",board);
		return result;
	}
	
//게시글 검색
	@Override
	public List<BoardDTO> SearchTitle(BoardDTO board) {
		System.out.println("DAOimpl온 보드 객체 : " + board);
	    return sqlSession.selectList("mapper.board.SearchTitle",board);
	}
	@Override
	public void plusViewcount(BoardDTO board) {
		System.out.println("조회수증가 DAO");
		sqlSession.update("mapper.board.plusViewcount", board);
		
	}	




} 