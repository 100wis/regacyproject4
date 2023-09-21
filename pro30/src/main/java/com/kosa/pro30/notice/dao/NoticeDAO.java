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

	// 공지사항 등록
	public int insert(NoticeDTO notice);
	
	// 공지사항 수정
	public int update(NoticeDTO notice);
	
	// 한건 삭제
	int deleteOne(NoticeDTO notice);
	
	//공지사항 체크박스 여러건 삭제
	public int deleteCheck(NoticeDTO notice);	
	
	//체크스로 고정
	int fixes(NoticeDTO notice);
	
	// 체크박스로 고정해제
	int none_fixes(NoticeDTO notice);	
	
	//6. 메인에 TOP5 출력하기
	public List<NoticeDTO> noticeTop5() throws Exception;
	

	





	
} // end class
