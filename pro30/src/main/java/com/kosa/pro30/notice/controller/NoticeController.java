package com.kosa.pro30.notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.pro30.notice.domain.NoticeDTO;
import com.kosa.pro30.notice.service.NoticeService;

@Controller
public class NoticeController  {

	@Autowired
	private NoticeService service;
    
	
//	목록 & 상세 =====================================================================================
	
	
	// 1. 공지사항 전체 목록 페이지
	/*
	 * public String list(NoticeDTO notice, HttpServletRequest req,
	 * HttpServletResponse res) throws Exception {
	 * System.out.println("notice.controller.NoticeList() invoked.");
	 * 
	 * try { req.setAttribute("notice", service.getNoticeList()); } catch (Exception
	 * e) { e.printStackTrace(); }
	 * 
	 * return "notice/list.jsp";
	 * 
	 * }
	 */
	
	// 1-2. [페이징]공지사항 전체 목록 페이지
	public String list(NoticeDTO notice, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("notice.controller.NoticeList() invoked.");
		
		try { 
        	req.setAttribute("result", service.getNoticePageList(notice));
        } catch (Exception e) { 
        	e.printStackTrace();
        }
		
		return "notice/list.jsp";
	}
	
	
	// 2. 공지사항 상세 페이지
	@ResponseBody
	@RequestMapping(value="/notice/detail.do", method = RequestMethod.POST)
	public NoticeDTO detail(@RequestBody NoticeDTO notice, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("notice.controller.detail() invoked.");
    	System.out.println("뭔데 = " + notice);
    	//JSONObject result = new JSONObject();
		
    	NoticeDTO detail = service.getNotice(notice.getNoticeid());
    	System.out.println(detail);
    	
    	return detail;
//    	result.put("noticeid", detail.getNoticeid());
//    	result.put("title", detail.getTitle());
//    	result.put("contents", detail.getContents());
//    	result.put("writer_uid", detail.getWriter_uid());
//    	result.put("reg_date", detail.getReg_date());
//    	result.put("view_count", detail.getView_count());
//	
//    	return result.toString();
		
	}

	
//	등록 =====================================================================================
	
	
	// 3. 공지사항 등록 페이지
	public String insertForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("notice.controller.insertForm() invoked.");
    	
		return "notice/insert.jsp";
	}
	
	// 4. 공지사항 등록
	public String insert(NoticeDTO notice, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("notice.controller.insert() invoked.");
    	JSONObject jsonResult = new JSONObject();
    	boolean status = service.noticeInsert(notice);
    	
    	jsonResult.put("status", status);
    	jsonResult.put("message", status ? "공지사항 글 작성이 등록되었습니다" : "공지사항 글 작성시 오류가 발생하였습니다");
		
		return jsonResult.toString();
	}
	
	
//	삭제 =====================================================================================
	
	
	// 5. 공지사항 글 삭제
	public String delete(NoticeDTO notice, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("notice.controller.delete() invoked.");
    	JSONObject jsonResult = new JSONObject();
    	boolean status = service.noticeDelete(notice.getNoticeid());
    	
    	jsonResult.put("status", status);
    	jsonResult.put("message", status ? "공지사항 글이 삭제되었습니다" : "공지사항 글 삭제시 오류가 발생하였습니다");
		
		return jsonResult.toString();
	}
	
	// 6. 공지사항 글 다중 삭제
	public String deletes(NoticeDTO notice, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("notice.controller.deletes() ");
    	JSONObject jsonResult = new JSONObject();
    	boolean status = service.noticeDeletes(notice);
    	
    	jsonResult.put("status", status);
    	jsonResult.put("message", status ? "공지사항 글 삭제 되었습니다" : "공지사항 글 삭제시 오류가 발생하였습니다");
    	if (status) {
        	jsonResult.put("noticeList", service.getNoticeListBoforeN(notice));
    	}
    	
    	
		return jsonResult.toString();
	}
	
	
//	수정 =====================================================================================
	
	
	// 6. 공지사항 수정 페이지
	public String updateForm(NoticeDTO notice, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("notice.controller.updateForm() invoked.");
    	
		try { 
        	req.setAttribute("notice", service.getNotice(notice.getNoticeid()));
        } catch (Exception e) { 
        	e.printStackTrace();
        }

		return "notice/update.jsp";
	}
	
//	System.out.println("notice.controller.detail() invoked.");
//	System.out.println("뭔데 = " + notice);
//	JSONObject result = new JSONObject();
//	
//		NoticeDTO detail = service.getNotice(notice.getNoticeid());
//    	result.put("noticeid", detail.getNoticeid());
//    	result.put("title", detail.getTitle());
//    	result.put("contents", detail.getContents());
//    	result.put("writer_uid", detail.getWriter_uid());
//    	result.put("reg_date", detail.getReg_date());
//    	result.put("view_count", detail.getView_count());
//	
//    	return result.toString();
	
	// 7. 공지사항 수정
	public String update(NoticeDTO notice, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	System.out.println("notice.controller.update() invoked.");
    	JSONObject result = new JSONObject();
    	
    	boolean status = service.noticeUpdate(notice);
    	
    	result.put("status", status);
    	result.put("message", status ? "공지사항 글이 수정되었습니다" : "공지사항 글 수정 시 오류가 발생하였습니다");
		
    	return result.toString();
	}
	
	
//	TOP5 =====================================================================================
	
	
	// 8. TOP5
//	public String noticeTop5(NoticeDTO notice, HttpServletRequest req, HttpServletResponse res) throws Exception {
//    	System.out.println("notice.controller.noticeTop5() ");
//    	JSONObject jsonResult = new JSONObject();
//
//		try { 
//        	req.setAttribute("notice", service.noticeTop5());
//        } catch (Exception e) { 
//        	e.printStackTrace();
//        }
//		
//		return "main.jsp";
//	
//	}
	
	
	
} // end class
