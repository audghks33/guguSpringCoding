package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

//impl ���� �� �ش� �ڿ��� root-context.xml �� �߰� ���� BoardService Tests
@Service
@AllArgsConstructor// Ŭ������ �����ϴ� ��� �ʵ忡 ���� �����ڸ� �ڵ����� ����
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
		
		log.info("get��..........."  +bno);
		
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		
		log.info("���� ��..........." + board);
		
		return mapper.update(board) == 1;
	}

	@Override
	public boolean remove(Long bno) {
		
		log.info("������ .............." + bno);
		
		return mapper.delete(bno) == 1;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		
		log.info("get����Ʈ..................+ cri");
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
	
		log.info("총 몇개니 " + cri);
		return mapper.getTotalCount(cri);
		//board controller list
	}
	
}
