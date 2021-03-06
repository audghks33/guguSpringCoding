package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService{
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;
	
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		
		log.info("등록 완료  ...." + vo);
		
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		 
		log.info("리스트 가져와  ...." + rno);
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		 
		log.info("변경 되었다.  ...." + vo);
		
		return mapper.update(vo);
	}
	
	@Transactional
	@Override
	public int remove(Long rno) {
		 
		log.info("삭제 되었다..  ...." + rno);
		
		ReplyVO vo = mapper.read(rno);
		
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		 
		log.info("게시판 댓글 인데요?  " + bno );
		
		return mapper.getListWithPaging(cri, bno);
	}
	// controller 설계

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		
		
		return new ReplyPageDTO(
				mapper.getCountByBno(bno),
				mapper.getListWithPaging(cri, bno));
	}
	

	
	
}
