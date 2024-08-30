package net.datasa.test.service;

import java.util.List;

import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.domain.dto.ReplyDTO;

public interface BoardService {

	void write(BoardDTO boardDTO);

	List<BoardDTO> selectAllList();

	BoardDTO getBoard(int boardNum);

	void delete(int boardNum, String username);

	void buy(int boardNum, String username);

}
