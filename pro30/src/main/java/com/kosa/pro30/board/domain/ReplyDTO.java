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
@AllArgsConstructor 
public class ReplyDTO {

		private int boardid; 		// 게시글번호
		private int replyid; 		// 댓글번호
		private String title;		// 제목
		private String contents;	// 내용
		private String writer_uid;	// 글쓴이
		private Date reg_date;		// 작성날짜
		private Date mod_date;		// 수정날짜
		private int view_count;		// 조회수
		private String delete_yn; 	// 삭제유무
		private int startnum;
		private int endnum;
		

}
