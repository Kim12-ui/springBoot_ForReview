package net.datasa.test.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.datasa.test.domain.entity.MemberEntity;

/**
 * 판매글 DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private Integer boardNum;			// 게시글 일련번호
	private String memberId;			// 작성자 아이디
	private String memberName;			// 작성자 이름
	private String category;			// 카테고리
	private String title;				// 글 제목
	private String contents;			// 글 내용
	private Integer price;				// 제품 가격
	private String soldout;				// 판매 여부
	private String buyerId;				// 구매자 아이디
	private String buyerName;			// 구매자 이름
	private LocalDateTime inputDate;	// 작성시간
}
