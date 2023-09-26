package com.kbhc.blackcode.Service;

import com.kbhc.blackcode.VO.DataVO;

public interface DataService {
	
	int insertData(DataVO dataVo);
	int selectCountData();
}
