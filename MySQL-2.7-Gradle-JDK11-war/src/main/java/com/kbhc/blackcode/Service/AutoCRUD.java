package com.kbhc.blackcode.Service;

import java.sql.Timestamp;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kbhc.blackcode.Mapper.DataMapper;
import com.kbhc.blackcode.VO.DataVO;

public class AutoCRUD {
	
	String rw;
	private final SqlSession sqlSession;
	
	public AutoCRUD(String rw,
			SqlSession sqlSession) {
		super();
		this.rw = rw;
		this.sqlSession = sqlSession;
	}
	
	private Logger logger = LoggerFactory.getLogger(AutoCRUD_thread.class);
	
	static String msg=null;
	int count = 0 ;
	public void insertMethod(String readOrWrite) {
		count ++;
		DataVO dataVO = new DataVO();
		dataVO.setDate(new Timestamp(System.currentTimeMillis()));
		dataVO.setContents(Integer.toString(count));
		dataVO.setRw(readOrWrite);
		DataMapper dm = sqlSession.getMapper(DataMapper.class);
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

}
