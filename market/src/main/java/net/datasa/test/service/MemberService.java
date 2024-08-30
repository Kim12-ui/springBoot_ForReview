package net.datasa.test.service;

import net.datasa.test.domain.dto.MemberDTO;

public interface MemberService {

	void join(MemberDTO member);

	boolean idCheck(String id);

}
