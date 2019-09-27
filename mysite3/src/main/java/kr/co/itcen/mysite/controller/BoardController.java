package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(Model model ) {
		int page =1;
		String kwd = "";
		List<BoardVo> list = boardService.getlist(page, kwd);
		int count = boardService.getCount(kwd);
		model.addAttribute("list", list);
		model.addAttribute("page_count", (page-1)/5);
		model.addAttribute("count", count);
		model.addAttribute("page", page	);
		model.addAttribute("kwd", kwd);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping("/list")
	public String list(Model model, @RequestParam("page") int page, @RequestParam("kwd") String kwd) {
		
		List<BoardVo> list =   boardService.getlist(page, kwd);
		int count = boardService.getCount(kwd);
		model.addAttribute("list", list);
		model.addAttribute("page_count", (page-1)/5);
		model.addAttribute("count", count);
		return "board/list";
	}
	
	@RequestMapping("/view")
	public String view(Model model, @RequestParam("page") int page, @RequestParam("kwd") String kwd, @RequestParam("no") Long no) {
		
		BoardVo vo = boardService.get(no);
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@RequestMapping(value ="/modify/{no}", method=RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, Model model ) {
		BoardVo vo = boardService.get(no);
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo  vo, @RequestParam("page") int page, @RequestParam("kwd") String kwd) {
		boardService.modify(vo);
		return "redirect:/board/view?page="+page+"&kwd="+kwd+"&no="+vo.getNo();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(@ModelAttribute BoardVo  vo, @RequestParam("page") int page, @RequestParam("kwd") String kwd) {
		boardService.delete(vo);
		return "redirect:/board/list?page="+page+"&kwd="+kwd;
	}
	
	@RequestMapping(value ="/write", method=RequestMethod.GET)
	public String write(@ModelAttribute BoardVo  vo, @RequestParam("page") int page, @RequestParam("kwd") String kwd ) {
		boardService.insert(vo);
		
		return "board/list";
	}
	
}
