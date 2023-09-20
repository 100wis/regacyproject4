package com.kosa.pro30.board.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosa.pro30.board.domain.AttacheFileDTO;
import com.kosa.pro30.board.domain.BoardDTO;

@Repository("attacheFileDAO")
public class AttacheFileDAOImpl implements AttacheFileDAO {
	@Autowired
	private  SqlSession sqlSession;

	
	//1. 첨부파일 목록
	@Override
	public List<AttacheFileDTO> getList(BoardDTO board) throws Exception {
		return sqlSession.selectList("mapper.attache_file.getList", board);
	}


	@Override
	public AttacheFileDTO getAttacheFile(String fileNo) {
		return sqlSession.selectOne("mapper.attache_file.getAttacheFile", fileNo);
	}

	// 첨부파일 DB에 넣기
	@Override
	public void insert(AttacheFileDTO attacheFile) {
		sqlSession.insert("mapper.attache_file.insert", attacheFile);
	}
	

} // end class