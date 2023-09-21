package com.kosa.pro30.notice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.pro30.board.domain.BoardDTO;
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
	
	//공지사항 목록
	public  Map<String, Object> getNoticeList(NoticeDTO notice) throws Exception {
		System.out.println("NoticeService.setNoticeDAO() 함수 호출됨");
		
		//1. 전체 건수를 얻는다
		notice.setTotalCount(noticeDAO.totallidstcount());
		
		Map<String, Object> result = new HashMap<>();
		result.put("notice", notice);
		result.put("list", noticeDAO.getNoticeList(notice));
		
		return result;
	} 
	
	//검색 페이징 
	public Map<String, Object> getSearchedList(NoticeDTO notice) {
		System.out.println("검색 페이징 서비스 진입");
		
		//1. 전체 건수를 얻는다
		notice.setTotalCount(noticeDAO.SearchedTotalCount(notice));
		

		Map<String, Object> result = new HashMap<>();
		result.put("notice", notice);
		result.put("list", noticeDAO.getSearchedList(notice));
		return result;
	}
	
	
	//공지사항 상세보기
	public NoticeDTO getNotice(NoticeDTO notice) throws Exception {
		System.out.println("공지사항 상세보기 서비스");
	return noticeDAO.getNotice(notice);
	}
	
	
	//공지사항 등록
	public Map<String, Object> insert(NoticeDTO notice) {
		System.out.println("공지사항 등록 서비스 진입");
		Map<String, Object> map = new HashMap<>();		
		
		int success = noticeDAO.insert(notice);
		if(success >0) {
			map.put("status",true);
			map.put("message", "공지사항이 등록되었습니다.");
		}else {
			map.put("status",false);
			map.put("message", "공지사항이 등록에 문제가 발생했습니다.");
		}
		return map;
	}
	
	
	//공지사항 수정
	public Map<String, Object> update(NoticeDTO notice) {
		System.out.println("공지사항 수정 서비스 진입");
		Map<String, Object> map = new HashMap<>();		
		
		int success = noticeDAO.update(notice);
		if(success >0) {
			map.put("status",true);
			map.put("message", "공지사항이 수정되었습니다.");
		}else {
			map.put("status",false);
			map.put("message", "공지사항이 수정에 문제가 발생했습니다.");
		}
		return map;
	}
	
	//공지사항 삭제
	public Map<String, Object> deleteOne(NoticeDTO notice) {
		System.out.println("공지사항 삭제 서비스 진입");
		Map<String, Object> map = new HashMap<>();		
		
		int success = noticeDAO.deleteOne(notice);
		if(success >0) {
			map.put("status",true);
			map.put("message", "공지사항이 삭제되었습니다.");
		}else {
			map.put("status",false);
			map.put("message", "공지사항이 삭제에 문제가 발생했습니다.");
		}
		return map;
	}

//공지사항 체크박스로 여러 건 삭제하기
	public Map<String, Object> deleteCheck(NoticeDTO notice) {
		System.out.println("공지사항 체크박스 여러건 삭제 서비스 진입");
		Map<String, Object> map = new HashMap<>();		
		
		int success = noticeDAO.deleteCheck(notice);
		System.out.println(success);
		if(success >0) {
			map.put("status",true);
			map.put("message", "공지사항이 총  "+ success+"건 삭제되었습니다.");
		}else {
			map.put("status",false);
			map.put("message", "공지사항이 삭제에 문제가 발생했습니다.");
		}
		return map;
	}	
	
	
//공지사항 체크박스로 여러 건 고정
	public Map<String, Object> fixes(NoticeDTO notice) {
		System.out.println("공지사항 체크박스 여러건 고정 서비스 진입");
		Map<String, Object> map = new HashMap<>();		
		
		int success = noticeDAO.fixes(notice);
		System.out.println(success);
		if(success >0) {
			map.put("status",true);
			map.put("message", "총"+ success+"건이  고정되었습니다.");
		}else {
			map.put("status",false);
			map.put("message", "고정에 실패했습니다.");
		}
		return map;
	}	
	

//공지사항 체크박스로 여러 건 고정해제
	public Map<String, Object> none_fixes(NoticeDTO notice) {
		System.out.println("공지사항 체크박스 여러건 고정해제 서비스 진입");
		Map<String, Object> map = new HashMap<>();		
		
		int success = noticeDAO.none_fixes(notice);
		System.out.println(success);
		if(success >0) {
			map.put("status",true);
			map.put("message", "총"+ success+"건이  고정해제 되었습니다.");
		}else {
			map.put("status",false);
			map.put("message", "고정해제에 실패했습니다.");
		}
		return map;
	}	
	
	
	//메인에 TOP5 출력하기
	public List<NoticeDTO> noticeTop5() throws Exception {
		System.out.println("notice.service.noticeTop5() 함수 호출됨");
		return noticeDAO.noticeTop5();
	} 


} // end class
