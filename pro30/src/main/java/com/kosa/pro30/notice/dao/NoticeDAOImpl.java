package com.kosa.pro30.notice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.kosa.pro30.notice.domain.NoticeDTO;

import oracle.jdbc.OracleTypes;

@Repository("noticeDAO")
public class NoticeDAOImpl implements NoticeDAO {
	@Autowired
	private  SqlSession sqlSession;
	
// 페이징 공지사항 가져오기
	@Override
	public List<NoticeDTO> getNoticeList(NoticeDTO notice) throws Exception {
		System.out.println("공지사항 리스트 가져오기 DAOImpl");
		return sqlSession.selectList("mapper.notice.getPagingcontents",notice);
	}
	
// 전체 공지사항 갯수 가져오기	
	@Override
	public int totallidstcount() {
		System.out.println("공지사항 전제 갯수 DAOImpl");
		return sqlSession.selectOne("mapper.notice.totalcount");
	}
	
//검색 된 공지사항의 전체 갯수 가져오기	
	@Override
	public int SearchedTotalCount(NoticeDTO notice) {
		System.out.println("검색 된 공지사항 전제 갯수 DAOImpl");
		return sqlSession.selectOne("mapper.notice.Searchtotalcount",notice);
	}
	
//검색 된 페이징 된  공지사항 가져오기
	@Override
	public List<NoticeDTO> getSearchedList(NoticeDTO notice) {
		System.out.println("검색 된 공지사항 리스트 가져오기 DAOImpl");
		return sqlSession.selectList("mapper.notice.getSearchedList",notice);
	}

// 공지사항 상세보기
	@Override
	public NoticeDTO getNotice(NoticeDTO notice) throws Exception {
		System.out.println("공지사항 상세 보기 DAOImpl");
		return sqlSession.selectOne("mapper.notice.detail",notice);
	}

// 공지사항 등록	
	@Override
	public int insert(NoticeDTO notice) {
		System.out.println("공지사항 등록 DAOImpl");
		return sqlSession.insert("mapper.notice.insert",notice);
	}
	
// 공지사항 수정	
	@Override
	public int update(NoticeDTO notice) {
		System.out.println("공지사항 수정 DAOImpl");
		return sqlSession.update("mapper.notice.update",notice);
	}

// 공지사항 수정	
	@Override
	public int deleteOne(NoticeDTO notice) {
		System.out.println("공지사항 한개 삭제 DAOImpl");
		Map<String, Object> del = new HashMap<>();
		del.put("noticeid", notice.getNoticeid());
	
		sqlSession.selectOne("mapper.notice.deleteOne",del);
		String row = (String) del.get("DELETEYN");
		int rownum = Integer.parseInt(row);
		System.out.println("rownum : "+rownum);
		return rownum;
	}

	//공지사항 체크박스 여러건 삭제
	@Override
	public int deleteCheck(NoticeDTO notice) {
		System.out.println("공지사항 체크박스 여러건 삭제 DAOImpl 진입");
		Map<String, Object> del = new HashMap<>();
		String noticeids = "";
		for(String noticeid : notice.getIds()) {
			noticeids += ","+noticeid;
		}
		
		noticeids = noticeids.substring(1);
		
		
		
		del.put("ids", noticeids);
		sqlSession.selectOne("mapper.notice.deleteCheck",del);
		String row = (String) del.get("DELETE_COUNT");
		int rownum = Integer.parseInt(row);
		System.out.println("rownum : "+rownum);
		return rownum;
	}
	
	
	//공지사항 체크박스 여러건 고정하기
	@Override
	public int fixes(NoticeDTO notice) {
		System.out.println("공지사항 체크박스 여러건 고정 DAOImpl 진입");
		Map<String, Object> fix = new HashMap<>();
		String noticeids = "";
		
		//스트링 배열을 문자열로 붙이기
		for(String noticeid : notice.getIds()) {
			noticeids += ","+noticeid;
		}
		//처음에 들어간 , 제거
		noticeids = noticeids.substring(1);	
		//인자값 map으로 주기 위해
		fix.put("ids", noticeids);
		//sql쿼리문 실행
		sqlSession.selectOne("mapper.notice.fixes",fix);
		//문자열로 리턴
		String row = (String) fix.get("Fixed_COUNT");
		//문자열로 리턴된 값을 숫자형으로 형변환
		int rownum = Integer.parseInt(row);
		System.out.println("rownum : "+rownum);
		return rownum;
	}
	
	//공지사항 체크박스 여러건 고정해제하기
	@Override
	public int none_fixes(NoticeDTO notice) {
		System.out.println("공지사항 체크박스 여러건 고정해제 DAOImpl 진입");
		Map<String, Object> fix = new HashMap<>();
		String noticeids = "";
		
		//스트링 배열을 문자열로 붙이기
		for(String noticeid : notice.getIds()) {
			noticeids += ","+noticeid;
		}
		//처음에 들어간 , 제거
		noticeids = noticeids.substring(1);	
		//인자값 map으로 주기 위해
		fix.put("ids", noticeids);
		//sql쿼리문 실행
		sqlSession.selectOne("mapper.notice.none_fixes",fix);
		//문자열로 리턴
		String row = (String) fix.get("Fixed_COUNT");
		//문자열로 리턴된 값을 숫자형으로 형변환
		int rownum = Integer.parseInt(row);
		System.out.println("rownum : "+rownum);
		return rownum;
	}
		
	//탑파이브 출력
	@Override
	public List<NoticeDTO> noticeTop5() throws Exception {
		return sqlSession.selectList("mapper.notice.noticeTop5");
	}

} // end class