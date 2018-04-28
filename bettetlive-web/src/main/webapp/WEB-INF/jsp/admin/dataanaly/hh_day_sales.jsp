<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>月营业额</title>
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/base.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/easyui.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/plugin/custom/uimaker/icon.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/providers.css">
<link rel="stylesheet" type="text/css"  href="${resourcepath}/admin/css/basic_info.css" >
				
<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/echarts.common.min.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/dataanaly/hh_day_sales.js"></script>
<script type="text/javascript">
	/**
	 * 初始化数据
	 */
	$(function(){
		// 初始化查询年限
		var queryDate = "${queryDate}";
		if (queryDate != null && queryDate != "") {
			$("select[name='queryYear']").val(queryDate.substring(0, 4));
			$("select[name='queryMonth']").val(queryDate.substring(5, 7));
		}
	});
	
	// 查询年度月报表
	function queryMonthCanvas(){
		var queryDate = "${queryDate}";
		$("#queryDate").val(queryDate.substring(0, 4));
		$("#canvasForm").attr("action", "${mainserver}/admin/orderanaly/hhMonthSales");
		document.forms['canvasForm'].submit();
	}
	
	// 查询日营收折线图
	function queryDayCanvas(){
		var queryDate = $("select[name='queryYear']").val() + "-" + $("select[name='queryMonth']").val();
		$("#queryDate").val(queryDate);
		document.forms['canvasForm'].submit();
	}

	function toOrders(queryDate){
		
		var centerTabs = parent.centerTabs;
		var url ="${mainserver}/admin/order/findList?queryDate="+queryDate+"&queryFlag=querySales";
		if (centerTabs.tabs('exists', '订单列表')) {
			centerTabs.tabs('select', '订单列表');
			var tab = centerTabs.tabs('getTab', '订单列表');
			var option = tab.panel('options');
			option.content = '<iframe class="page-iframe" src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
			centerTabs.tabs('update', {
				tab : tab,
				options : option
			});
		} else {
			centerTabs.tabs('add', {
    			title : '订单列表',
    			closable : true,
    			content : '<iframe class="page-iframe" src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
    		});
		} 
	}
</script>
</head>

<!-- 返回店铺年度表 -->
<form id="canvasForm" name="canvasForm" method="post" action="${mainserver}/admin/orderanaly/hhDaySales">
	<input type="hidden" id="queryDate" name="queryDate" value="${queryDate}"/>
</form>
<div id="orderMonthSales" style="overflow:hidden;zoom:1;">
	<input type="button" value="返回" onclick="queryMonthCanvas();" style="float:left;">
	<input type="button" value="挥货日营收报表" onclick="$('#canvasForm').submit();" style="float:left;">
<table style="width: 100%;margin-top: 50px;">
	<tr style="text-align: center; COLOR: #0076C8;font-weight: bold">
		<td>
			<div>
				<label>选择营业年份:</label>
				<span>
					<select style="width: 120px; border: 1px solid #dcdcdc;" name="queryYear" >
						<option value="2017">2017</option>
						<option value="2018">2018</option>
						<option value="2019">2019</option>
						<option value="2020">2020</option>
						<option value="2021">2021</option>
						<option value="2022">2022</option>
						<option value="2023">2023</option>
						<option value="2024">2024</option>
					</select>
				</span>
				<label>&nbsp;&nbsp;选择月份:</label>
				<span>
					<select style="width: 120px; border: 1px solid #dcdcdc;" name="queryMonth" >
						<option value="01">1月</option>
						<option value="02">2月</option>
						<option value="03">3月</option>
						<option value="04">4月</option>
						<option value="05">5月</option>
						<option value="06">6月</option>
						<option value="07">7月</option>
						<option value="08">8月</option>
						<option value="09">9月</option>
						<option value="10">10月</option>
						<option value="11">11月</option>
						<option value="12">12月</option>
					</select>
				</span>
				<a onclick="queryDayCanvas()" style="cursor: pointer; display:inline-block; vertical-align:middle;width:60px; height:30px; line-height:30px; margin-left:20px; text-align:center; font-size:16px; color:#fff; background:#0093dd; -moz-border-radius:3px;-webkit-border-radius:3px;border-radius:3px; text-decoration:none;">查询</a>
			</div>
		</td>
	</tr>
<tr>
	<td>
		<div>
		<c:choose>
		<c:when test="${dayTotalMoneys == null || dayTotalMoneys.length() == 0}">
			<div align='center'>挥货平台${queryDate}月份暂无营收数据</div>
		</c:when>
		<c:otherwise>
			<div id="myCanvas" style="height:500px;width:100%;margin-top: 10px;"></div>
			<script type="text/javascript">
			//定义数据
			$(function(){
				var dayTotalMoneys = "${dayTotalMoneys}";
				dayTotalMoneys = dayTotalMoneys.split(",");
				
				var dayTotalPays = "${dayTotalPays}";
				dayTotalPays = dayTotalPays.split(",");
				
				var days = "${days}";
				days = days.split(",");
				var dayMoneyValue = new Array();
				for(var i=0;i<dayTotalMoneys.length;i++) 
				{
					dayMoneyValue.push(dayTotalMoneys[i]);
				}
				
				var dayPayValue = new Array();
				for(var i=0;i<dayTotalPays.length;i++) 
				{
					dayPayValue.push(dayTotalPays[i]);
				}
				
				var daysValue = new Array();
				for(var i=0;i<days.length;i++) 
				{
					daysValue.push(days[i]);
				}
				
				
				var legendName = '${queryDate}' + '月份日营收折线图';
				var option = {
					    title: {
					        text: legendName,
					        left:"center"
					    },
					    tooltip: {
					        trigger: 'axis'
// 					        ,
// 					        formatter: function(params){
// 					        	var tip = '日期：' + '${queryDate}' + '-' + params[0].name;
// 		        				if(params[0].value == undefined){
// 		        					return tip + '<br>暂无数据';
// 		        				} else {
// 		        					return tip + '<br>销售总额：' + params[0].value + '万' + '<br>实售总额：' + params[1].value + '万';
// 		        				}
// 					        }
					    },
					    legend: {
					        data:['销售总额','实售总额']
					    },
					    grid: {
					        left: '4%',
					        right: '5%',
					        bottom: '4%',
					        containLabel: true
					    },
					    toolbox: {
					    	show: true,
					        feature: {
					            dataZoom: {
					                yAxisIndex: 'none'
					            },
					            dataView: {readOnly: false},
					            magicType: {type: ['line', 'bar']},
					            restore: {},
					            saveAsImage: {}
					        }
					    },
					    xAxis: {
					        type: 'category',
					        boundaryGap: false,
					        data: daysValue
					    },
					    yAxis:{
							type : 'value',
							name: '日营收额(元)',
							axisLabel : {
								formatter: '{value}'
							}
// 					    	,splitNumber:5
					    },
					    series: [
					        {
					            name:'日销售额(元)',
					            type:'line',
					            data:dayMoneyValue,
					        	itemStyle:{
					            	normal:{color:'#ffdcae'}
								}
					        }, {
					            name:'日实售额(元)',
					            type:'line',
					            data:dayTotalPays,
					        	itemStyle:{
					            	normal:{color:'#ff0000'}
								}
					        }
					    ]
					};
			
					var myChart = echarts.init(document.getElementById('myCanvas')); 
					myChart.setOption(option);
					myChart.on('click', function (params) {
						var day = params.dataIndex + 1;
						if(day < 10){
							day = '0' + day;
						}
						var queryDay = '${queryDate}' + '-' + day;
						toOrders(queryDay);
					});
			});
			</script>
			<div id="tips" style="margin-left:80px;  font-size:12px;">
				<span><i>Tips：点击坐标查看当天订单明细列表</i></span>
			</div>
		</c:otherwise>
		</c:choose>
		</div>
		</td>
	</tr>
	</table>
</div>

</body>
</html>