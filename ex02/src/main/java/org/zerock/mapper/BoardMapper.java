package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {

//	@Select("select * from tbl_board where bno>0")
	public List<BoardVO> getList();
	//test/java mapper BoardMapperTests
	
	public void insert(BoardVO board);
	//insertó���ϰ� pk���ʿ� ���� ���
	public void insertSelectKey(BoardVO board);
	//pk �� �ʿ䰡 �մ� ���
	
	public BoardVO read(long bno);
	// �� �Խù��� ������ �����ϹǷ� BoardVO
	
	public int delete(long bno);
	//Mybatis������ �⺻������ ������ ���� ���� ������Ʈ �� ���� ������ ����
	//�׷��� ������ ���η� Ȯ���ϱ� ���� void���� int���
	
	public int update(BoardVO board);
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	// resource�� BoardMapper.xml getListWithPaging �߰�
	
	public int getTotalCount(Criteria cri);
	// 실제 게시글 총 개수
	// BoardMapper.xml
}
