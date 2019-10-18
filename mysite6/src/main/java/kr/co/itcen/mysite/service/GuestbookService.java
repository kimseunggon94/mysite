package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.GuestbookDao;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookDao guestbookdao;

	public List<GuestbookVo> list() {
		return guestbookdao.getList();
	}

	public void delete(GuestbookVo vo) {
		guestbookdao.delete(vo);
		
	}

	public void insert(GuestbookVo vo) {
		guestbookdao.insert(vo);
	}
}
