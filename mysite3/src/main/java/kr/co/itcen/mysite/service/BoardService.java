package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	
	public List<BoardVo> getlist(int page, String kwd) {
		return boardDao.getList(page, kwd);
	}

	public int getCount(String kwd) {
		return boardDao.getCount(kwd);
	}

	public BoardVo get(Long no) {
		return boardDao.get(no);
	}

	public void modify(BoardVo vo) {
		boardDao.modify(vo);
	}

	public void delete(BoardVo vo) {
		boardDao.delete(vo);
		
	}

	public void insert(BoardVo vo) {
		boardDao.insert(vo);
		
	}

	public void reply(BoardVo vo) {
		boardDao.reply(vo);
		
	}

}
