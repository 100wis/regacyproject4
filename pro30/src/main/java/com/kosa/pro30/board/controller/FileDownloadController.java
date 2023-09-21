package com.kosa.pro30.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosa.pro30.board.domain.AttacheFileDTO;
import com.kosa.pro30.board.service.AttacheFileService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class FileDownloadController {
	//나는 풀경로라 필요없음
	//private static String CURR_IMAGE_REPO_PATH = "C:\\file_repo";
	
	@Autowired
	AttacheFileService attacheFileService;
	

	@RequestMapping("/attacheFile/download.do")
	protected void download(@RequestParam("file No") String fileNo,
			                 HttpServletResponse response) throws Exception {
		
		System.out.println("실행되는지...");
		
		OutputStream out = response.getOutputStream();
		AttacheFileDTO attacheFile = attacheFileService.getAttacheFile(fileNo); 
		if (attacheFile != null) {
			String filePath = attacheFile.getFileNameReal();  
			File image = new File(filePath);
			FileInputStream in = new FileInputStream(image);
			
			byte[] buffer = new byte[1024 * 8];
			while (true) {
				int count = in.read(buffer); 
				if (count == -1) 
					break;
				out.write(buffer, 0, count);
			}
			in.close();
			
		}
		out.close();
	}
	
}
