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
		data : [ 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3,
				18.3, 13.9, 9.6 ]
	},
	{
		name : '材料一',
		data : [ -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1,
				14.1, 8.6, 2.5 ]
	}];

	var json = {};

	json.title = title;
	json.subtitle = subtitle;
	json.xAxis = xAxis;
	json.yAxis = yAxis;
	json.tooltip = tooltip;
	json.legend = legend;
	json.series = series;
	
	$.getJSON("/report/saleVolume", function(data) {
		alert(data);
		alert(JSON.stringify(data));
		series[0].data = data;
		json.series = series;
		$('#container1').highcharts(json);
		
	});

	
}

$(function() {

	drawYearlyTrendChart();

});