package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	
	private BoardService service;
	
	@GetMapping("/list")
	public void list(Model model,Criteria cri) {
		log.info("list--------------: "+ cri);
		
		
		model.addAttribute("list",service.getList(cri));
		/* model.addAttribute("pageMaker", new PageDTO(cri,123)); 
		 * 임시 페이지 넣어둔거 주석*/
		//list.jsp
		int total = service.getTotal(cri);
		
		log.info("토탈 몇개  :  "+ total);
		
		model.addAttribute("pageMaker",new PageDTO(cri,total));
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("register : "+board);
		
		service.register(board);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	@GetMapping("/register")
	public void  register() {
		
	}
	
	@GetMapping({"/get" ,"/modify"})
	public void get(@RequestParam("bno") Long bno, Model model, 
			@ModelAttribute("cri") Criteria cri) {
		//@ModelAttribute("cri") Criteria cri 파라미터 추가 후 get.jsp
		
		log.info("/get");
		model.addAttribute("board", service.get(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr,
			@ModelAttribute("cri") Criteria cri) {
		// @ModelAttribute cri 파라미터 추가 후 rttr.add pageNum+amount 추가 
		//redirect하니까 따로 다시 보내주기 위해 추가 하는 것임.
		log.info("modify " + board);

		if(service.modify(board)) {
			rttr.addFlashAttribute("result" , "success");
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr,
			@ModelAttribute("cri") Criteria cri) {
		
		log.info("remove ..." +bno+ " ��");
		
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		
		
		return "redirect:/board/list" ;
	}
	


}
