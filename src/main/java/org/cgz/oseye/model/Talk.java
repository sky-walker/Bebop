package org.cgz.oseye.model;

import java.math.BigInteger;

/**
 * 私信会话
 * @author Administrator
 */
public class Talk {

	private BigInteger msgCount;
	
	private Private_Message lastMessage;

	private BigInteger newMsgNum;
	
	public BigInteger getNewMsgNum() {
		return newMsgNum;
	}

	public void setNewMsgNum(BigInteger newMsgNum) {
		this.newMsgNum = newMsgNum;
	}

	public BigInteger getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(BigInteger msgCount) {
		this.msgCount = msgCount;
	}

	public Private_Message getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(Private_Message lastMessage) {
		this.lastMessage = lastMessage;
	}
}
