package net.datasa.test.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.domain.dto.ReplyDTO;
import net.datasa.test.domain.entity.BoardEntity;
import net.datasa.test.domain.entity.MemberEntity;
import net.datasa.test.domain.entity.ReplyEntity;
import net.datasa.test.security.AuthenticatedUser;
import net.datasa.test.service.BoardService;

/**
 * 거래 게시판 관련 콘트롤러
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("board")
public class BoardController {

	private final BoardService boardService;
	
	/**
	 * 거래 게시판으로 이동
	 * @return list.html
	 */
   @GetMapping("list")
   public String list(Model model) {
	   List<BoardDTO> boardList = boardService.selectAllList();
	   model.addAttribute("boardList",boardList);
	   return "board/list";
   }
   
   /**
    * 글쓰기 버튼 누를시
    * @return writeForm.html
    */
   @GetMapping("write")
   public String write() {
	   return "board/writeForm";
   }
   
   /**
    * 글 작성 후 내용 저장
    */
   @PostMapping("write")
   public String write(
		   @ModelAttribute BoardDTO boardDTO,
		   @AuthenticationPrincipal AuthenticatedUser user
		   ) {
	   boardDTO.setMemberId(user.getUsername());
	   boardDTO.setMemberName(user.getName());
	   log.debug("입력한 내용 : {}",boardDTO);
	   
	   try {
			boardService.write(boardDTO);
			return "redirect:/board/list";
		} catch(Exception e) {
			e.printStackTrace();
			return "board/writeForm";
		}
   }
   
   /**
    * 게시글 상세보기
    */
   @GetMapping("read")
   public String read(
		   Model model,
		   @RequestParam(name = "boardNum", defaultValue = "0") int boardNum
		   ) {
	   try {
			log.debug("boardNum: {}", boardNum);
			BoardDTO boardDTO = boardService.getBoard(boardNum);
			model.addAttribute("board",boardDTO);
			return "board/read";
		} catch (Exception e) {
			return "redirect:listAll";
		}
   }
   
   /**
	 * 게시글 삭제 처리
	 * @param boardNum	삭제할 글번호
	 * @param user		로그인한 사용자 정보
	 * @return list.html
	 */
	@GetMapping("delete")
	public String delete(
			@RequestParam(name = "boardNum") int boardNum,
			@AuthenticationPrincipal AuthenticatedUser user
			) {
		try {
			boardService.delete(boardNum, user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:list";
	}
	
	@GetMapping("buy")
	public String buy(
			@RequestParam(name = "boardNum") int boardNum,
			@AuthenticationPrincipal AuthenticatedUser user
			) {
		try {
			boardService.buy(boardNum, user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:read?boardNum="+boardNum;
	}
	
	
}
