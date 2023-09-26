package com.kbhc.blackcode.Service;

import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kbhc.blackcode.Mapper.DataMapper;
import com.kbhc.blackcode.VO.DataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataServiceImp implements DataService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
     * '생성자 주입 형태'로 사용합니다.
     * - Autowired 는 권장되지 않기에 생성자 주입 형태로 구성합니다.
     */
	private final SqlSession sqlSession;
//	private final DataSource master;
//	private final DataSource slave;
	
	public DataServiceImp(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
//		this.master = master;
//		this.slave = slave;
	}
	
	/**
	 * 1초에 2번 Auto Insert
	 */
	int count = 0 ;
	@Scheduled(fixedDelay = 500, zone = "Asia/Seoul")
	public void insertData() {
		DataVO dataVO = new DataVO();
		count ++;
		dataVO.setDate(new Timestamp(System.currentTimeMillis()));
		dataVO.setContents(Integer.toString(count));
		DataMapper dm = sqlSession.getMapper(DataMapper.class);
		try {
			dm.insertData(dataVO);
		} catch (Exception e) {
			dataVO.setContents("Exception : "+e);
			dm.insertData(dataVO);
		}
		logger.info(dataVO.getDate()+">>>"+count);
	}
	
	/**
	 * 1초에 1번 Auto Select
	 */
	@Scheduled(fixedDelay = 1000, zone = "Asia/Seoul")
	public void selectCountData() {
		DataMapper dm = sqlSession.getMapper(DataMapper.class);
		logger.info("TOTAL : "+dm.selectCountData());
	}

}
