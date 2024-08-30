package net.datasa.test.domain.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.datasa.test.domain.entity.BoardEntity;
import net.datasa.test.domain.entity.MemberEntity;

/**
 * 리플 DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
	private Integer replyNum;
	private Integer boardNum;
	private String memberId;			// 작성자 아이디
	private String memberName;			// 작성자 이름
	private String replyText;	
}
