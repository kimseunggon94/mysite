package kr.co.itcen.mysite.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		//model("userVo", vo);	@ModelAttribute가 하는 행동
		
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());			//Map으로 받는것도 가능
			return "user/join";
		}
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@ModelAttribute UserVo vo) {
		return "user/login";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session,Model model, UserVo vo) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		vo = userService.get(authUser.getNo());
		model.addAttribute("email", vo.getEmail());
		model.addAttribute("userVo", vo);
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute @Valid UserVo vo, BindingResult result, HttpSession session, Model model) {
		if(result.hasFieldErrors("name")) {
			model.addAllAttributes(result.getModel());			//Map으로 받는것도 가능
			return "user/update";
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		
		vo.setNo(authUser.getNo());
		userService.update(vo);
		authUser.setName(vo.getName());
		session.setAttribute("authUser", authUser);
		
		return "redirect:/";
	}
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handlerException() {
//		return "error/exception";
//	}
		
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(@ModelAttribute @Valid UserVo vo, BindingResult result, HttpSession session, Model model) {
//		UserVo userVo = userService.getUser(vo);
//		if(userVo==null) {
//			session.setAttribute("logresult", "fail");
//			return "user/login";
//		}
//		if(result.hasFieldErrors("email")) {
//			model.addAllAttributes(result.getModel());		
//			return "user/login";
//		}
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}
}
