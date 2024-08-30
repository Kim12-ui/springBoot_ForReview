package net.datasa.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.domain.dto.ReplyDTO;
import net.datasa.test.domain.entity.BoardEntity;
import net.datasa.test.domain.entity.MemberEntity;
import net.datasa.test.domain.entity.ReplyEntity;
import net.datasa.test.repository.BoardRepository;
import net.datasa.test.repository.MemberRepository;
import net.datasa.test.repository.ReplyRepository;

/**
 * 게시판 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;

	/**
	 * DB에서 조회한 게시글 정보인 BoardEntity 객체를 BoardDTO 객체로 변환
	 * @param entity 객체
	 * @return dto 객체
	 */
	private BoardDTO convertDTO(BoardEntity entity) {
		return BoardDTO.builder()
			   .boardNum(entity.getBoardNum())
			   .memberId(entity.getMember() != null
			   ? entity.getMember().getMemberId() : null)
			   .memberName(entity.getMember() != null
			   ? entity.getMember().getMemberName() : null)
			   .category(entity.getCategory())
			   .title(entity.getTitle())
			   .contents(entity.getContents())
			   .price(entity.getPrice())
			   .buyerId(entity.getBuyer() != null ?
					   entity.getBuyer().getMemberId() : null)
			   .soldout(entity.getSoldout()==false?"판매중":"판매완료")
			   .inputDate(entity.getInputDate())
			   .build();
	}
	
	/**
	 * 글 작성 후 저장
	 * @param BoardDTO
	 */
	@Override
	public void write(BoardDTO boardDTO) {
		MemberEntity memberEntity = memberRepository.findById(boardDTO.getMemberId())
				.orElseThrow(() -> new EntityNotFoundException("아이디가 없습니다."));
		
		BoardEntity entity = new BoardEntity();
		entity.setMember(memberEntity);
		entity.setCategory(boardDTO.getCategory());
		entity.setTitle(boardDTO.getTitle());
		entity.setContents(boardDTO.getContents());
		entity.setPrice(boardDTO.getPrice());
		
		boardRepository.save(entity);
	}

	/**
	 * DB로부터 게시글 전체 조회
	 * @return 글 목록
	 */
	@Override
	public List<BoardDTO> selectAllList() {		
		// boardNum을 정렬기준으로 데이터를 entity로 가져오기
		// DESC = 내림차순, ASC = 오름차순
		Sort sort = Sort.by(Sort.Direction.DESC, "boardNum");
		List<BoardEntity> entityList = boardRepository.findAll(sort);
		
		// entity 데이터를 dto 데이터로 옮겨담기
		List<BoardDTO> dtoList = new ArrayList<>();
		for (BoardEntity entity : entityList) {
			BoardDTO dto = convertDTO(entity);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/**
	 * 게시물 읽기
	 */
	@Override
	public BoardDTO getBoard(int boardNum) {
		// boardNum에 일치하는 게시글이 있는지 조회
		BoardEntity entity = boardRepository.findById(boardNum)
				.orElseThrow(() -> 
				new EntityNotFoundException("해당 번호의 글이 없습니다."));
				
		// 존재한다면 entity의 정보를 dto에 옮겨담기
		BoardDTO dto = convertDTO(entity);
		return dto;
	}

	/**
	 * 게시물 삭제
	 */
	@Override
	public void delete(int boardNum, String username) {
		BoardEntity boardEntity = boardRepository.findById(boardNum)
				.orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
		
		if (!boardEntity.getMember().getMemberId().equals(username)) {
			throw new RuntimeException("삭제 권한이 없습니다");
		}
		boardRepository.delete(boardEntity);
	}

	/**
	 * 구매 시
	 */
	@Override
	public void buy(int boardNum, String username) {
		MemberEntity memberEntity = memberRepository.findById(username)
				.orElseThrow(() -> new EntityNotFoundException("아이디가 없습니다."));
		BoardEntity boardEntity = boardRepository.findById(boardNum)
				.orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
		
		// soldout: 0->1
		boardEntity.setSoldout(true);
		
		// buyer 추가
		boardEntity.setBuyer(memberEntity);
		}
	
}
