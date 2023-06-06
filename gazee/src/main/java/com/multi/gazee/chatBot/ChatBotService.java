package com.multi.gazee.chatBot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@PropertySource("classpath:application.properties")

@Component
public class ChatBotService {
	
	
	@Value("${chatBotSecretKey}")
	private String secretKey;
	
	@Value("${invokeURL}")
	private String invokeURL; 
	public String invoke(String sessionId, ChatSendRequest csr) {
		String chatbotMessage = null;
		System.out.println(invokeURL);
		try {
			ObjectMapper om = new ObjectMapper();			
			String body = om.writeValueAsString(csr);
			System.out.println(body);
			System.out.println(invokeURL);
			byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(secretKeySpec);
			byte[] signature = mac.doFinal(body.getBytes(StandardCharsets.UTF_8));
			String signatureHeader = Base64.getEncoder().encodeToString(signature);
			
			URL url = new URL(invokeURL);
			HttpsURLConnection conns = (HttpsURLConnection) url.openConnection();
			conns.setRequestMethod("POST");
            conns.setRequestProperty("Content-Type", "application/json;UTF-8");
            conns.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", signatureHeader);
            conns.setDoOutput(true);

            // post request
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(conns.getOutputStream(), Charset.forName("UTF-8")));
			wr.write(body); // 요청 바디를 쓰기
            wr.flush();
            wr.close();
            
            if(conns.getResponseCode()==200) { // Normal call
                BufferedReader in = new BufferedReader(new InputStreamReader(conns.getInputStream(), "UTF-8"));
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    chatbotMessage = decodedString;
                }
                //chatbotMessage = decodedString;
                in.close();         
            } else {  // Error occurred
                chatbotMessage = conns.getResponseMessage();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
		
		return chatbotMessage;
	}
	
	public String open(String sessionId) {
		ChatSendRequest csr = new ChatSendRequest();
		csr.setTimestamp(System.currentTimeMillis());
		csr.setEvent("open");
		csr.setUserId(sessionId);
		
		return invoke(sessionId, csr);
	}
	
	public String send(String sessionId, String message) {
		ChatSendRequest csr = new ChatSendRequest();
		csr.setTimestamp(System.currentTimeMillis());
		csr.setEvent("send");
		csr.setUserId(sessionId);
		csr.addText(message);
		
		return invoke(sessionId, csr);
	}
}