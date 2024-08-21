package com.dsa.web5.service;

import com.dsa.web5.dto.MemberDTO;

public interface MemberService {

	boolean idCheck(String searchId);

	void join(MemberDTO member);

	MemberDTO select(String username);

	void updateData(MemberDTO member);

}
