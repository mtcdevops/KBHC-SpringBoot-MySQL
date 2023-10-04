package com.kbhc.blackcode.Service;

import java.sql.Timestamp;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kbhc.blackcode.Mapper.DataMapper;
import com.kbhc.blackcode.VO.DataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class DataServiceImp implements DataService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
     * '생성자 주입 형태'로 사용합니다.
     * - Autowired 는 권장되지 않기에 생성자 주입 형태로 구성합니다.
     */
	private final SqlSession sqlSession;
	private final SqlSession masterSqlSession;
	private final SqlSession slaveSqlSession;
//	private final DataSource master;
//	private final DataSource slave;
	
	public DataServiceImp(SqlSession sqlSession,
			@Qualifier("masterSqlSession") SqlSession masterSqlSession,
			@Qualifier("slaveSqlSession") SqlSession slaveSqlSession) {
		super();
		this.sqlSession = sqlSession;
		this.masterSqlSession = masterSqlSession;
		this.slaveSqlSession = slaveSqlSession;
	}
	
	/**
	 * 1초에 2번 Auto Insert
	 */
	@Scheduled(fixedRate = 250, zone = "Asia/Seoul")
	public void insertData() {
		insertMethod("W");
	}
	
	/**
	 * 1초에 1번 Auto Select
	 */
	@Transactional(readOnly = true)
	@Scheduled(fixedDelay = 1000, zone = "Asia/Seoul")
	public void selectCountData() {
		DataMapper dm = slaveSqlSession.getMapper(DataMapper.class);
		dm.selectCountData();
		insertMethod("R");
	}
	
	int count = 0 ;
	public void insertMethod(String readOrWrite) {
		count ++;
		DataVO dataVO = new DataVO();
		dataVO.setDate(new Timestamp(System.currentTimeMillis()));
		dataVO.setContents(Integer.toString(count));
		dataVO.setRw(readOrWrite);
		DataMapper dm = masterSqlSession.getMapper(DataMapper.class);
		try {
			dm.insertData(dataVO);
		} catch (Exception e) {
			dataVO.setContents("Exception : "+e);
			dm.insertData(dataVO);
		}
		logger.info(dataVO.getDate()+">>>"+count);
	}
}
