package org.zerock.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.BoardAttachVO;
import org.zerock.mapper.BoardAttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {
	
	
	@Setter(onMethod_ = {@Autowired})
	private BoardAttachMapper attachMapper;
	// 1분마다 싹다 * 이면 1초마다
	
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
		
	}
	
	//새벽2시에 돌아감
	@Scheduled(cron="0 0 2 * * *")
	public void checkFiles() throws Exception{
		log.warn("===========================================");
		log.warn("File check Task run.............");
		log.warn(new Date());
		
		//데이터베이스에서 파일 목록가져오고
		List<BoardAttachVO> fileList =  attachMapper.getOldFiles();
		
		//폴더내 파일 확인
		List<Path> fileListPaths = fileList.stream()
				.map(vo -> Paths.get("D:\\upload\\temp",vo.getUploadPath(),vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());
		
		//이미지면 썸네일파일도 가지니까.
		fileList.stream().filter(vo -> vo.isFileType()  == true)
		.map(vo -> Paths.get("D:\\upload\\temp",vo.getUploadPath(),"s_"+vo.getUuid() + "_"+vo.getFileName()))
		.forEach(p -> fileListPaths.add(p));
		
		log.warn("===========================================");
		
		fileListPaths.forEach(p -> log.warn(p));
		
		// 어제 디렉토리 파일
		File targetDir = Paths.get("D:\\upload\\temp", getFolderYesterDay())
				.toFile();
		
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		log.warn("====================================================");
		
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
		
	}
	

}
