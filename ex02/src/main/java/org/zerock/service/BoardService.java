package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;

//��Ŀ�� ��Ƶ� �� ���� ����? 
public interface BoardService {

	//BoardMapper.java�� SQL������ ������ �ۼ�
	//BoardService�� 
	public void register(BoardVO board);
	
	public BoardVO get(Long bno);
	
	public boolean modify(BoardVO board);
	
	public boolean remove(Long bno);
	
	public List<BoardVO> getList();
}
