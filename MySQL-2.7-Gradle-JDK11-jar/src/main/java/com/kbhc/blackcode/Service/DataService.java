package com.kbhc.blackcode.Service;

import java.util.List;

import com.kbhc.blackcode.VO.DBServerInfoVO;
import com.kbhc.blackcode.VO.DataInfoVO;
import com.kbhc.blackcode.VO.DataVO;
import com.kbhc.blackcode.VO.DatabaseVO;

public interface DataService {
	
//	void insertData();
//	DataInfoVO selectCountData();
	List<DataVO> selectEcxeption();
	DataInfoVO selectCountData();
	Boolean deleteExceptionData();
	Boolean deleteAllData();
	DBServerInfoVO showServerID();
	List<DatabaseVO> selectAllDatabase();
}
