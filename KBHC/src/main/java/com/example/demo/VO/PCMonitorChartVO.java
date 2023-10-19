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

	int CPU_Usage_Percent 	= (int) (Math.round(load*100.0));
	int CPU_Idle_Percent 	= (int) (100 - Math.round(load*100.0));
	int HDD_Usage 			= (int) Math.round( Double.valueOf(f.getTotalSpace() - f.getUsableSpace())/ Double.valueOf(f.getTotalSpace()) * 100);
	int HDD_Idle_Percent 	= (int) Math.round( Double.valueOf(f.getUsableSpace()) / Double.valueOf(f.getTotalSpace()) * 100);
	int Memory_Percent 		= (int) (100 - ( Math.round( Double.valueOf(((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize()) / Double.valueOf( ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize() ) * 100)));
	int Memory_Idle_Percent = (int) (Math.round( Double.valueOf(((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize()) / Double.valueOf( ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize() ) * 100));
	
}
