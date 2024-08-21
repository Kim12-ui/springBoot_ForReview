package com.dsa.web5.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsa.web5.dto.MemberDTO;
import com.dsa.web5.entity.MemberEntity;
import com.dsa.web5.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService {

	private final MemberRepository mr;
	
	// 암호화 (WebSecurityConfig에 있는 객체)
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * 가입시 아이디 중복 확인 처리
	 * @param searchId
	 * @return 해당 아이디로 가입 가능 여부 true / false
	 */
	@Override
	public boolean idCheck(String searchId) {
		return !mr.existsById(searchId);
	}

	/**
	 * 회원가입 처리
	 * @param MemberDTO 가입 데이터
	 */
	@Override
	public void join(MemberDTO member) {
		MemberEntity m = MemberEntity.builder()
				.memberId(member.getMemberId())
				// 444 -> $2a$10$GAhGwIkeZAFF.uBJ2X4XK.ZNJ.r39ewPNJlHlsOoN1xMEFCg4RVrO
				// (단방향)해싱함수로 암호화
				.memberPassword(
					passwordEncoder.encode(member.getMemberPassword())
						)
				.memberName(member.getMemberName())
				.phone(member.getPhone())
				.email(member.getEmail())
				.address(member.getAddress())
				.build();
		mr.save(m);
	}

	/**
	 * 회원정보 조회
	 * @return memberDTO
	 */
	@Override
	public MemberDTO select(String username) {
		MemberEntity entity = mr.findById(username)
				.orElseThrow(() -> new EntityNotFoundException(
							username + " : 아이디가 없습니다."
						));
		
		// MemberDTO
		MemberDTO memberDTO = MemberDTO.builder()
							.memberId(entity.getMemberId())
							.memberName(entity.getMemberName())
							.phone(entity.getPhone())
							.address(entity.getAddress())
							.email(entity.getEmail())
							.build();
				
		return memberDTO;
	}

	/**
	 * 개인정보 수정
	 * @param MemberDTO
	 */
	@Override
	public void updateData(MemberDTO member) {
		// DB정보를 조회
		MemberEntity entity = mr.findById(member.getMemberId())
				.orElseThrow(() -> new EntityNotFoundException(
						member.getMemberId() + " : 아이디가 없습니다."));
		
		// MemberDTO의 수정할 정보를 entity에 세팅
		if(!member.getMemberPassword().isEmpty()) {
			entity.setMemberPassword(
					passwordEncoder.encode(member.getMemberPassword())
					);
		}
		entity.setMemberName(member.getMemberName());
		entity.setAddress(member.getAddress());
		entity.setEmail(member.getEmail());
		entity.setPhone(member.getPhone());
		
		// entity 저장
		mr.save(entity);
	}
	
}
