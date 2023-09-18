package com.kosa.pro30.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.kosa.pro30.board.domain.ReplyDTO;

@Repository("ReplyDAO")
public class ReplyDAOImpl implements ReplyDAO {
	
	@Autowired
	private  SqlSession sqlSession;

	//댓글 리스트 불러오기
	@Override
	public List<ReplyDTO> getReplyList(ReplyDTO reply) {
		System.out.println("replyimpl온 보드 객체 : " + reply);
		
		return sqlSession.selectList("mapper.reply.getView10",reply) ;
	}
	
	//댓글 등록
	@Override
	public int insert(ReplyDTO reply) {
		System.out.println("DAOimpl insert에온 댓글 객체 : " + reply);
		int result = sqlSession.insert("mapper.reply.insert", reply);
		return result;
	}

	// 댓글 가져오기
	@Override
	public ReplyDTO getLastreply() {
		System.out.println("replyDAO 가장 최근 댓글 가져오기");
		return sqlSession.selectOne("mapper.reply.getLastreply");
	}

	// 댓글 삭제
	@Override
	public int delete(ReplyDTO reply) {
		System.out.println("DAOimpl delete에  온 댓글객체: " + reply);
		int result = sqlSession.update("mapper.reply.delete",reply);
		return result;
	}

	@Override
	public int update(ReplyDTO reply) {
		System.out.println("DAOimpl update에  온 댓글객체: " + reply);
		int result = sqlSession.update("mapper.reply.update",reply);
		return result;
	}

	// 댓글 객체 하나 가져오기
	@Override
	public ReplyDTO getreply(ReplyDTO reply) {
		System.out.println("DAOimpl getreply에  온 댓글객체: " + reply);
		return sqlSession.selectOne("mapper.reply.getreply", reply);
	}
	

}
