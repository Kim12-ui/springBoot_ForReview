package com.dsa.web3.service;

import java.util.List;

import com.dsa.web3.dto.MemberDTO;

/*
 * 회원 정보 관련 비즈니스 로직을 처리하는 메서드를 정의
 * */
public interface MemberService {

	/*
	 * JPA 기본 쿼리 연습
	 * */
	void insertData();

	MemberDTO selectData(String string);

	void updateData(MemberDTO m);

	boolean deleteData(String string);

	List<MemberDTO> selectAllData();

	void joinData(MemberDTO member);

}
