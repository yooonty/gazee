package com.multi.gazee.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

	@Configuration
	@EnableWebSocketMessageBroker
	public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {
		
		public void configureMessageBroker(MessageBrokerRegistry config) {
			// "/topic" 시작되는 destination은 메시지를 broker로 라우팅 한다.
			config.enableSimpleBroker("/topic");
			// "/app" 시작되는 destination은 @MessageMapping으로 라우팅 된다.
			config.setApplicationDestinationPrefixes("/app");
		}
		
		@Override
		public void registerStompEndpoints(StompEndpointRegistry registry) {
			registry.addEndpoint("/chat/{roomId}");
			registry.addEndpoint("/chat/{roomId}").withSockJS();
			registry.addEndpoint("/user/{userId}").withSockJS();
		}
	}
