<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<c:forEach items="${list}" var="bag" varStatus="status">
	<%
		@SuppressWarnings("unchecked")
        List<String> nickname = (List<String>) request.getAttribute("nickname");
	
		@SuppressWarnings("unchecked")
        List<String> profile = (List<String>) request.getAttribute("profile");
	
		@SuppressWarnings("unchecked")
        List<String> lastMessage = (List<String>) request.getAttribute("lastMessage");
		
		@SuppressWarnings("unchecked")
        List<String> lastMessageTime = (List<String>) request.getAttribute("lastMessageTime");
    %>
	<li class="chat_list" id="chat${bag.roomId}" value="${bag.roomId}">
		<div class="chatList">
			<div class="newMessage"></div>
			<div class="chatPartnerProfile" style="background-image: url('http://zurvmfyklzsa17604146.cdn.ntruss.com/${profile[status.index]}?type=f&w=40&h=40')">
			</div>
			<div style="width: 160px;">
				<div style="display: flex; justify-content: space-between; align-items: center;">
					<div class="chatRoomName">${nickname[status.index]}</div>
					<div class="recentMessageDate">${lastMessageTime[status.index]}</div>
				</div>
				<p class="recentMessage">${lastMessage[status.index]}</p>
			</div>
		</div>
	</li>
</c:forEach>