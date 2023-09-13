package com.kosa.pro30.notice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.pro30.notice.dao.NoticeDAO;
import com.kosa.pro30.notice.dao.NoticeDAOImpl;
import com.kosa.pro30.notice.domain.NoticeDTO;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	// 서비스 재정의
	public NoticeService() {
	
	}
	
	//1. 공지사항 목록
	public List<NoticeDTO> getNoticeList() throws Exception {
		System.out.println("NoticeService.setNoticeDAO() 함수 호출됨");
		
		return noticeDAO.getNoticeList();
	} // getNoticeList
		
	
	//1-2. 공지사항 페이지 목록 조회
	// 인자 : 페이지 번호
	//        검색키(X)
	public Map<String, Object> getNoticePageList(NoticeDTO notice) throws Exception {
		System.out.println("notice.service.getNoticeList() 페이징 목록 함수 호출됨");

		//1. 전체 건수를 얻는다
		notice.setTotalCount(noticeDAO.getTotalCount(notice));
		
		Map<String, Object> result = new HashMap();
		
		result.put("notice", notice);
		result.put("list", noticeDAO.getNoticeList(notice));
		
		return result;
		
	} // getNoticeList
	
	
	//2. 공지사항 상세보기
	public NoticeDTO getNotice(int noticeId) throws Exception {
		System.out.println("notice.service.getNotice() 함수 호출됨");
		
		return noticeDAO.getNotice(noticeId);
	} // getNotice
	
	
	//3. 공지사항 글쓰기
	public boolean noticeInsert(NoticeDTO notice) throws Exception {
		System.out.println("notice.service.noticeInsert() 함수 호출됨");
		
		return noticeDAO.insertNotice(notice);
	} // NoticeInsert
	
	
	//4. 공지사항 수정하기
	public boolean noticeUpdate(NoticeDTO noticeUpdate) throws Exception {
		
		return noticeDAO.updateNotice(noticeUpdate);
	} // updateNotice

	
	//5. 공지사항 삭제하기
	public boolean noticeDelete(int noticeid) throws Exception {
		System.out.println("notice.service.noticeDelete() 함수 호출됨");
		
		return noticeDAO.deleteNotice(noticeid);
	} // deleteNotice
	
	
	//6. 메인에 TOP5 출력하기
	public List<NoticeDTO> noticeTop5() throws Exception {
		System.out.println("notice.service.noticeTop5() 함수 호출됨");
		
		return noticeDAO.noticeTop5();
	} // noticeTop5
	
	
	//7. 조회수 증가
	public int viewCount(int noticeid) throws Exception {
		System.out.println("notice.service.viewCount() 함수 호출됨");
	
		return noticeDAO.viewCount(noticeid); 
	}
	
	
	//8. 체크박스 공지사항 게시글 삭제하기
	public int deleteNotices(String[] noticeids) throws Exception {
		System.out.println("notice.service.deleteNotices() 함수 호출됨");
		
		return noticeDAO.deleteNotices(noticeids);
	}

	
	// 메인
	public static void main(String [] args) throws Exception { 
		NoticeService noticeService = new NoticeService(); 
		noticeService.noticeDAO = new NoticeDAOImpl(); 
		NoticeDTO notice = new NoticeDTO(); 
		notice.setPageNo(11);
	
	    Map<String, Object> result = noticeService.getNoticePageList(notice);
	
	    System.out.println(result);
	
	}
	
	
	//9. 다중 삭제
	public boolean noticeDeletes(NoticeDTO notice) throws Exception {
		 
		return 0 != noticeDAO.deleteNotices(notice.getIds());
	}
	
	//10. 무슨용도지...
	public List<NoticeDTO> getNoticeListBoforeN(NoticeDTO notice) throws Exception{
		
		return noticeDAO.getNoticeListBoforeN(notice, notice.getIds().length);
	}
	


} // end class
