package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page= request.getParameter("page");
		String kwd = request.getParameter("kwd");
		
		
		int page_get=Integer.parseInt(page);
		List<BoardVo> list = new BoardDao().getList((page_get-1)*5, kwd); 
		
		//페이지 구하기
		int page_count;
		page_count=((page_get-1)/5);
		request.setAttribute("list", list);
		request.setAttribute("page_count", page_count);
		
		//총페이지 구하기
		int count = new BoardDao().getCount(kwd);
		request.setAttribute("count", count);
		
		
		
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");

	}

}
