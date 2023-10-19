package com.example.demo.VO;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data   // getter / setter / toString() 사용
@NoArgsConstructor  // 생성자를 사용하지 않도록 선언
public class PCMonitorChartVO {
	@JsonIgnore
	OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
	double load = ((com.sun.management.OperatingSystemMXBean) osBean).getSystemCpuLoad();

	File f = new File("/");

	int CPU_Usage_Percent = (int) (Math.round(load*100.0));
	int HDD_Usage = (int) ( Math.round( (f.getTotalSpace() - f.getUsableSpace())/(1024*1024)/ 1000.0));
	int Memory_Idle_Percent = (int) ( Math.round( Double.valueOf(((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize()) / Double.valueOf( ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize() ) * 100));
	
}
