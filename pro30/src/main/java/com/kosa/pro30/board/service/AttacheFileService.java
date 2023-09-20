package com.kosa.pro30.board.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.pro30.board.dao.AttacheFileDAO;
import com.kosa.pro30.board.dao.BoardDAO;
import com.kosa.pro30.board.domain.AttacheFileDTO;
import com.kosa.pro30.board.domain.BoardDTO;

@Service
public class AttacheFileService {
	
	@Autowired
	private AttacheFileDAO attacheFileDAO;
	
	public List<AttacheFileDTO> getAttacheFileList(BoardDTO board) throws Exception{
		System.out.println("리스트 함수 호출됨");
		return attacheFileDAO.getList(board);
	}

	//2. 첨부 파일 상세보기
	public AttacheFileDTO getAttacheFile(String fileNo) throws Exception {
		System.out.println("AttacheFileService.getAttacheFile() 함수 호출됨");
		
		return attacheFileDAO.getAttacheFile(fileNo);
	} // getBoard

	public void insert(AttacheFileDTO plus_file_to_DB) {
		System.out.println("업로드 파일 DB에 추가 서비스");
		attacheFileDAO.insert(plus_file_to_DB);
		System.out.println(plus_file_to_DB);
	}
	
	


} // end class
