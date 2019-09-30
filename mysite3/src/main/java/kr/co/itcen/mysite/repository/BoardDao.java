package kr.co.itcen.mysite.repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> getList(int page, String kwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page-1)*5);
		List<BoardVo> result = null;
		
		if(kwd!=null && kwd.length() != 0) {
			map.put("kwd", "%"+kwd+"%");
			result = sqlSession.selectList("board.getListkwd", map);
		}else {
			result = sqlSession.selectList("board.getList", map);
		}
		
		return result;
	}
	
	public int getCount(String kwd) {
		int count =0;
		if(kwd!=null && kwd.length() != 0) {
			kwd = "%"+kwd+"%";
			count = sqlSession.selectOne("board.getCountkwd",kwd);
		}else {
			count = sqlSession.selectOne("board.getCount");
		}
		
		return count;
	}

	public BoardVo get(Long no) {
		return sqlSession.selectOne("board.get", no);
		
	}

	public boolean modify(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		return count ==1;
		
	}

	public boolean delete(BoardVo vo) {
		int count = sqlSession.update("board.delete", vo);
		return count ==1;
		
	}

	public boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count ==1;
		
	}

	public boolean reply(BoardVo vo) {
		sqlSession.update("board.replyupdate", vo);
		int count = sqlSession.insert("board.reply", vo);
		return count ==1;
		
	}

	public void hit(Long no) {
		sqlSession.update("board.hit", no);
		
	}
		


}