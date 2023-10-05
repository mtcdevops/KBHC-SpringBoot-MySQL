package com.kbhc.blackcode.Service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kbhc.blackcode.Mapper.DataMapper;
import com.kbhc.blackcode.VO.DataInfoVO;
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
	
	static String LocalWrite = "WW";
	static String LocalRead = "RR";
	static String WebAppWrite = "W";
	static String WebAppRead = "R";
	static String temp = "AS"; //배포시 : AS
	/**
	 * 1초에 2번 Auto Insert
	 */
	@Scheduled(fixedDelay = 1000, zone = "Asia/Seoul")
	public void insertData() {
		DBcrud insert = null;
		if(temp.equals("Local")) {
			insert = new DBcrud(LocalWrite,masterSqlSession);
		}else {
			insert = new DBcrud(WebAppWrite,masterSqlSession);
		}
		insert.start();
//		insertMethod("W");
	}
	
	/**
	 * 1초에 1번 Auto Select
	 * @return 
	 */
	@Transactional(readOnly = true)
	@Scheduled(fixedDelay = 1000, zone = "Asia/Seoul")
	public DataInfoVO selectCountData() {
		DataMapper dm = slaveSqlSession.getMapper(DataMapper.class);
		DBcrud insert = null;
		if(temp.equals("Local")) {
			insert = new DBcrud(LocalRead,masterSqlSession);
		}else {
			insert = new DBcrud(WebAppRead,masterSqlSession);
		}
		
		insert.start();
//		insertMethod("R");
		return dm.selectCountData();
	}
	static String msg=null;
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
			if (msg != null) {
				msg = null;
			}
			msg = "Exception : "+e;
		} finally {
			if (msg != null) {
				dataVO.setContents(msg);
				dm.insertData(dataVO);
			}else {
				if (!dataVO.getContents().equals(Integer.toString(count))){
					dataVO.setContents(Integer.toString(count));
					dm.insertData(dataVO);
				}
			}
		}
		logger.info(dataVO.getDate()+">>>"+count);
	}

	@Override
	public List<DataVO> selectEcxeption() {
		DataMapper dm = slaveSqlSession.getMapper(DataMapper.class);
		return dm.selectException();
	}

	@Override
	public Boolean deleteExceptionData() {
		try {
			masterSqlSession.getMapper(DataMapper.class).deleteExceptionData();;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean deleteAllData() {
		try {
			masterSqlSession.getMapper(DataMapper.class).deleteAllData();;;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
