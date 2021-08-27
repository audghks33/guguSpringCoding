package org.zerock.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {

	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("upload form");
	}
	
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "D:\\upload\\temp";
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("-------------------------------------------");
			log.info("upload file name:  " + multipartFile.getOriginalFilename());
			log.info("upload file size:  " + multipartFile.getSize());
	
			File saveFile= new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}//end catch
		
		}// end for
		
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		
		log.info("ajax로 post 업데이트중");
		
		String uploadFolder = "D:\\upload\\temp";
				
				for(MultipartFile multipartFile : uploadFile) {
					log.info("---------------------------------------");
					log.info("올리는 파일 이름 : " + multipartFile.getOriginalFilename());
					log.info("올리는 파일 사이즈 :  " +multipartFile.getSize());
					
					String uploadFileName = multipartFile.getOriginalFilename();
					
					uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
					log.info("깔끔한 파일이름은 : " + uploadFileName);
					
					File saveFile = new File(uploadFolder, uploadFileName);
					
					try {
						multipartFile.transferTo(saveFile);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
	}
	
}
