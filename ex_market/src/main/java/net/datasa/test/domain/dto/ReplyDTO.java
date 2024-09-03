package net.datasa.test.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 리플 DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

    //리플번호
    Integer replyNum;

    //본문글 번호
    Integer boardNum;

    //작성자 ID
    String memberId;

    //리플 내용
    String replyText;

}
