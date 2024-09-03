package net.datasa.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.domain.dto.ReplyDTO;
import net.datasa.test.security.AuthenticatedUser;
import net.datasa.test.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 거래 게시판 Ajax 요청 처리 콘트롤러
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("board")
public class BoardRestController {

    //게시판 처리 서비스
    private final BoardService boardService;

    /**
     * 글 목록 조회
     * @param category      판매상품 카테고리
     * @param keyword       검색어
     * @return              게시글 목록
     */
    @PostMapping("getList")
    public ResponseEntity<?> write(
            @RequestParam("category") String category
            , @RequestParam("keyword") String keyword) {

        log.debug("전달된 카테고리 :{}, 검색어 :{}", category, keyword);

        List<BoardDTO> list = boardService.getList(category, keyword);

        return ResponseEntity.ok(list);
    }

    /**
     * 리플 저장
     * @param replyDTO 저장할 리플 정보
     * @param user      로그인한 사용자 정보
     */
    @PostMapping("replyWrite")
    public void replyWrite(ReplyDTO replyDTO
        , @AuthenticationPrincipal AuthenticatedUser user) {

        replyDTO.setMemberId(user.getUsername());
        log.debug("저장할 리플 정보 : {}", replyDTO);

        boardService.replyWrite(replyDTO);
    }

    /**
     * 리플 목록 조회
     * @param boardNum 본문 글번호
     * @return  리플 목록
     */
    @GetMapping("replyList")
    public ResponseEntity<?> getReplyList(@RequestParam("boardNum") int boardNum) {
        log.debug("리플 조회할 글번호 : {}", boardNum);
        List<ReplyDTO> replyList = boardService.getReplyList(boardNum);
        return ResponseEntity.ok(replyList);
    }

    /**
     * 리플 삭제
     * @param replyDTO 삭제할 리플 정보
     * @param user  로그인한 사용자정보
     */
    @PostMapping("replyDelete")
    public void replyDelete(ReplyDTO replyDTO
        , @AuthenticationPrincipal AuthenticatedUser user) {

        replyDTO.setMemberId(user.getUsername());
        log.debug("삭제할 리플 정보 : {}", replyDTO);

        boardService.replyDelete(replyDTO);
    }
}
