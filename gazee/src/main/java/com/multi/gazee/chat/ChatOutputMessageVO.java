package com.multi.gazee.chat;

public class ChatOutputMessageVO {
	
	private String roomId;
	private String sender;
	private String content;
	private String time;
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String string) {
		this.time = string;
	}

	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	@Override
	public String toString() {
		return "ChatOutputMessage [roomId=" + roomId + ", sender=" + sender + ", content=" + content + ", time=" + time
				+ "]";
	}
	
	
	
	
}
