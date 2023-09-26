package com.kbhc.blackcode.Controller;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kbhc.blackcode.Service.DataService;
import com.kbhc.blackcode.VO.DataVO;

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
	
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		System.out.println("Main Index Page");
		return modelAndView;
	}
	
	@GetMapping("total")
	public int countData() {
		System.out.println();
		return dataService.selectCountData();
	}
	
	int count = 0;
	@Scheduled(fixedDelay = 1000, zone = "Asia/Seoul")
	public ResponseEntity<Integer> insertData() {
		DataVO dataVO = new DataVO();
		Integer result = null;
		count++ ;
		dataVO.setDate(new Timestamp(System.currentTimeMillis()));
		dataVO.setContents(Integer.toString(count));
		try {
			result = dataService.insertData(dataVO);
		} catch (Exception e) {
			dataVO.setContents("Exception : "+e);
			result = dataService.insertData(dataVO);
		}
		logger.info(dataVO.getDate()+">>>"+count);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
