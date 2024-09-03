package net.datasa.test.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.domain.dto.MemberDTO;
import net.datasa.test.domain.dto.ReplyDTO;
import net.datasa.test.domain.entity.BoardEntity;
import net.datasa.test.domain.entity.MemberEntity;
import net.datasa.test.domain.entity.ReplyEntity;
import net.datasa.test.repository.BoardRepository;
import net.datasa.test.repository.MemberRepository;
import net.datasa.test.repository.ReplyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {

    //회원 관련 Repository
    private final MemberRepository memberRepository;
    //게시판 관련 Repository
    private final BoardRepository boardRepository;
    //리플 관련 Repository
    private final ReplyRepository replyRepository;

    /**
     * 글 저장
     * @param boardDTO 게시글 정보
     */
    public void write(BoardDTO boardDTO) {

        MemberEntity memberEntity = memberRepository.findById(boardDTO.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("회원 정보가 없습니다."));

        //전달된 회원정보를 테이블에 저장한다.
        BoardEntity boardEntity = BoardEntity.builder()
                .member(memberEntity)
                .category(boardDTO.getCategory())
                .title(boardDTO.getTitle())
                .contents(boardDTO.getContents())
                .price(boardDTO.getPrice())
                .soldout(false)
                .build();

        boardRepository.save(boardEntity);
    }

    /**
     * 게시글 목록 조회
     * @param category  상품 카테고리
     * @param keyword   검색어
     * @return          게시글 목록
     */
    public List<BoardDTO> getList(String category, String keyword) {
        List<BoardEntity> entityList = boardRepository.selectBoardList(category, keyword);
        List<BoardDTO> dtoList = new ArrayList<>();

        for (BoardEntity entity : entityList) {
            BoardDTO dto = BoardDTO.builder()
                    .boardNum(entity.getBoardNum())
                    .memberId(entity.getMember().getMemberId())
                    .category(entity.getCategory())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .price(entity.getPrice())
                    .soldout(entity.getSoldout())
                    .buyerId(entity.getBuyer() == null ? "" : entity.getBuyer().getMemberId())
                    .inputDate(entity.getInputDate())
                    .build();

            dtoList.add(dto);
        }

        return dtoList;
    }

    /**
     * 글 1개 조회
     * @param boardNum  조회할 글번호
     * @return          글정보
     */
    public BoardDTO getBoard(int boardNum) {
        BoardEntity boardEntity = boardRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("글이 없습니다."));

        BoardDTO boardDTO = BoardDTO.builder()
                .boardNum(boardEntity.getBoardNum())
                .memberId(boardEntity.getMember().getMemberId())
                .category(boardEntity.getCategory())
                .title(boardEntity.getTitle())
                .contents(boardEntity.getContents())
                .price(boardEntity.getPrice())
                .soldout(boardEntity.getSoldout())
                .buyerId(boardEntity.getBuyer() == null ? "" : boardEntity.getBuyer().getMemberId())
                .inputDate(boardEntity.getInputDate())
                .build();

        return boardDTO;
    }

    /**
     * 판매글 삭제
     * @param boardNum 삭제할 글 번호
     * @param username  사용자 아이디
     */
    public void delete(int boardNum, String username) {
        MemberEntity memberEntity = memberRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보가 없습니다."));

        BoardEntity boardEntity = boardRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("글이 없습니다."));

        if (!username.equals(memberEntity.getMemberId())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        boardRepository.delete(boardEntity);
    }


    /**
     * 구매
     * @param boardNum 구매할 판매글 번호
     * @param username 구매자 아이디
     */
    public void buy(int boardNum, String username) {
        MemberEntity memberEntity = memberRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("구매자 아이디가 없습니다."));

        BoardEntity boardEntity = boardRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("판매글이 없습니다."));

        boardEntity.setBuyer(memberEntity);
        boardEntity.setSoldout(true);
    }

    /**
     * 리플 저장
     * @param replyDTO 저장할 리플 정보
     */
    public void replyWrite(ReplyDTO replyDTO) {
        MemberEntity memberEntity = memberRepository.findById(replyDTO.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보가 없습니다."));

        BoardEntity boardEntity = boardRepository.findById(replyDTO.getBoardNum())
                .orElseThrow(() -> new EntityNotFoundException("게시글 정보가 없습니다."));

        ReplyEntity replyEntity = ReplyEntity.builder()
                .board(boardEntity)
                .member(memberEntity)
                .replyText(replyDTO.getReplyText())
                .build();

        replyRepository.save(replyEntity);
    }

    /**
     * 리플 목록
     * @param boardNum 리플 조회할 본문 글번호
     * @return 리플 목록
     */
    public List<ReplyDTO> getReplyList(int boardNum) {
        Sort sort = Sort.by(Sort.Direction.ASC, "replyNum");
        List<ReplyEntity> replyEntityList = replyRepository.findByBoard_BoardNum(boardNum, sort);
        List<ReplyDTO> replyDTOList = new ArrayList<ReplyDTO>();

        for (ReplyEntity entity : replyEntityList) {
            ReplyDTO dto = ReplyDTO.builder()
                    .replyNum(entity.getReplyNum())
                    .boardNum(entity.getBoard().getBoardNum())
                    .memberId(entity.getMember().getMemberId())
                    .replyText(entity.getReplyText())
                    .build();
            replyDTOList.add(dto);
        }
        return replyDTOList;
    }

    /**
     * 리플 삭제
     * @param replyDTO 삭제할 리플 정보
     */
    public void replyDelete(ReplyDTO replyDTO) {
        ReplyEntity replyEntity = replyRepository.findById(replyDTO.getReplyNum())
                .orElseThrow(() -> new EntityNotFoundException("리플 정보가 없습니다."));

        if (!replyDTO.getMemberId().equals(replyEntity.getMember().getMemberId())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        replyRepository.delete(replyEntity);
    }

}
