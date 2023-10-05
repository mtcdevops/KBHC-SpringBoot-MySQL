package com.kbhc.blackcode.Service;

import java.util.List;

import com.kbhc.blackcode.VO.DataInfoVO;
import com.kbhc.blackcode.VO.DataVO;

public interface DataService {
	
	void insertData();
	DataInfoVO selectCountData();
	List<DataVO> selectEcxeption();
	Boolean deleteExceptionData();
	Boolean deleteAllData();
}
