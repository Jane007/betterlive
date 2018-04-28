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
<script type="text/javascript" src="${resourcepath}/admin/js/dataanaly/hh_month_sales.js"></script>
<script type="text/javascript">
	var mainServer = "${mainserver}";
	/**
	 * 初始化数据
	 */
	$(function(){
		// 初始化查询年限
		var queryDate = "${queryDate}";
		if (queryDate != null && queryDate != "") {
			$("select[name='selectDate']").val(queryDate);
		}
	});
	
	// 查询年度月报表
	function queryMonthCanvas(selectObj){
		if(selectObj.value == '${queryDate}'){
			return;
		}
		$("#queryDate").val(selectObj.value);
		document.forms['canvasForm'].submit();
	}
	function exportExcel() {
		var monthTotalMoneys = "${monthTotalMoneys}";
		var monthTotalPays = "${monthTotalPays}";
		var queryDate = "${queryDate}";
		location.href = mainServer+"/admin/orderanaly/exportExcelByhhMonth?monthTotalMoneys="+monthTotalMoneys+"&monthTotalPays="+monthTotalPays+"&queryDate="+queryDate;
	}
</script>
</head>

<body>
<!-- 返回店铺年度表 -->
<form id="canvasForm" name="canvasForm" method="post" action="${mainserver}/admin/orderanaly/hhMonthSales">
	<input type="hidden" id="queryDate" name="queryDate" value="${queryDate}"/>
</form>
<div id="orderMonthSales" style="overflow:hidden;zoom:1;">
	<input type="button" value="挥货月度营收报表" onclick="$('#canvasForm').submit();" style="float:left;">
	<input type="button" value="其他平台月度营收报表" onclick="queryOtherMonthCanvas()" style="margin-left: 0px;float:left;">
<table style="width: 100%;margin-top: 40px;">
	<tr style="text-align: center; COLOR: #0076C8;font-weight: bold">
		<td style="padding-bottom: 10px;"> 
			<a href="javascript:customerSourceStatistics();">用户来源（订单统计）</a>&nbsp;&nbsp;
			<a href="javascript:orderSourceStatistics();">订单来源</a>&nbsp;&nbsp;
			<a href="javascript:productSalseStatistics();">商品销量榜</a>
		</td>
	</tr>
	<tr style="text-align: center; COLOR: #0076C8;font-weight: bold">
		<td>
		<div style="padding-bottom:10px">
			<label>选择年份：</label>
			<span>
				<select style="width: 120px; border: 1px solid #dcdcdc;" name="selectDate" onchange="queryMonthCanvas(this)">
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
			<span>
			<a id="conExport"
				class="easyui-linkbutton" data-options="selected:true"
				onclick="exportExcel()">导出</a></span>
		</div>
		</td>
	</tr>
<tr>
	<td>
		<div>
		<c:choose>
		<c:when test="${monthTotalMoneys == null || monthTotalMoneys.length() == 0}">
			<div align='center'>挥货平台${queryDate}年份暂无营收数据</div>
		</c:when>
		<c:otherwise>
			<div id="myCanvas" style="height:550px;width:100%;"></div>
			<script type="text/javascript">
			//定义数据
			$(function(){
				var monthTotalMoneys = "${monthTotalMoneys}";
				monthTotalMoneys = monthTotalMoneys.split(",");
				
				var monthTotalPays = "${monthTotalPays}";
				monthTotalPays = monthTotalPays.split(",");
				
				var totalMoneyValue = new Array();
				for(var i=0;i<monthTotalMoneys.length;i++) 
				{
					totalMoneyValue.push(monthTotalMoneys[i]);
				}
				
				var totalPayValue = new Array();
				for(var i=0;i<monthTotalPays.length;i++) 
				{
					totalPayValue.push(monthTotalPays[i]);
				}
				
				var legendName = '${queryDate}' + '年挥货平台月度营收表';
				var option = {
					    title: {
					        text: legendName,
					        left:"center"
					    },
					  	tooltip : {
				            trigger: 'axis',
				            axisPointer: {
				                type: 'shadow',
				                label: {
				                    show: true
				                }
				            }
				        },
					    toolbox: {
					    	show : true,
				            feature : {
				                mark : {show: true},
				                dataView : {show: true, readOnly: false},
				                magicType: {show: true, type: ['line', 'bar']},
				                restore : {show: true},
				                saveAsImage : {show: true}
				            }
					    },
					    calculable : true,
					    xAxis: [
					        {
					            type: 'category',
					            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
					        }
					    ],
					    yAxis: [
					        {
					            type: 'value',
					            name: '月营收额(元)',
// 					            interval: 5,
								axisLabel: {
					                formatter: '{value}'
					            }
					        },
					    ],
					    series: [
					        {
					            name:'月销售额(元)',
					            label:{
					            	normal:{
					            		show:true,
					            		position:'top',
					            		textStyle:{
					            			color:'#dc9f4f',
					            			fontSize:14
					            		}
					            	}
					            },
					            type:'bar',
					            data:totalMoneyValue,
								itemStyle:{
					            	normal:{color:'#ffdcae'}
								},
					            markLine : {
					                data : [
					                    {type : 'average', name: '平均值'}
					                ]
					            }
					        },{
					            name:'月实售额(元)',
					            label:{
					            	normal:{
					            		show:true,
					            		position:'top',
					            		textStyle:{
					            			color:'#dc0000',
					            			fontSize:14
					            		}
					            	}
					            },
					            type:'bar',
					            data:totalPayValue,
								itemStyle:{
					            	normal:{color:'#ff0000'}
								},
					            markLine : {
					                data : [
					                    {type : 'average', name: '平均值'}
					                ]
					            }
					        }
					    ]
					};
			
					var myChart = echarts.init(document.getElementById('myCanvas')); 
					myChart.setOption(option);
					myChart.on('click', function (params) {
						var month = params.dataIndex + 1;
						if(month < 10){
							month = '0' + month;
						}
						var queryMonth = '${queryDate}' + '-' + month;
						queryDayCanvas(queryMonth);
					});
			});
			
			</script>
			<div id="tips" style="margin-left:80px; font-size:12px;">
				<span><i>Tips：点击柱状图查看当月营收报表</i></span>
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