package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session==null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		
		UserVo vo = (UserVo)session.getAttribute("authUser");
		
		if(password.equals("")) {
			new UserDao().update(vo.getNo(), name, gender );
		}else {
			new UserDao().update(vo.getNo(), name, password, gender );
		}
		
		UserVo update_authUser = new UserVo();
		update_authUser.setNo(vo.getNo());
		update_authUser.setName(name);
		
		session.setAttribute("authUser", update_authUser);
		
		WebUtils.redirect(request, response, request.getContextPath());
	}

}
