package com.kosa.pro30.notice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	
	
	

	
	
	
	
	
	
	


	@Override
	public boolean insertNotice(NoticeDTO notice) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateNotice(NoticeDTO notice) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteNotice(int noticeid) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<NoticeDTO> noticeTop5() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int viewCount(int noticeid) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteNotices(String[] noticeids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCount(NoticeDTO notice) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<NoticeDTO> getNoticeListBoforeN(NoticeDTO notice, int length) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}






	
} // end class