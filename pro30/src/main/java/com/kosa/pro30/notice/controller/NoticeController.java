package com.kosa.pro30.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.pro30.board.domain.BoardDTO;
import com.kosa.pro30.notice.domain.NoticeDTO;
import com.kosa.pro30.notice.service.NoticeService;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

//공지사항 리스트 가져오면서 페이지로 로드
	@RequestMapping("/notice/list.do")
	public String list(NoticeDTO notice, Model model) throws Exception {
		System.out.println("공지사항 리스트 출력");

		System.out.println("현재페이지 : " + notice.getPageNo());

		notice.getStartNo();
		notice.getEndNo();
		// model.addAttribute("noticelist",noticeService.getNoticeList(notice) );
		model.addAttribute("result", noticeService.getNoticePageList(notice));

		System.out.println("notice리스트제대로 왔는지: " + noticeService.getNoticeList(notice));

		return "notice/noticeList";
	}

//검색 boardList 가져오기
	@RequestMapping("/notice/titlesearch.do")
	public String SearchTitle(NoticeDTO notice, Model model) throws Exception {
		System.out.println("제목 검색 컨트롤러  " + notice.getSearchTitle());

		System.out.println("현재페이지 : " + notice.getPageNo());

		notice.getStartNo();
		notice.getEndNo();

		model.addAttribute("result", noticeService.getSearchedList(notice));
		return "notice/noticeList";

	}
	
//notice아이디로 상세 공지사항 가져오기
	@ResponseBody
	@RequestMapping("/notice/detail.do")
	public NoticeDTO getnoticedetail(@RequestBody NoticeDTO notice) throws Exception {
		System.out.println("공지사항 상세 컨트롤러");
		NoticeDTO detail = noticeService.getNotice(notice);
		System.out.println("상세정보 받아왔나 확인 : " + detail);

		return detail;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	/*
	 * 
	 * // 등록
	 * =============================================================================
	 * ========
	 * 
	 * 
	 * // 3. 공지사항 등록 페이지 public String insertForm(HttpServletRequest req,
	 * HttpServletResponse res) throws Exception {
	 * System.out.println("notice.controller.insertForm() invoked.");
	 * 
	 * return "notice/insert.jsp"; }
	 * 
	 * // 4. 공지사항 등록 public String insert(NoticeDTO notice, HttpServletRequest req,
	 * HttpServletResponse res) throws Exception {
	 * System.out.println("notice.controller.insert() invoked."); JSONObject
	 * jsonResult = new JSONObject(); boolean status = service.noticeInsert(notice);
	 * 
	 * jsonResult.put("status", status); jsonResult.put("message", status ?
	 * "공지사항 글 작성이 등록되었습니다" : "공지사항 글 작성시 오류가 발생하였습니다");
	 * 
	 * return jsonResult.toString(); }
	 * 
	 * 
	 * // 삭제
	 * =============================================================================
	 * ========
	 * 
	 * 
	 * // 5. 공지사항 글 삭제 public String delete(NoticeDTO notice, HttpServletRequest
	 * req, HttpServletResponse res) throws Exception {
	 * System.out.println("notice.controller.delete() invoked."); JSONObject
	 * jsonResult = new JSONObject(); boolean status =
	 * service.noticeDelete(notice.getNoticeid());
	 * 
	 * jsonResult.put("status", status); jsonResult.put("message", status ?
	 * "공지사항 글이 삭제되었습니다" : "공지사항 글 삭제시 오류가 발생하였습니다");
	 * 
	 * return jsonResult.toString(); }
	 * 
	 * // 6. 공지사항 글 다중 삭제 public String deletes(NoticeDTO notice, HttpServletRequest
	 * req, HttpServletResponse res) throws Exception {
	 * System.out.println("notice.controller.deletes() "); JSONObject jsonResult =
	 * new JSONObject(); boolean status = service.noticeDeletes(notice);
	 * 
	 * jsonResult.put("status", status); jsonResult.put("message", status ?
	 * "공지사항 글 삭제 되었습니다" : "공지사항 글 삭제시 오류가 발생하였습니다"); if (status) {
	 * jsonResult.put("noticeList", service.getNoticeListBoforeN(notice)); }
	 * 
	 * 
	 * return jsonResult.toString(); }
	 * 
	 * 
	 * // 수정
	 * =============================================================================
	 * ========
	 * 
	 * 
	 * // 6. 공지사항 수정 페이지 public String updateForm(NoticeDTO notice,
	 * HttpServletRequest req, HttpServletResponse res) throws Exception {
	 * System.out.println("notice.controller.updateForm() invoked.");
	 * 
	 * try { req.setAttribute("notice", service.getNotice(notice.getNoticeid())); }
	 * catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return "notice/update.jsp"; }
	 * 
	 * // System.out.println("notice.controller.detail() invoked."); //
	 * System.out.println("뭔데 = " + notice); // JSONObject result = new
	 * JSONObject(); // // NoticeDTO detail =
	 * service.getNotice(notice.getNoticeid()); // result.put("noticeid",
	 * detail.getNoticeid()); // result.put("title", detail.getTitle()); //
	 * result.put("contents", detail.getContents()); // result.put("writer_uid",
	 * detail.getWriter_uid()); // result.put("reg_date", detail.getReg_date()); //
	 * result.put("view_count", detail.getView_count()); // // return
	 * result.toString();
	 * 
	 * // 7. 공지사항 수정
	 * 
	 * public String update(NoticeDTO notice, HttpServletRequest req,
	 * HttpServletResponse res) throws Exception {
	 * System.out.println("notice.controller.update() invoked."); JSONObject result
	 * = new JSONObject();
	 * 
	 * boolean status = service.noticeUpdate(notice);
	 * 
	 * result.put("status", status); result.put("message", status ?
	 * "공지사항 글이 수정되었습니다" : "공지사항 글 수정 시 오류가 발생하였습니다");
	 * 
	 * return result.toString(); }
	 * 
	 * 
	 * // TOP5
	 * =============================================================================
	 * ========
	 * 
	 */

	  
	 

} // end class
