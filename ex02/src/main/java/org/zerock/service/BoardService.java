package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;

//마커를 담아둔 통 같은 느낌? 
public interface BoardService {

	//BoardMapper.java는 SQL기준의 행위를 작성
	//BoardService는 
	public void register(BoardVO board);
	
	public BoardVO get(Long bno);
	
	public boolean modify(BoardVO board);
	
	public boolean remove(Long bno);
	
	public List<BoardVO> getList();
}
