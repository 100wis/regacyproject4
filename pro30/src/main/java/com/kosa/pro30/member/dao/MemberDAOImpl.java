package com.kosa.pro30.member.dao;

import java.util.List;

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
		//�쉶�썝 �젙蹂� �닔�젙�븯怨�
		sqlSession.selectOne("mapper.member.update", memberDTO);
		//�닔�젙�맂 �쉶�썝 �젙蹂� 由ы꽩�븯湲�
		return sqlSession.selectOne("mapper.member.getUserInfo", memberDTO);
	}

	@Override
	public int insert(MemberDTO memberDTO) {
		return sqlSession.insert("mapper.member.insert", memberDTO);
		
	}

	@Override
	public MemberDTO getUser(MemberDTO memberDTO) {
		System.out.println("start getuser");
		MemberDTO getuser = sqlSession.selectOne("mapper.member.getUserInfo", memberDTO);
		System.out.println(getuser);
		return getuser;
	}

	@Override
	public void delete(MemberDTO memberDTO) {
		sqlSession.delete("mapper.member.delete", memberDTO);
	}

	@Override
	public String getID(MemberDTO memberDTO) {
		return sqlSession.selectOne("mapper.member.lookforID", memberDTO);
	}

	@Override
	public String getPWD(MemberDTO memberDTO) {
		 return sqlSession.selectOne("mapper.member.lookforPWD", memberDTO);
	}

	@Override
	public List<MemberDTO> getList() {
		return sqlSession.selectList("mapper.member.list");	
	}



}
