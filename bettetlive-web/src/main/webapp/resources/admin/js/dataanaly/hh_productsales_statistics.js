var StringBuffer = Application.StringBuffer;
		var buffer = new StringBuffer();
    	var boxDataGrid;
	    $(function(){
	    	
	    	boxDataGrid = dataGrid.createGrid('dataGrid');
	    	
	    });
	    
	   function queryList(){
		    var startTime = $("input[name=startTime]").val();
	    	var endTime = $("input[name=endTime]").val();
	   		if(startTime>endTime){
	   			alert("开始时间不能大于结束时间");
	   			return false;
	   		}
	    	$('#dataGrid').datagrid('load', { 
	    		"productName":$("#productName").val(),
	    		"startTime":startTime,
	    		"endTime":endTime
	    	});
		}
	   
	   function exportExcel() {
			var startTime = $("input[name=startTime]").val();
			var endTime = $("input[name=endTime]").val();
			var productName = $("#productName").val();
			location.href = mainServer+"/admin/orderanaly/exportExcelhhPro?startTime="+startTime+"&endTime="+endTime+"&productName="+productName;
		}
	   
    	var dataGrid = {
    		createGrid:function(grId){
    			return $('#'+grId).datagrid({
    				url : mainServer+'/admin/orderanaly/hhQueryProductSales',
    				rownumbers:true,
                    singleSelect:false,
                    pagination:true,
                    autoRowHeight:true,
                    fit:true,
                    fitColumns : true, //自动使列适应表格宽度以防止出现水平滚动。
                    striped:true,
                    checkOnSelect:true, //点击某一行时，则会选中/取消选中复选框
                    selectOnCheck:true, //点击复选框将会选中该行
                    collapsible:true,
    				columns : [ [ {
                        title:"商品名称",
                        field:"productName",
                        width:100
                    },{
                        title:"商品销量",
                        field:"quantity",
                        width:100
                    },{
                        title:"商品金额",
                        field:"totalPrice",
                        width:100
                    },{
                        title:"实销金额",
                        field:"totalPay",
                        width:100
                    },{
                        title:"详细列表",
                        field:"xiangXi",
                        width:100,
                        formatter: function(value, row) {
                        	return '<a href="javascript:clickDetailedEntity(\''+row.productId+'\');" style="color:blue;">查看</a>';
                         }
                    }
                    ]],
    				toolbar : '#dataToolbar',
    				onBeforeLoad : function(param) {
    					$('#dataGrid').datagrid('clearSelections');
    				},
    				onLoadSuccess:function(data){
//     					initCUIDBtn();
    				},
    				onSelect:function(rowIndex, rowData){
//     					initCUIDBtn();
    				},
    				onUnselect:function(rowIndex, rowData){
//     					initCUIDBtn();
    				},
    				onCheckAll:function(rowIndex, rowData){
//     					initCUIDBtn();
    				}
    				
    				
    				
    			});
    		}
    	};
    	function clickDetailedEntity(value){
  		    var startTime = $("input[name=startTime]").val();
  	    	var endTime = $("input[name=endTime]").val();
  	    	
  	    	var centerTabs = parent.centerTabs;
      		var url =mainServer+"/admin/order/findList?productId="+value+"&startTime="+startTime+"&endTime="+endTime;
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