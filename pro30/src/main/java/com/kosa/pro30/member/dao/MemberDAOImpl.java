package com.kosa.pro30.member.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosa.pro30.member.domain.MemberDTO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private  SqlSession sqlSession;


	@Override
	public MemberDTO update(MemberDTO memberDTO) {
		//회원 정보 수정하고
		sqlSession.selectOne("mapper.member.update", memberDTO);
		//수정된 회원 정보 리턴하기
		return sqlSession.selectOne("mapper.member.getUserInfo", memberDTO);
	}

	@Override
	public int insert(MemberDTO memberDTO) {
		return sqlSession.insert("mapper.member.insert", memberDTO);
		
	}

	@Override
	public MemberDTO getUser(MemberDTO memberDTO) {
		return sqlSession.selectOne("mapper.member.getUserInfo", memberDTO);
	}

	@Override
	public void delete(MemberDTO memberDTO) {
		sqlSession.delete("mapper.member.delete", memberDTO);
	}



}
