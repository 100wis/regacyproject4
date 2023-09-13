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
	public List<BoardDTO> getBoardList() throws Exception {
		return sqlSession.selectList("mapper.board.getBoardList");
	}
	
	//2. 게시판 상세보기
	@Override
	public BoardDTO getBoard(int boardid) throws Exception {
		return sqlSession.selectOne("mapper.board.getBoard", boardid);
	} // getBoard

	
	//3. 게시판 글쓰기
	@Override
	public boolean insertBoard(BoardDTO board) throws Exception {
		return 0 != sqlSession.insert("mapper.board.insertBoard", board);
	} //insertBoard

	
	//4. 게시판 수정하기
	@Override
	public boolean updateBoard(BoardDTO board) throws Exception {
		return 0 != sqlSession.update("mapper.board.updateBoard", board);
	} //updateBoard

	
	//5. 게시판 삭제하기
	@Override
	public boolean deleteBoard(int boardid) throws Exception {
		return 0 != sqlSession.delete("mapper.board.deleteBoard", boardid);
	} // deleteBoard

	
	//6. 메인에 TOP5 출력하기
	@Override
	public List<BoardDTO> boardTop5() throws Exception {
		return sqlSession.selectList("mapper.board.boardTop5");
	} // boardTop5


	//7. 조회수 증가
	@Override
	public int viewCount(int boardid) throws Exception {
		return sqlSession.update("mapper.board.boardid", boardid);
	} // viewCount


	//8. 체크박스된 게시글 삭제
	/*
	 * 삭제시 할  게시물아이디는 배열로 구성하여 list라는 이름으로 전달 한다 
	 */
	@Override
	public int deleteBoards(Map<String, Object> params) throws Exception {
		return sqlSession.delete("mapper.board.deleteBoards", params);
		
	 } // deleteBoards

	
	//9. 더보기 만들기
	@Override
	public List<BoardDTO> getBoardList2(BoardDTO board) throws Exception {
		return sqlSession.selectList("mapper.board.getBoardList2", board);
	}
	
	
	// 게시글 삭제해도 다시 글 10개씩 보이게 하는 메서드
	public List<BoardDTO> getboardListBoforeN(BoardDTO board) throws Exception {
		return sqlSession.selectList("mapper.board.getboardListBoforeN", board);
	}

	@Override
	public int reply(BoardDTO board) {
		return sqlSession.insert("mapper.board.insertBoardReply", board);
	}

} // end class