package com.kosa.pro30.board.dao;

import java.util.List;

import com.kosa.pro30.board.domain.ReplyDTO;

public interface ReplyDAO {
	public List<ReplyDTO> getReplyList(ReplyDTO reply);

	public int insert(ReplyDTO reply);

	public ReplyDTO getLastreply();

	public int delete(ReplyDTO reply);

	public int update(ReplyDTO reply);
	
	public ReplyDTO getreply(ReplyDTO reply);

}
