package com.dsa.web4.service;

import java.util.List;

import com.dsa.web4.dto.GuestBookDTO;

public interface GuestService {

	void writeData(GuestBookDTO guest);

	List<GuestBookDTO> selectAllData();

	boolean deleteData(Integer num);

	GuestBookDTO selectData(Integer num);

}
