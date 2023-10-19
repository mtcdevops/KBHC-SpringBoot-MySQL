package com.example.demo.Controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.VO.Client;
import com.example.demo.VO.PCMonitorChartVO;
import com.example.demo.VO.PCMonitorVO;
import com.example.demo.VO.UserVO;

import lombok.extern.slf4j.Slf4j;

/**
 * [ 템플릿 설명 ]
 * - 해당 파일은 Restful API 형태로 URL 관리하는 파일입니다
 * - 뷰 자체만을 리턴해주거나 API 호출에 대한 처리를 해줍니다.
 * [ Annotation 설명 ]
 * - @Slf4j : 로그를 위해 사용하는 Annotation
 * - @RestController: Restful API 구조에서 JSON 타입으로 데이터를 반환 받기 위해 사용하는 Annotation
 * - @Controller: Spring MVC 패턴을 사용하기 위한 Annotation
 * - @RequestMapper: Controller URL Default
 *
 * @author lee
 * @since 2022.09.30
 */


@Slf4j
@RequestMapping("/")
@Controller
public class MainController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping({"index",""})
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("index");
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("user") == null) {
			modelAndView.setViewName("redirect:login");
			return modelAndView;
		}
		
		
		Client client = new Client();
		String ip = client.getRemoteIP(request);
		UserVO user = (UserVO) session.getAttribute("user");
		session.setAttribute("user", user);
		modelAndView.addObject("clientIP",ip);
		modelAndView.addObject("list", null);
		modelAndView.addObject("count", null);
		
		/* CPU 사용량 */
		PCMonitorVO pcMonitorVO = new PCMonitorVO();
//		modelAndView.addObject("pcMonitorVO", pcMonitorVO);
		
		return modelAndView;
	}
	
	@GetMapping("login")
	public ModelAndView getLogin() {
		ModelAndView modelAndView = new ModelAndView("login");
		System.out.println("Login Page");
		
		 try{
			modelAndView.addObject("hostname", InetAddress.getLocalHost().getHostName());
			modelAndView.addObject("ip", InetAddress.getLocalHost().getHostAddress());
			System.out.println( InetAddress.getLocalHost().getHostName() );
			System.out.println( InetAddress.getLocalHost().getHostAddress() );
		}
		catch( UnknownHostException e ){
		  e.printStackTrace();
		}
		
		/* CPU 사용량 */
		return modelAndView;
	}
	
	@PostMapping("login")
	public String postLogin(HttpServletRequest request, HttpServletResponse response) {
		
		UserVO user = new UserVO();

		user.setEmail(request.getParameter("inputEmail"));
		user.setPassword(request.getParameter("inputPassword"));
		user.setClientIP(new Client().getRemoteIP(request));
		System.out.println(user.toString());
		ModelAndView modelAndView = new ModelAndView("index");
		
		/* session 등록 */
		// 세션을 생성하기 전에 기존의 세션 파기
		request.getSession().invalidate();
		HttpSession session = request.getSession(true);  // Session이 없으면 생성
		// 세션에 userId를 넣어줌
		session.setAttribute("user", user);
		session.setMaxInactiveInterval(1800); // Session이 30분동안 유지
//		return modelAndView;
		return "redirect:/index";
	}
	
	@PostMapping("deleteSession")
	@ResponseBody
	public boolean deleteSession(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			session.invalidate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@PostMapping("chart")
	@ResponseBody
	public PCMonitorChartVO chart() {
		PCMonitorChartVO chart=new PCMonitorChartVO();
//		System.out.println("TEST"+chart.toString());
		return new PCMonitorChartVO();
	}
	
}
