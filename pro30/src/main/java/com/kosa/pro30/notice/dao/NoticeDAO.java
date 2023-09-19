package com.kosa.pro30.notice.dao;

import java.util.List;

import com.kosa.pro30.notice.domain.NoticeDTO;

public interface NoticeDAO {
	
	//1공지사항 목록
	public List<NoticeDTO> getNoticeList(NoticeDTO notice) throws Exception;
	
	//전체 공지사항 리스트 갯수 가져오기
	public int totallidstcount();
	
	//검색된 공지사항 리스트  총 갯수 가져오기
	public int SearchedTotalCount(NoticeDTO notice);
	
	//검색 된 공지사항 리스트 가져오기
	public List<NoticeDTO> getSearchedList(NoticeDTO notice);
	
	//공지사항 상세보기
	public NoticeDTO getNotice(NoticeDTO notice) throws Exception;
	
	
	
	

	
	//3. 공지사항 글쓰기
	public boolean insertNotice(NoticeDTO notice) throws Exception; 
	
	//4. 공지사항 수정하기
	public boolean updateNotice(NoticeDTO notice) throws Exception;

	//5. 공지사항 삭제하기
	public boolean deleteNotice(int noticeid) throws Exception;
	
	//6. 메인에 TOP5 출력하기
	public List<NoticeDTO> noticeTop5() throws Exception;
	
	//7. 조회수 증가
	public int viewCount(int noticeid) throws Exception;
	
	//8. 체크박스된 게시글 삭제
	public int deleteNotices(String[] noticeids) throws Exception;

	//9. [페이징]검색된 전체 건수 얻는다
	//	 공지사항 총 개수
	public int getTotalCount(NoticeDTO notice) throws Exception;
	// 	 공지사항 목록
	
	//10. 게시글 삭제 후 다시 10건으로 만들어주는 메서드
	public List<NoticeDTO> getNoticeListBoforeN(NoticeDTO notice, int length) throws Exception;


	
} // end class
