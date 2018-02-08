toastr.options.positionClass = 'toast-top-center';// 提示框位置

//绘制一年内每月销量趋势图
function drawYearlyTrendChart() {
	var title = {
		text : '今年每月销量趋势图'
	};
	var subtitle = {
		text : '浏阳富茂无纺厂'
	};
	var xAxis = {
		categories : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月',
				'九月', '十月', '十一月', '十二月' ]
	};
	var yAxis = {
		title : {
			text : '总金额'
		},
		plotLines : [ {
			value : 0,
			width : 1,
			color : '#808080'
		} ]
	};

	var tooltip = {
		valueSuffix : '元'
	}

	var legend = {
		layout : 'vertical',
		align : 'right',
		verticalAlign : 'middle',
		borderWidth : 0
	};

	var series = [
	{
		name : '总销量',
		data : []
	}
	];

	var json = {};

	json.title = title;
	json.subtitle = subtitle;
	json.xAxis = xAxis;
	json.yAxis = yAxis;
	json.tooltip = tooltip;
	json.legend = legend;
	json.series = series;
	
	var queryYear = $("#year").val();
	
	if($("#year").val()=="") {
		var today = new Date();
		var year = today.getFullYear();
		queryYear = year;
		toastr.info("没有选择年份，默认为" + queryYear);
	}
	
	$.getJSON("/report/saleVolume/" + queryYear, function(data) {
//		alert(data);
//		alert(JSON.stringify(data));
		series[0].data = data;
		json.series = series;
		$('#container1').highcharts(json);
		
	});
}


//绘制一年内欠款客戶前十
function drawDebtTop10() {
	var title = {
		text : '今年欠款排行榜TOP10'
	};
	var subtitle = {
		text : '浏阳富茂无纺厂'
	};
	var xAxis = {
		categories : []
	};
	var yAxis = {
		title : {
			text : '未付金额'
		},
		plotLines : [ {
			value : 0,
			width : 1,
			color : '#808080'
		} ]
	};

	var tooltip = {
		valueSuffix : '元'
	}

	var legend = {
		layout : 'vertical',
		align : 'right',
		verticalAlign : 'middle',
		borderWidth : 0
	};

	var series = [
	{
		name : '未付金额',
		data : []
	}		
	];

	var json = {};

	json.title = title;
	json.subtitle = subtitle;
	json.xAxis = xAxis;
	json.yAxis = yAxis;
	json.tooltip = tooltip;
	json.legend = legend;
	json.series = series;
	
	var queryYear = $("#year").val();
	
	if($("#year").val()=="") {
		var today = new Date();
		var year = today.getFullYear();
		queryYear = year;
		toastr.info("没有选择年份，默认为" + queryYear);
	}
		
	$.getJSON("/report/debtTop10/"+ queryYear, function(data) {
		xAxis.categories = data.nameList;
		series[0].data = data.debtList;
		json.series = series;
		$('#containerDebt').highcharts(json);
			
	});
}

$(function() {

	$("#queryBtn").on("click", function(){
		drawYearlyTrendChart();
		drawDebtTop10();
	});
	

});