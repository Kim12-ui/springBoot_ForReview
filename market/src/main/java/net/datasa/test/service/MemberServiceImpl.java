package net.datasa.test.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datasa.test.domain.dto.MemberDTO;
import net.datasa.test.domain.entity.MemberEntity;
import net.datasa.test.repository.MemberRepository;

/**
 * 회원정보 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	// WebSecurityConfig에서 생성한 암호화 인코더
    private final BCryptPasswordEncoder passwordEncoder;
    // MemberRepository
	private final MemberRepository memberRepository;
	
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
				.build();
		memberRepository.save(m);
	}

	@Override
	public boolean idCheck(String id) {
		return !memberRepository.existsById(id);
	}
	
}
