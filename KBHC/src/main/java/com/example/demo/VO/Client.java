package com.example.demo.VO;

import javax.servlet.http.HttpServletRequest;

public class Client {
	
	public String getRemoteIP(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for"); 
		//proxy 환경일 경우
		if (ip == null || ip.length() == 0) {
		    ip = request.getHeader("Proxy-Client-IP");
		    System.out.println("Proxy-Client-IP : "+ip);
		}
		
		//웹로직 서버일 경우
		if (ip == null || ip.length() == 0) {
		    ip = request.getHeader("WL-Proxy-Client-IP");
		    System.out.println("WL-Proxy-Client-IP : "+ip);
		}
		
		if (ip == null || ip.length() == 0) {
		    ip = request.getRemoteAddr() ;
		    System.out.println("request.getRemoteAddr() : "+ip);
		}
		
//		System.out.println("CLIENT IP :"+ip);
		return ip;
	}
}
