package com.kosa.pro30.notice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.kosa.pro30.notice.domain.NoticeDTO;

import oracle.jdbc.OracleTypes;

@Component
public class NoticeDAOImpl implements NoticeDAO {
	
	public Connection getConnection() throws Exception {
		// 톰캣 서버에서 자원을 찾기 위해 InitialContext 인스턴스 생성
		InitialContext initialContext = new InitialContext();
		// lookup() 메소드로 JNDI 이름으로 등록돼있는 서버 자원 찾음
		// @name : 서버 자원의 JNDI 이름
		// 찾으려는 자원이 JDBC DataSource이므로 java:comp/env...
		DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/kosa");
		return ds.getConnection();
	}

	// 공지사항 목록
	@Override
	public List<NoticeDTO> getNoticeList() throws Exception {
		System.out.println("notice.noticeDAOImpl.getNoticeList() 게시판 목록 함수 호출됨");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		NoticeDTO notice = new NoticeDTO();
		List<NoticeDTO> noticeList = new ArrayList() ;
		
		try {
			conn = getConnection();
			
			String sql = "select noticeid, title, writer_uid, reg_date, view_count, fixed_yn from notice  order by fixed_yn DESC, CASE WHEN fixed_yn = 'y' THEN reg_date END DESC, reg_date DESC";
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {// -------------------------

				notice = NoticeDTO.builder()
								  .noticeid(rs.getInt("noticeid"))
		  			  			  .title(rs.getString("title"))
		  			  			  .writer_uid(rs.getString("writer_uid"))
		  			  			  .reg_date(rs.getDate("reg_date"))
		  			  			  .view_count(rs.getInt("view_count"))
		  			  			  .fixed_yn(rs.getString("fixed_yn"))
		  			  			  .build();
				
				
				noticeList.add(notice);

			}
			
			return noticeList; 
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
	

	// [페이징] 공지사항 목록
	@Override
	public List<NoticeDTO> getNoticeList(NoticeDTO notice) throws Exception {
	    System.out.println("notice.noticeDAOImpl.getNoticeList() [페이징]공지사항 목록 함수 호출됨");

		Connection conn = null;
		PreparedStatement pstmt = null;
		List<NoticeDTO> noticeList = new ArrayList() ;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select  O.* from ("
						+ "   select rownum nrow, b.* from ("
						+ "     select "
						+ "        a.* "
						+ "     from notice a";

			//검색 조건 설정 하는 부분   
			if (!notice.getSearchTitle().isEmpty()) {
				sql += " where title like concat(concat('%', ?), '%')";
			}
			
			sql += "     order by title "
						+ "   ) b "
						+ "   where rownum <= " + notice.getEndNo()
						+ " ) O"
						+ " where nrow between " + notice.getStartNo() + " and " + notice.getEndNo()
						+ " ";
			System.out.println(sql);
			
			pstmt = conn.prepareStatement(sql);
			if (!notice.getSearchTitle().isEmpty()) {
				pstmt.setString(1, notice.getSearchTitle());
			}
			
			rs = pstmt.executeQuery();

	        while (rs.next()) {
	            noticeList.add(NoticeDTO.builder()
	                    .noticeid(rs.getInt("noticeid"))
	                    .title(rs.getString("title"))
	                    .writer_uid(rs.getString("writer_uid"))
	                    .reg_date(rs.getDate("reg_date"))
	                    .view_count(rs.getInt("view_count"))
	                    .fixed_yn(rs.getString("fixed_yn"))
	                    .build());
	        }

	        return noticeList;

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (rs != null)
	            rs.close();
	        if (pstmt != null)
	        	pstmt.close();
	        if (conn != null)
	            conn.close();
	    }
	}
	
	
//	// [페이징] 공지사항 목록
//	@Override
//	public List<NoticeDTO> getNoticeList(NoticeDTO notice) throws Exception {
//	    System.out.println("notice.noticeDAOImpl.getNoticeList() [페이징]공지사항 목록 함수 호출됨");
//
//	    Connection conn = null;
//	    CallableStatement cstmt = null;
//	    ResultSet rs = null;
//	    List<NoticeDTO> noticeList = new ArrayList<>();
//
//	    try {
//	        conn = getConnection();
//
//	        // Prepare the callable statement
//	        cstmt = conn.prepareCall("{ call NOTICELIST(?) }");
//	        cstmt.registerOutParameter(1, OracleTypes.CURSOR); // Register the cursor as output parameter
//
//	        // Execute the procedure
//	        cstmt.execute();
//
//	        // Get the result cursor
//	        rs = (ResultSet) cstmt.getObject(1);
//
//	        while (rs.next()) {
//	            noticeList.add(NoticeDTO.builder()
//	                    .noticeid(rs.getInt("noticeid"))
//	                    .title(rs.getString("title"))
//	                    .writer_uid(rs.getString("writer_uid"))
//	                    .reg_date(rs.getDate("reg_date"))
//	                    .view_count(rs.getInt("view_count"))
//	                    .fixed_yn(rs.getString("fixed_yn"))
//	                    .build());
//	        }
//
//	        return noticeList;
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        throw e;
//	    } finally {
//	        if (rs != null)
//	            rs.close();
//	        if (cstmt != null)
//	            cstmt.close();
//	        if (conn != null)
//	            conn.close();
//	    }
//	}


	

	// 공지사항 상세
	@Override
	public NoticeDTO getNotice(int noticedid) throws Exception {
	    System.out.println("notice.noticeDAOImpl.getNotice() 함수 호출됨");

	    Connection conn = null;
	    CallableStatement cstmt = null;
	    NoticeDTO notice = new NoticeDTO();

	    try {
	        conn = getConnection();
	        cstmt = conn.prepareCall("{ call NOTICEDETAIL(?, ?) }");
	        cstmt.setInt(1, noticedid);
	        cstmt.registerOutParameter(2, OracleTypes.CURSOR);

	        cstmt.execute();

	        ResultSet rs = (ResultSet) cstmt.getObject(2);

	        if (rs.next()) {
	            notice = NoticeDTO.builder()
	                    .noticeid(rs.getInt("noticeid"))
	                    .title(rs.getString("title"))
	                    .contents(rs.getString("contents"))
	                    .writer_uid(rs.getString("writer_uid"))
	                    .reg_date(rs.getDate("reg_date"))
	                    .mod_date(rs.getDate("mod_date"))
	                    .view_count(rs.getInt("view_count"))
	                    .fixed_yn(rs.getString("fixed_yn"))
	                    .build();
	        }
	        rs.close();
	        return notice;

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (cstmt != null)
	            cstmt.close();
	        if (conn != null)
	            conn.close();
	    }
	}
	
	

	// 공지사항 글쓰기
	@Override
	public boolean insertNotice(NoticeDTO notice) throws Exception {
		System.out.println("notice.noticeDAOImpl.noticeInsert() 함수 호출됨");
		
		Connection conn = null;
		CallableStatement cstmt = null;
		PreparedStatement pstmt = null;
		boolean success = false;

		try {
			conn = getConnection();
			cstmt = conn.prepareCall("{ call NOTICEINSERT(?, ?, ?, ?, ?) }");
//			String sql = "insert into notice (noticeid, title, contents, writer_uid, fixed_yn) values (seq_board.nextval, ?, ?, ?, ?)" ;
//			pstmt = conn.prepareStatement(sql);
			cstmt.setInt(1, notice.getNoticeid());
			cstmt.setString(2, notice.getTitle());
			cstmt.setString(3, notice.getContents());
			cstmt.setString(4, notice.getWriter_uid());
			cstmt.setString(5, notice.getFixed_yn());
			
			int affectedRows = cstmt.executeUpdate();
			success = affectedRows > 0;
			
			return success; 
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
		}
	}
	
//	@Override
//	public boolean insertNotice(NoticeDTO notice) throws Exception {
//		System.out.println("notice.service.noticeInsert() 함수 호출됨");
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		boolean success = false;
//
//		try {
//			conn = getConnection();
//			String sql = "insert into notice (noticeid, title, contents, writer_uid, fixed_yn) values (seq_board.nextval, ?, ?, ?, ?)" ;
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, notice.getTitle());
//			pstmt.setString(2, notice.getContents());
//			pstmt.setString(3, notice.getWriter_uid());
//			pstmt.setString(4, notice.getFixed_yn());
//			
//			int affectedRows = pstmt.executeUpdate();
//			success = affectedRows > 0;
//			
//			return success; 
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if (pstmt != null)
//				pstmt.close();
//			if (conn != null)
//				conn.close();
//		}
//	}

	// 공지사항 수정
	// 공지사항 수정
	@Override
	public boolean updateNotice(NoticeDTO notice) throws Exception {
	    System.out.println("notice.noticeDAOImpl.noticeUpdate() 함수 호출됨");
	    CallableStatement cstmt = null;
	    Connection conn = null;
	    
	    try {
	        conn = getConnection();
	        
	        //String sql = "update Notice set title=?, contents=?, fixed_yn=? where noticeid=?" ;
	        cstmt = conn.prepareCall("{ call NOTICEUPDATE(?, ?, ?, ?, ?) }");
	        cstmt.setInt(1, notice.getNoticeid());
	        cstmt.setString(2, notice.getTitle());
	        cstmt.setString(3, notice.getContents());
	        cstmt.setString(4, notice.getWriter_uid());
	        cstmt.setString(5, notice.getFixed_yn());
	        
	        int affectedRows = cstmt.executeUpdate();
	        System.out.println("쿼리결과 = " + affectedRows);
	        boolean result = affectedRows > 0;

	        return result; 
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (cstmt != null)
	            cstmt.close();
	        if (conn != null)
	            conn.close();
	    }
	    
	}

	

	// 공지사항 삭제
	@Override
	public boolean deleteNotice(int noticeid) throws Exception {
		System.out.println("notice.noticeDAOImpl.noticeDelete() 함수 호출됨");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int affectedRows = 0;
		
		try {
			conn = getConnection();
			
			String sql = "delete from notice where noticeid=?" ;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeid);
			
			affectedRows = pstmt.executeUpdate();
			boolean result = affectedRows > 0;
	
			return result; 
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}

	// 메인페이지 TOP5 뽑기
	@Override
	public List<NoticeDTO> noticeTop5() throws Exception {
		System.out.println("notice.noticeDAOImpl.noticeTop5() Top5 목록 함수 호출됨");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		NoticeDTO notice = new NoticeDTO();
		
		// 공지사항 목록을 불러올 DTO의 리스트객체를 생성
		List<NoticeDTO> noticeTop5 = new ArrayList() ;
		
		try {
			conn = getConnection();
			
			String sql = "SELECT * " +
		             "FROM ( " +
		             "    SELECT /*+ index_desc(a PK_NOTICE_NOTICEID) */ " +
		             "        * " +
		             "    FROM notice a " +
		             "    WHERE fixed_yn='Y' " +
		             "    UNION ALL " +
		             "    SELECT /*+ index_desc(a PK_NOTICE_NOTICEID) */ " +
		             "        * " +
		             "    FROM notice a " +
		             "    WHERE fixed_yn='N' " +
		             ") " +
		             "WHERE rownum <= 5";


			
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {// -------------------------

				notice = NoticeDTO.builder()
								  .noticeid(rs.getInt("noticeid"))
		  			  			  .title(rs.getString("title"))
		  			  			  .writer_uid(rs.getString("writer_uid"))
		  			  			  .reg_date(rs.getDate("reg_date"))
		  			  			  .view_count(rs.getInt("view_count"))
		  			  			  .build();
				
				
				noticeTop5.add(notice);

			}
			
			return noticeTop5; 
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		
	} // noticeTop5

	
	// 조회수 증가
	@Override
	public int viewCount(int noticeid) throws Exception {
		System.out.println("notice.noticeDAOImpl.viewCount() 함수 호출됨");
		Connection conn = null;
		PreparedStatement pstmt = null;
		int viewCount;
		
		try {
			conn = getConnection();
			String sql = "update notice set view_count = view_count + 1 where noticeid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeid);
			viewCount = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		
		return viewCount;
	} // viewCount

	
	// 체크박스 선택된 게시글 삭제
	@Override
	public int deleteNotices(String[] noticeids) throws Exception {
		System.out.println("notice.noticeDAOImpl.deleteNotices() 함수 호출됨");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuilder params = new StringBuilder();
		int result;
		
		
		if(noticeids.length >= 1) {
			params.append("('");
			params.append(noticeids[0]);
			for(int i=1;i<noticeids.length;i++) {
			    params.append("','").append(noticeids[i]);
			}

			params.append("')");
		}
		
		try {
			conn = getConnection();
			String sql = "delete from notice where noticeid in "+ params.toString();
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		
		return result;
		
	} // deleteBoards

	
	// 게시글 총 개수 반환
	@Override
	public int getTotalCount(NoticeDTO notice) throws Exception {
		System.out.println("Notice.noticeDAOImpl.getTotalCount() 함수 호출됨");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {
			conn = getConnection();
			
			String sql = "select count(*) from notice ";
			if (!notice.getSearchTitle().isEmpty()) {
				sql += " where title like concat(concat('%', ?), '%')";
			}
			pstmt = conn.prepareStatement(sql);
			if (!notice.getSearchTitle().isEmpty()) {
				pstmt.setString(1, notice.getSearchTitle());
			}
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {// -------------------------
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		
		return count;
	}

	
	// 게시글 삭제해도 다시 글 10개씩 보이게 하는 메서드
	public List<NoticeDTO> getNoticeListBoforeN(NoticeDTO notice, int N) throws Exception {
		System.out.println("notice.noticeDAOImpl.getNoticeList() 게시판 목록 함수 호출됨");
			
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<NoticeDTO> noticeList = new ArrayList() ;
			
		try {
			conn = getConnection();
				
			String sql = "select * from ("
						+ "     select * from notice"
						+ "     where noticeid < ? "
						+ "     order by noticeid desc"
						+ " ) A"
						+ " where rownum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, notice.getNoticeid());
			pstmt.setInt(2, N);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {// -------------------------

				noticeList.add(NoticeDTO.builder()
						  .noticeid(rs.getInt("noticeid"))
	  		  			  .title(rs.getString("title"))
	  		  			  .writer_uid(rs.getString("writer_uid"))
	  		  			  .reg_date(rs.getDate("reg_date"))
	  		  			  .view_count(rs.getInt("view_count"))
	  		  			  .fixed_yn(rs.getString("fixed_yn"))
	  		  			  .build());

			}
				
			return noticeList; 
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
			
	}
		
	
} // end class