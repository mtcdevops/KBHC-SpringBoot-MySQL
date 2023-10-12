package com.kbhc.blackcode.Controller;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kbhc.blackcode.Service.DataService;
import com.kbhc.blackcode.VO.PCMonitorVO;
import com.kbhc.blackcode.VO.UserVO;

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
	
	// 필드 주입이 아닌 생성자 주입형태로 사용합니다. '생성자 주입 형태'로 사용합니다.
	private final DataService dataService;
	public MainController(DataService dataService) {
		super();
		this.dataService = dataService;
	}
	
	@GetMapping("index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("index");
		HttpSession session = request.getSession(false);
		
		System.out.println("Main Index Page");
		System.out.println("SESSION : "+session.getAttribute("user"));
		
		modelAndView.addObject("list", dataService.selectEcxeption());
		modelAndView.addObject("count", dataService.selectCountData());
		
		/* CPU 사용량 */
		PCMonitorVO pcMonitorVO = new PCMonitorVO();
//		modelAndView.addObject("pcMonitorVO", pcMonitorVO);
		
		return modelAndView;
	}
	
	@GetMapping("login")
	public ModelAndView getLogin() {
		ModelAndView modelAndView = new ModelAndView("login");
		System.out.println("Login Page");
		
		/* CPU 사용량 */
		return modelAndView;
	}
	
	@PostMapping("login")
	@ResponseBody
	public ModelAndView postLogin(HttpServletRequest request, HttpServletResponse response) {
		
		UserVO user = new UserVO();

		user.setEmail(request.getParameter("inputEmail"));
		user.setPassword(request.getParameter("inputPassword"));
		
		ModelAndView modelAndView = new ModelAndView("index");
		
		/* session 등록 */
		// 세션을 생성하기 전에 기존의 세션 파기
		request.getSession().invalidate();
		HttpSession session = request.getSession(true);  // Session이 없으면 생성
		// 세션에 userId를 넣어줌
		session.setAttribute("user", user);
		session.setMaxInactiveInterval(1800); // Session이 30분동안 유지
		return modelAndView;
	}
	
	
	
	@PostMapping("deleteException")
	@ResponseBody
	public String deleteException(@RequestBody String msg) {
		String jsonData = URLDecoder.decode(msg, StandardCharsets.UTF_8);
		System.out.println(jsonData);
		if(dataService.deleteExceptionData().equals("OK")) {
			return "data";
		}else {
			return "error";
		}
	}
	
	@PostMapping("deleteAllData")
	@ResponseBody
	public String deleteAllData(@RequestBody String msg) {
		String jsonData = URLDecoder.decode(msg, StandardCharsets.UTF_8);
		System.out.println(jsonData);
		if(dataService.deleteAllData().equals("OK")) {
			return "data";
		}else {
			return "error";
		}
	}
	
	public void getCPUProcess() {
		OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();		
//		String cpuUsage = String.format("%.2f", osbean.getSystemCpuLoad() * 100);
		
		System.out.println(osbean.getAvailableProcessors());;
		
//		return list;
	}
}
