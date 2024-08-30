package net.datasa.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.test.domain.dto.MemberDTO;
import net.datasa.test.service.MemberService;

/**
 * 회원 관련 콘트롤러
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("member")
public class MemberController {

	private final MemberService memberservice;
	
	/**
	 * 회원가입 창으로 이동
	 * @return joinForm.html
	 */
   @GetMapping("join")
   public String join() {
	   return "member/joinForm";
   }
   
   /**
	 * 회원가입페이지에서 "ID중복확인" 버튼을 클릭하면 새창으로 보여줄 검색 페이지 이동
	 * @return idCheck.html
	 */
	@GetMapping("idCheck")
	public String idCheck() {
		return "member/idCheck";
	}
   
   /**
    * 가입 버튼 클릭
    * @param member 회원가입 정보
    * @return home.html
    */
   @PostMapping("join")
   public String join(@ModelAttribute MemberDTO member) {
	   log.debug("전달된 회원가입정보 : {}",member);
	   memberservice.join(member);
	   return "redirect:/";
   }
   
   /**
	 * ID 중복확인 페이지에서 검색 요청했을때 처리
	 * @param searchId 검색할 아이디
	 * @return idCheck.html
	 */
	@PostMapping("idCheck")
	public String idCheck(
			@RequestParam("searchId") String searchId,
			Model model
			) {
		
		boolean result = memberservice.idCheck(searchId);
		
		model.addAttribute("result",result);
		model.addAttribute("searchId",searchId);
		
		return "member/idCheck";
	}
	
	/**
	 * 로그인 페이지로 이동
	 * @return loginForm.html
	 */
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
}
