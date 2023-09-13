package com.kosa.pro30.member.dao;

import com.kosa.pro30.member.domain.MemberDTO;

public interface MemberDAO {
	
	//1.로그인

	public MemberDTO update(MemberDTO memberDTO);

	public int insert(MemberDTO memberDTO);

	public MemberDTO getUser(MemberDTO memberDTO);
	
	public void delete(MemberDTO memberDTO);
	
	

} 
