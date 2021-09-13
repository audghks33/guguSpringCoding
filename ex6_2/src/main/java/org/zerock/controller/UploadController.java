package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

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

		for (MultipartFile multipartFile : uploadFile) {

			log.info("-------------------------------------------");
			log.info("upload file name:  " + multipartFile.getOriginalFilename());
			log.info("upload file size:  " + multipartFile.getSize());

			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			} // end catch

		} // end for

	}

	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}

	/*
	 * @PostMapping("/uploadAjaxAction") public void uploadAjaxPost(MultipartFile[]
	 * uploadFile) {
	 * 
	 * log.info("ajax로 post 업데이트중");
	 * 
	 * String uploadFolder = "D:\\upload\\temp";
	 * 
	 * File uploadPath = new File(uploadFolder, getFolder());
	 * log.info("upload path:  "+ uploadPath);
	 * 
	 * if(uploadPath.exists() == false) { uploadPath.mkdirs(); }
	 * 
	 * 
	 * for(MultipartFile multipartFile : uploadFile) {
	 * log.info("---------------------------------------"); log.info("올리는 파일 이름 : "
	 * + multipartFile.getOriginalFilename()); log.info("올리는 파일 사이즈 :  "
	 * +multipartFile.getSize());
	 * 
	 * String uploadFileName = multipartFile.getOriginalFilename();
	 * 
	 * uploadFileName =
	 * uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
	 * log.info("깔끔한 파일이름은 : " + uploadFileName);
	 * 
	 * //UUID 추가 UUID uuid = UUID.randomUUID();
	 * 
	 * uploadFileName = uuid.toString() + "_" + uploadFileName;
	 * 
	 * File saveFile = new File(uploadFolder, uploadFileName);
	 * 
	 * try { File saveFile = new File(uploadPath, uploadFileName);
	 * multipartFile.transferTo(saveFile); if(checkImageType(saveFile)) {
	 * FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath,"S_" +
	 * uploadFileName));
	 * 
	 * Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail,
	 * 100,100);
	 * 
	 * thumbnail.close(); } } catch (Exception e) { log.error(e.getMessage()); } } }
	 */
	//위의 uploadAjaxPost를 수정
	@PostMapping(value= "/uploadAjaxAction", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		log.info("ajax로 post 업데이트중");
		
		List<AttachFileDTO> list = new ArrayList<>();
		
		String uploadFolder = "D:\\upload\\temp";
		
		String uploadFolderPath = getFolder();
		
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path:  "+ uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("---------------------------------------");
			log.info("올리는 파일 이름 : " + multipartFile.getOriginalFilename());
			log.info("올리는 파일 사이즈 :  " +multipartFile.getSize());
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			log.info("깔끔한 파일이름은 : " + uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			
			//UUID 추가
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			/* File saveFile = new File(uploadFolder, uploadFileName); */
			 
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				log.info(attachDTO);
				
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail 
					= new FileOutputStream(new File(uploadPath,"s_" + uploadFileName));
					
					log.info(thumbnail);
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100,100);
					
					thumbnail.close();
				}
				
				list.add(attachDTO);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("_", File.separator);
	}

	private boolean checkImageType(File file) {

		try {
			String contentType = Files.probeContentType(file.toPath());
			log.info("콘텐트 타입: "+contentType);
			return contentType.startsWith("image");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		
		log.info("fileName:  "+ fileName);
		
		File file = new File("D:\\upload\\temp\\" + fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),
					header,HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
			
		//test 
		// http://localhost:8080/display?fileName=2021/08/30/gg.png
	}
	
	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent ,String fileName) {
		
		log.info("다운한 파일:  " + fileName );
		
		Resource resource = new FileSystemResource ("D:\\upload\\temp\\"+ fileName);
		
		log.info("resource:" +resource);
		if(resource.exists() == false) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("resource:" +resource);
		
		//http://localhost:8080/download?fileName=1.png
		//다운로드를 MIME(type/subtype) 타입으로 지정 해주는 application/octet-stream
		//다운시 저장되는 이름은 Content-Disposition을 이용해 지정
		/* return null; */
		
		String resourceName = resource.getFilename();
		
		// remmove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			
			String downloadName = null;
			
			if(userAgent.contains("Trident")) {
				log.info("인터넷 익스플로러");
				//브라우저 엔진 이름으로 확인
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+", " ");
				
			}else if(userAgent.contains("Edge")) {
				
				log.info("엣지브라우저");
				
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
				
				log.info("엣지  이름 :  " + downloadName);
				
			}else {
				log.info("크롬임");
				downloadName =  new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
			}
			
			log.info("downloadName : "+downloadName);
			
			/*
			 * headers.add("Content-Disposition", "attachment; fileName=" + new
			 * String(resourceName.getBytes("UTF-8"),"ISO-8859-1"));
			 */
			
			headers.add("Content-Disposition", "attachment; fileName=" + downloadName);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		//IE를 같이 서비스한다면 HttpServletReuqest에 포함된 헤더 정보들을 이용해서 IE 확인 후 다르게 처리하는 방식을 사용
	}
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		
		log.info("업로드파일중 삭제된 파일 : " +fileName);
		
		File file;
		
		try {
			file = new File("D:\\upload\\temp\\"+URLDecoder.decode(fileName,"UTF-8"));
			
			file.delete();
			
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				
				log.info("largeFileName : " +largeFileName);
				
				file = new File(largeFileName);
				
				file.delete();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
