package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {

//	@Select("select * from tbl_board where bno>0")
	public List<BoardVO> getList();
	//test/java mapper BoardMapperTests
	
	public void insert(BoardVO board);
	//insert처리하고 pk알필요 없는 경우
	public void insertSelectKey(BoardVO board);
	//pk 알 필요가 잇는 경우
	
	public BoardVO read(long bno);
	// 고른 게시물의 내용을 봐야하므로 BoardVO
	
	public int delete(long bno);
	//Mybatis에서는 기본적으로 쿼리가 돌고 나면 업데이트 한 행의 개수를 리턴
	//그래서 리턴의 여부로 확인하기 위해 void말고 int사용
	
	public int update(BoardVO board);
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	// resource에 BoardMapper.xml getListWithPaging 추가
}
