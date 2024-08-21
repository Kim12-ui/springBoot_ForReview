package com.dsa.web4.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dsa.web4.dto.GuestBookDTO;
import com.dsa.web4.entity.GuestBookEntity;
import com.dsa.web4.repository.GuestRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GuestServiceImpl implements GuestService {
	private final GuestRepository gr;

	@Override
	public void writeData(GuestBookDTO guest) {
		// builder
				GuestBookEntity g = GuestBookEntity.builder()
									.name(guest.getName())
									.password(guest.getPassword())
									.message(guest.getMessage())
									.build();
				gr.save(g);
	}

	@Override
	public List<GuestBookDTO> selectAllData() {
		List<GuestBookEntity> entityList = gr.findAll();
		List<GuestBookDTO> dtoList = new ArrayList<>();
		
		for (GuestBookEntity entity:entityList) {
			GuestBookDTO dto = new GuestBookDTO();
			dto.setNum(entity.getNum());
			dto.setName(entity.getName());
			dto.setPassword(entity.getPassword());
			dto.setMessage(entity.getMessage());
			dto.setInputdate(entity.getInputdate());
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	@Override
	public boolean deleteData(Integer num) {
		
		boolean result = gr.existsById(num);
		
		if(result)
			gr.deleteById(num);
		
		return result;
	}

	@Override
	public GuestBookDTO selectData(Integer num) {
		GuestBookEntity guest = gr.findById(num).orElse(null);
		if(guest==null) return null;
		
		log.debug("[service=find] guestBookEntity: {}",guest);
		
		GuestBookDTO guestbookDTO = GuestBookDTO.builder()
									.num(guest.getNum())
									.name(guest.getName())
									.password(guest.getPassword())
									.message(guest.getMessage())
									.inputdate(guest.getInputdate())
									.build();
		return guestbookDTO;
	}
}
