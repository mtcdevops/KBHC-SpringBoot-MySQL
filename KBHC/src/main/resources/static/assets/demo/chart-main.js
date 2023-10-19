//chartBar()
var cpu = 0;
var hdd = 0;
var memory = 0;
var pCMonitorChartData = null;
function chartBar(){
	$.ajax({
		type: 'post',
		url : 'chart',
		async : true,            // Ã«Â¹ÂÃ«ÂÂÃªÂ¸Â°Ã­ÂÂ Ã¬ÂÂ¬Ã«Â¶Â (default : true)
		/* headers : {              // Http header
		      "Content-Type" : "application/json",
		      "X-HTTP-Method-Override" : "POST"
		    }, */
		contentType: 'application/json', // Ã«ÂÂ°Ã¬ÂÂ´Ã­ÂÂ° Ã­ÂÂÃ¬ÂÂÃ¬ÂÂ JSONÃ¬ÂÂ¼Ã«Â¡Â Ã¬ÂÂ¤Ã¬Â Â
		dataType : 'json',       // Ã«ÂÂ°Ã¬ÂÂ´Ã­ÂÂ° Ã­ÂÂÃ¬ÂÂ (html, xml, json, text Ã«ÂÂ±Ã«ÂÂ±)
		data : {},
		error : function(request, status, error) {
			console.log("request : ",request);
			console.log("status : ",status);
			console.log("error : ",error);
		},
		success : function(result) {
			//console.log(result)
			cpu 		= result.cpu_Usage_Percent;
			cpu_idle 	= result.cpu_Idle_Percent;
			hdd 		= result.hdd_Usage;
			hdd_idle 	= result.hdd_Idle_Percent;
			memory 		= result.memory_Percent;
			memory_idle = result.memory_Idle_Percent;
			
			//console.log(cpu, hdd, memory);
			PCMonitorChart = new Chart(ctx2, chartParam(cpu, cpu_idle, hdd, hdd_idle, memory, memory_idle))
		}
	});
}

//Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

function chartParam (cpu, cpu_idle, hdd, hdd_idle, memory, memory_idle) {
	data = { type: 'bar',
		data: {
			labels: ["CPU","CPU Idle", "HDD", "HDD Idle", "Memory", "Memory Idle"],
			datasets: [{
				label: "Revenue",
				backgroundColor: "rgba(2,117,216,1)",
				borderColor: "rgba(2,117,216,1)",
				data: [cpu, cpu_idle, hdd, hdd_idle, memory, memory_idle],
			}],
		},
		options: {
			scales: {
				xAxes: [{
					time: {
						unit: 'month'
					},
					gridLines: {
						display: false
					},
					ticks: {
						maxTicksLimit: 6
					}
				}],
				yAxes: [{
					ticks: {
						min: 0,
						max: 100,
						maxTicksLimit: 6
					},
					gridLines: {
						display: true
					}
				}],
			},
			legend: {
			  display: false
			}
		}
	}// chartParam
	return data;
}
// Bar Chart Example
var ctx2 = document.getElementById("PCMonitorChart");
var PCMonitorChart;