package com.kosa.pro30.member.dao;

import java.util.List;

import com.kosa.pro30.member.domain.MemberDTO;

public interface MemberDAO {
	
	//1.로그인

	public MemberDTO update(MemberDTO memberDTO);

	public int insert(MemberDTO memberDTO);

	public MemberDTO getUser(MemberDTO memberDTO);
	
	public void delete(MemberDTO memberDTO);

	public String getID(MemberDTO memberDTO);

	public String getPWD(MemberDTO memberDTO);

	public List<MemberDTO> getList();
	
	

} 
