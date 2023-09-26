package com.kbhc.blackcode.DataSource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

public class Snippet {
	private static final String MASTER_DATASOURCE = null;
	private static final String SLAVE_DATASOURCE = null;

	@Bean
	@Primary
	@DependsOn({MASTER_DATASOURCE, SLAVE_DATASOURCE})
	public DataSource routingDataSource(
	    @Qualifier(MASTER_DATASOURCE) DataSource masterDataSource,
	    @Qualifier(SLAVE_DATASOURCE) DataSource slaveDataSource) {
	
	    RoutingDataSource routingDataSource = new RoutingDataSource();
	
	    Map<Object, Object> datasourceMap = new HashMap<>() {
	        {
	            put("master", masterDataSource);
	            put("slave", slaveDataSource);
	        }
	    };
	
	    routingDataSource.setTargetDataSources(datasourceMap);
	    routingDataSource.setDefaultTargetDataSource(masterDataSource);
	
	    return routingDataSource;
	}
}

