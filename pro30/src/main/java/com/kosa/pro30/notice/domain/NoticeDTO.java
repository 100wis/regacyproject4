package com.kosa.pro30.notice.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {

	// 필드
	private int noticeid; 		// 게시글번호
	private String title;		// 제목
	private String contents = "";	// 내용
	private String writer_uid;	// 글쓴이
	private Date reg_date;		// 작성날짜
	private Date mod_date;		// 수정날짜
	private int view_count;		// 조회수
	private String delete_yn; 	// 삭제유무
	private String fixed_yn; 	// 고정유무
	
	private String [] ids;         // 삭제시 사용될 아이디들
	
	//검색필드
	private String searchTitle = "";
	
	//페이징필드
	private int pageNo = 1;     // 현재 페이지 번호
	private int totalCount;     // 전체 건수 (건수가 있어야 페이지 계산함..)
	private int totalPageSize;  // 전체 페이지수 (계산된 페이지 개수)
	private int pageLength = 10;// 한 페이지당 보여줄 게시물 갯수(크기)
	private int navSize = 10;   // 페이지 하단에 출력되는 페이지의 항목수 (보통 10개씩 보여줌)
	private int navStart = 0;   // 페이지 하단에 출력되는 페이지 시작 번호 : NavStart = ((PageNo-1) / NavSize) * NavSize + 1
	private int navEnd = 0;     // 페이지 하단에 출력되는 페이지 끝 번호 : NavEnd = ((PageNo-1) / NavSize + 1) * NavSize
	
	
	// 글 작성 시 엔터키 역할 해주는 메서드
	public String getContentsHTML() {
		return contents.replace("\n", "<br/>");
	}
	
	// 페이징 메서드
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		//2. 전체 페이지 건수를 계산한다 
		totalPageSize = (int) Math.ceil((double) totalCount / pageLength);
		
		//3. 페이지 네비게이터 시작 페이지를 계산한다
		navStart = ((pageNo - 1) / navSize) * navSize + 1;
		
		//4. 페이지 네비게이터 끝 페이지를 계산한다
		navEnd = ((pageNo - 1) / navSize + 1) * navSize;
		
		//5. 전체 페이지 보다 크면 전체 페이지 값을 변경한다
		if (navEnd >= totalPageSize) {
			navEnd = totalPageSize;
		}
		
	}
	
	
	// 한 페이지의 시작 숫자 (보통 1, 11, 21, 31 ... )
	public int getStartNo() { 
	
		return (pageNo - 1) * pageLength + 1; 
	} //getStartNo
	
	// 한 페이지의 끝 숫자 (보통 10, 20, 30... 이랑 완전 끝 페이지임)
	public int getEndNo() { 
	
		return pageNo * pageLength; 
	} //getEndNo
	
	
} // end class
