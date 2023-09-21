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
		model.addAttribute("result", noticeService.getNoticeList(notice));

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
	
// 공지사항 등록
		@ResponseBody
		@RequestMapping("/notice/insert.do")
		public Map<String, Object> insert(@RequestBody NoticeDTO notice) throws Exception {
			System.out.println("공지사항 등록 컨트롤러");
			
		return noticeService.insert(notice);
		}
		

// 공지사항 수정
		@ResponseBody
		@RequestMapping("/notice/update.do")
		public Map<String, Object> update(@RequestBody NoticeDTO notice) throws Exception {
			System.out.println("공지사항 수정 컨트롤러");
			
		return noticeService.update(notice);
		}
		
// 공지사항 한건 삭제
		@ResponseBody
		@RequestMapping("/notice/deleteOne.do")
		public Map<String, Object> deleteOne(@RequestBody NoticeDTO notice) throws Exception {
			System.out.println("공지사항 삭제 컨트롤러");
			
		return noticeService.deleteOne(notice);
		}
	
// 공지사항 체크박스 여러건 삭제
		@ResponseBody
		@RequestMapping("/notice/deleteCheck.do")
		public Map<String, Object> deleteCheck(@RequestBody NoticeDTO notice) throws Exception {
			System.out.println("공지사항 삭제 컨트롤러");
			System.out.println(notice.getIds().toString());
			
		return noticeService.deleteCheck(notice);
		}	

// 공지사항 체크박스 여러건 고정
		@ResponseBody
		@RequestMapping("/notice/fixes.do")
		public Map<String, Object> fixes(@RequestBody NoticeDTO notice) throws Exception {
			System.out.println("공지사항 고정컨트롤러");
			System.out.println(notice.getIds().toString());
			
		return noticeService.fixes(notice);
		}		
		
// 공지사항 체크박스 여러건 고정해제
		@ResponseBody
		@RequestMapping("/notice/none_fixes.do")
		public Map<String, Object> none_fixes(@RequestBody NoticeDTO notice) throws Exception {
			System.out.println("공지사항 고정해제 컨트롤러");
			System.out.println(notice.getIds().toString());
			
		return noticeService.none_fixes(notice);
		}		


	
	
	
	
	


	  
	 

} // end class
