package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

//impl 생성 후 해당 자원을 root-context.xml 에 추가 이후 BoardService Tests
@Service
@AllArgsConstructor// 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성
@Log4j
public class BoardServiceImpl implements BoardService{

	private BoardMapper mapper;

	@Override
	public void register(BoardVO board) {
		
		log.info("register......." +board);
		
		mapper.insertSelectKey(board);
	}

	@Override
	public BoardVO get(Long bno) {
		
		log.info("get임..........."  +bno);
		
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		
		log.info("변경 댐..........." + board);
		
		return mapper.update(board) == 1;
	}

	@Override
	public boolean remove(Long bno) {
		
		log.info("삭제됨 .............." + bno);
		
		return mapper.delete(bno) == 1;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		
		log.info("get리스트..................+ cri");
		return mapper.getListWithPaging(cri);
	}
	
	
}
