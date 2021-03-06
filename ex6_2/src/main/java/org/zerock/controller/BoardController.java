package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.AttachFileDTO;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	
	private BoardService service;
	
	@GetMapping("/list")
	public void list(Model model,Criteria cri) {
		log.info("list--------------: "+ cri);
		
		
		model.addAttribute("list",service.getList(cri));
		/* model.addAttribute("pageMaker", new PageDTO(cri,123)); 
		 * ?????? ????????? ???????????? ??????*/
		//list.jsp
		int total = service.getTotal(cri);
		
		log.info("?????? ??????  :  "+ total);
		
		model.addAttribute("pageMaker",new PageDTO(cri,total));
	}
	
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void  register() {
		
	}
	
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("=============================================");
		
		log.info("register : "+board);
		
		if(board.getAttachList() != null) {
			
			board.getAttachList().forEach(attach -> log.info("?????? ??????"+attach));
		}
		
		
		
		  service.register(board);
		  
		  rttr.addFlashAttribute("result", board.getBno());
		 
		 
		log.info("=============================================");
		return "redirect:/board/list";
	}

	
	@GetMapping({"/get" ,"/modify"})
	public void get(@RequestParam("bno") Long bno, Model model, 
			@ModelAttribute("cri") Criteria cri) {
		//@ModelAttribute("cri") Criteria cri ???????????? ?????? ??? get.jsp
		
		log.info("/get");
		model.addAttribute("board", service.get(bno));
	}
	
	@PreAuthorize("principal.username == #board.writer")
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr,
			@ModelAttribute("cri") Criteria cri) {
		// @ModelAttribute cri ???????????? ?????? ??? rttr.add pageNum+amount ?????? 
		//redirect????????? ?????? ?????? ???????????? ?????? ?????? ?????? ??????.
		log.info("modify " + board);

		if(service.modify(board)) {
			rttr.addFlashAttribute("result" , "success");
		}
		
		/*
		 * rttr.addAttribute("pageNum", cri.getPageNum()); rttr.addAttribute("amount",
		 * cri.getAmount()); rttr.addAttribute("type", cri.getType());
		 * rttr.addAttribute("keyword", cri.getKeyword());
		 */
		
		return "redirect:/board/list" + cri.getListLink();
		//??????????????? ????????? ?????? ??? ????????? ?????? ??? ??? ??????.
	}
	
	@PreAuthorize("pricipal.username == #writer")
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr,
			@ModelAttribute("cri") Criteria cri) {
		
		log.info("remove ..." +bno+ " ??????");
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		
		if(service.remove(bno)) {
			
			//???????????? ??????
			deleteFiles(attachList);
			
			rttr.addFlashAttribute("result","success");
		}
		
		/*
		 * rttr.addAttribute("pageNum",cri.getPageNum());
		 * rttr.addAttribute("amount",cri.getAmount()); rttr.addAttribute("type",
		 * cri.getType()); rttr.addAttribute("keyword", cri.getKeyword());
		 	+cri.getListLink(); ??? ?????? ??????
		 */
		//modify.jsp???
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
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
	 * log.info("ajax??? post ???????????????");
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
	 * log.info("---------------------------------------"); log.info("????????? ?????? ?????? : "
	 * + multipartFile.getOriginalFilename()); log.info("????????? ?????? ????????? :  "
	 * +multipartFile.getSize());
	 * 
	 * String uploadFileName = multipartFile.getOriginalFilename();
	 * 
	 * uploadFileName =
	 * uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
	 * log.info("????????? ??????????????? : " + uploadFileName);
	 * 
	 * //UUID ?????? UUID uuid = UUID.randomUUID();
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
	//?????? uploadAjaxPost??? ??????
	@PostMapping(value= "/uploadAjaxAction", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		log.info("ajax??? post ???????????????");
		
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
			log.info("????????? ?????? ?????? : " + multipartFile.getOriginalFilename());
			log.info("????????? ?????? ????????? :  " +multipartFile.getSize());
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			log.info("????????? ??????????????? : " + uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			
			//UUID ??????
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
			log.info("????????? ??????: "+contentType);
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
		
		log.info("????????? ??????:  " + fileName );
		
		Resource resource = new FileSystemResource ("D:\\upload\\temp\\"+ fileName);
		
		log.info("resource:" +resource);
		if(resource.exists() == false) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("resource:" +resource);
		
		//http://localhost:8080/download?fileName=1.png
		//??????????????? MIME(type/subtype) ???????????? ?????? ????????? application/octet-stream
		//????????? ???????????? ????????? Content-Disposition??? ????????? ??????
		/* return null; */
		
		String resourceName = resource.getFilename();
		
		// remmove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			
			String downloadName = null;
			
			if(userAgent.contains("Trident")) {
				log.info("????????? ???????????????");
				//???????????? ?????? ???????????? ??????
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+", " ");
				
			}else if(userAgent.contains("Edge")) {
				
				log.info("??????????????????");
				
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
				
				log.info("??????  ?????? :  " + downloadName);
				
			}else {
				log.info("?????????");
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
		//IE??? ?????? ?????????????????? HttpServletReuqest??? ????????? ?????? ???????????? ???????????? IE ?????? ??? ????????? ???????????? ????????? ??????
	}
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		
		
		log.info("?????????????????? ????????? ?????? : " +fileName);
		
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
	
	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		
		log.info("getAttachList " + bno);
		
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}//get.jsp
	
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		
		log.info("??????????????? ?????????");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file =  Paths.get("D:\\upload\\temp\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				
				Files.deleteIfExists(file);
				
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumNail = Paths.get("D:\\\\upload\\\\temp\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					
					Files.delete(thumNail);
				}
			} catch (Exception e) {
				log.error("???????????? ????????? ?????? " + e.getMessage());
			}
		});
	}
}

