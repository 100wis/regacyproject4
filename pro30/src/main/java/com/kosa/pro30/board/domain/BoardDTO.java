package com.kosa.pro30.board.domain;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor // 글쓰기
public class BoardDTO {

	// 필드
	private int pid; 			// 게시글 부모번호, 부모가 없을 때 : 0
	private int boardid; 		// 게시글번호
	private String title;		// 제목
	private String contents;	// 내용
	private String writer_uid;	// 글쓴이
	private Date reg_date;		// 작성날짜
	private Date mod_date;		// 수정날짜
	private int view_count;		// 조회수
	private String delete_yn; 	// 삭제유무
	private int level;
    private int startnum ;
    private int endnum ;


	private String [] ids;         // 삭제시 사용될 아이디들
	
	private List<AttacheFileDTO> attacheFileList;
	
	//검색필드
	private String searchTitle = "";
	
	// 글 작성 시 엔터키 역할 해주는 메서드
	public String getContentsHTML() {
		return contents != null ? contents.replace("\n", "<br/>") : "";
	}
	

	
} // board

