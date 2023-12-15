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
import com.kbhc.blackcode.VO.DBServerInfoVO;
import com.kbhc.blackcode.VO.DataInfoVO;
import com.kbhc.blackcode.VO.DataVO;
import com.kbhc.blackcode.VO.DatabaseVO;

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
	static String temp = "Local"; //배포시 : AS
	/**
	 * 1초에 2번 Auto Insert
	 */
	@Scheduled(fixedDelay = 1000, zone = "Asia/Seoul")
	public void insertData() {
		AutoCRUD_thread insert = null;
		if(temp.equals("Local")) {
			insert = new AutoCRUD_thread(LocalWrite,masterSqlSession);
		}else {
			insert = new AutoCRUD_thread(WebAppWrite,masterSqlSession);
		}
		insert.start(); // Thread 시작
		// insertMethod("W");
	}
	
	/**
	 * 1초에 1번 Auto Select > 화면이 호출될때마다 자동으로 Select됨
	 * @return 
	 */
	@Transactional(readOnly = true)
	@Scheduled(fixedDelay = 1000, zone = "Asia/Seoul")
	public DataInfoVO selectCountData() {
		DataMapper dm = slaveSqlSession.getMapper(DataMapper.class);
		AutoCRUD_thread insert = null;
		if(temp.equals("Local")) {
			insert = new AutoCRUD_thread(LocalRead,masterSqlSession);
		}else {
			insert = new AutoCRUD_thread(WebAppRead,masterSqlSession);
		}
		
		insert.start(); // Thread 시작
//		insertMethod("R");
		return dm.selectCountData();
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
	
	@Scheduled(fixedDelay = 1000, zone = "Asia/Seoul")
	@Override
	public DBServerInfoVO showServerID() {
		
		// 1. show server_id 조회
		DataMapper slaveDm = slaveSqlSession.getMapper(DataMapper.class);
		DataMapper masterDm = masterSqlSession.getMapper(DataMapper.class);
		DBServerInfoVO dbServer = slaveDm.showServerID();
		
		// 2. database server list 조회
		System.out.println(dbServer.getValue());
		String server_id = dbServer.getValue();
		
		if (slaveDm.selectDB(server_id) == null) {
			DatabaseVO db = new DatabaseVO();
			db.setServer_id(dbServer.getValue());
			masterDm.insertDatabase(db);
		}
		
		return slaveDm.showServerID();
	}

	@Override
	public List<DatabaseVO> selectAllDatabase() {
		DataMapper slaveDm = slaveSqlSession.getMapper(DataMapper.class);
		return slaveDm.selectDBList();
	}

}
