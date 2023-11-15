package com.example.demo.VO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [ 템플릿 설명 ]
 * - 해당 파일은 객체를 구성하는 목적으로 사용되는 파일입니다.
 * - Lombok 기능을 이용하여 간단한 VO 구성을 하였습니다.
 */
@Data   // getter / setter / toString() 사용
@NoArgsConstructor  // 생성자를 사용하지 않도록 선언
public class UserVO implements HttpSessionBindingListener{
	private int num;
	private String clientIP;
	private String sessionID;
    private String email;
    private String password;
    static private int totalClient = 0;
    static private List<ClientVO> clientList = new ArrayList<>();;
    static ClientVO client;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
	public void valueBound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		HttpSessionBindingListener.super.valueBound(event);
		System.out.println("Binding Event");
		++totalClient;
		client = new ClientVO();
		client.setIp(clientIP);
		client.setSessionID(sessionID);
		client.setNum(num);
		
		
		/* 기존 Session List 로직 
		if(UserVO.clientList.size() == 0) {
			UserVO.clientList.add(client);
			System.out.println("clientList.get(0) is null");
		}
		
		for (int i = 0; i < clientList.size(); i++) {
			if (UserVO.clientList.get(i) != null) {
				if (!UserVO.clientList.get(i).getIp().equals(clientIP)) {
					UserVO.clientList.add(client);
				}
			}
		}
		 * */
		
		UserVO.clientList.add(client);
		
		System.out.println("===== SESSION CLIENT LIST =====");
		for (int i = 0; i < clientList.size(); i++) {
			System.out.println(UserVO.clientList.get(i) + "\n");
		}
		
	}
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Unbinding Event");
		HttpSessionBindingListener.super.valueUnbound(event);
		totalClient--;
		
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getSessionID().equals(sessionID)) {
				UserVO.clientList.remove(client);
				System.out.println("Session Delete Success : "+sessionID);
			}else {
				System.out.println("Session Delete Fail : "+sessionID);
			}
		}
		
		UserVO.clientList.remove(num);
		
		System.out.println("===== SESSION CLIENT LIST =====");
		for (int i = 0; i < clientList.size(); i++) {
			System.out.println(UserVO.clientList.get(i) + "\n");
		}
	}

	public static int getTotalClient() {
		return totalClient;
	}

	public static void setTotalClient(int totalClient) {
		UserVO.totalClient = totalClient;
	}

	public static List<ClientVO> getClientList() {
		return clientList;
	}

}