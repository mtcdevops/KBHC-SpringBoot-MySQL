package com.kbhc.blackcode.Service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.kbhc.blackcode.Mapper.DataMapper;
import com.kbhc.blackcode.VO.DataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataServiceImp implements DataService {
	
	/**
     * '생성자 주입 형태'로 사용합니다.
     * - Autowired 는 권장되지 않기에 생성자 주입 형태로 구성합니다.
     */
	private final SqlSession sqlSession;
	
	public DataServiceImp(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}


	public int insertData(DataVO dataVo) {
		DataMapper dm = sqlSession.getMapper(DataMapper.class);
		return dm.insertData(dataVo);
	}


	@Override
	public int selectCountData() {
		DataMapper dm = sqlSession.getMapper(DataMapper.class);
		return dm.selectCountData();
	}

}
