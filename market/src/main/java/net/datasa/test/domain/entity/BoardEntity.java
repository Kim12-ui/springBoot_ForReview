package net.datasa.test.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 판매글 Entity
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "market_board")
public class BoardEntity {
	// 게시판 일련번호 (board_num)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_num")
	private Integer boardNum;
	
	 //작성자 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member;
	
    // 카테고리
	@Column(name = "category", nullable=false, columnDefinition="varchar(50) check(category in ('컴퓨터','자동차','카메라'))")
	private String category; 
	
	// 글 제목
	@Column(name="title", nullable=false, length=200)
	private String title;
	
	// 글 내용
	@Column(name="contents", nullable=false, length=2000)
	private String contents;
	
	// 글 가격
	@Column(name="price", columnDefinition="int default 0")
	private Integer price;
	
	// 0이면 판매중(false), 1이면 판매완료(true)
	@Column(name="soldout",columnDefinition="tinyint(1) default 0 check(soldout in (0, 1))")
	private Boolean soldout;
	
	//구매자 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", referencedColumnName = "member_id")
    private MemberEntity buyer;
    
    //작성일
    @CreatedDate
    @Column(name = "input_date", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime inputDate;
    
    @PrePersist
    public void prePersist() {
       if(price == null) this.price = 0;
       if(soldout == null) this.soldout = false;
    }
}
