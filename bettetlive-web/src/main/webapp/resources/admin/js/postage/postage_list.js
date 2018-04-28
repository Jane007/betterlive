var boxDataGrid;
	    $(function(){
	    	boxDataGrid = StoreGrid.createGrid('StoreGrid');
	    });
     	
	    function searchPostage(){
	    	$('#StoreGrid').datagrid('load', { 
	    		"postageName":$("#postageName").val(),
	    		"postageType":$("#postageType").val()
	    	});
		} 
	    
	    var StoreGrid = {
    		createGrid:function(grId){
    			return $('#'+grId).datagrid({
    				url : mainServer+'/admin/postage/queryList',
    				rownumbers:true,
                    singleSelect:false,
                    pagination:true,
                    autoRowHeight:true,
                    fit:true,
                    fitColumns : true, // 自动使列适应表格宽度以防止出现水平滚动。
                    striped:true,
                    checkOnSelect:true, // 点击某一行时，则会选中/取消选中复选框
                    selectOnCheck:true, // 点击复选框将会选中该行
                    collapsible:true,
                    showFooter:true,
                    nowrap:false,
    				columns : [ [ {
                        title:"序号",
                        field:"postageId",
                        checkbox: true,
                        width:30
                    },{
                        title:"运费模板名称",
                        field:"postageName",
                        width:100
                    },{
                        title:"模板类型",
                        field:"postageType",
                        width:50,
                        formatter:function(value, row){
                        	switch (value) {
							case 1:
								return "价格类";
								break;
							default:
								return "未知类型";
								break;
							}
                        }
                    },{
                        title:"包邮的条件",
                        field:"meetConditions",
                        width:40
                    },
                    {
                        title:"运费价格",
                        field:"postage",
                        width:40
                    },
                    {
                        title:"详情描述",
                        field:"postageMsg",
                        width:40
                    },{
                        title:"匹配商品",
                        field:"productIds",
                        width:40,
                        formatter:function(value, row){
                        	if (null == value || "" == value) {
								return "未匹配任何商品";
							}else{
								return "已匹配商品";
							}
                        }
                    },{
                        title:"选择商品匹配",
                        field:"do",
                        width:50,
                        formatter : function(value, row) {
                        	return '<a href="javascript:clickDetailedEntity('
							+ row.postageId+',\''+row.productIds
							+ '\');" style="color:blue;">匹配商品</a>';
						}
                    }
                    ]],
    				toolbar : '#storeToolbar',
    				onBeforeLoad : function(param) {
    					$('#StoreGrid').datagrid('clearSelections');
    				},
    				onLoadSuccess:function(data){
    					initCUIDBtn();
    				},
    				onSelect:function(rowIndex, rowData){
    					initCUIDBtn();
    				},
    				onUnselect:function(rowIndex, rowData){
    					initCUIDBtn();
    				}
    			});
    		}
    	};
	    
	   	// 查看详细、添加、修改、删除按钮状态同步刷新
     	function initCUIDBtn() {
    		var rows = boxDataGrid.datagrid('getSelections');
    		if(rows.length > 1){// 多行情况
    			
    			boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
    			boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
    		}else if(rows.length == 1){
    			boxDataGrid.datagrid("enableToolbarBtn", 'delConfBtn');
    			boxDataGrid.datagrid("enableToolbarBtn", 'updConfBtn');
    		}else if(rows.length == 0){
    			boxDataGrid.datagrid("disableToolbarBtn", 'delConfBtn');
    			boxDataGrid.datagrid("disableToolbarBtn", 'updConfBtn');
    		}
    	} 

// 新增运费
	   	function toAddPostage(){
	   		if (null != postageString && postageString != '') {
				alert("目前仅支持配置一条运费模板");
				return;
			}
	   		var centerTabs = parent.centerTabs;
			var url = mainServer+"/admin/postage/toAddPostage";
			if (centerTabs.tabs('exists', '添加运费')) {
				centerTabs.tabs('select', '添加运费');
				var tab = centerTabs.tabs('getTab', '添加运费');
				var option = tab.panel('options');
				option.content = '<iframe class="page-iframe" src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
				centerTabs.tabs('update', {
					tab : tab,
					options : option
				});
			} else {
				centerTabs.tabs('add', {
	    			title : '添加运费',
	    			closable : true,
	    			content : '<iframe class="page-iframe" src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
	    		});
			}
	   	}
	
		// 删除运费
	   	function delPostage(){
			var objectArray = boxDataGrid.datagrid('getSelections');
			
			var postageIdArray = "";
			$.each(objectArray,function(i,obj){
				postageIdArray+=obj.postageId+",";
			});
			if(postageIdArray==''){
				$.messager.alert('提示',"请先选择运费",'error');
				return false;
			}else{
				if(postageIdArray.lastIndexOf(",")!=-1){
					postageIdArray=postageIdArray.substring(0,postageIdArray.lastIndexOf(","));
				}
			}
			
			$.messager.confirm("提示","你确认要删除吗?",function(r){
    			if(r){
    				$.ajax({
    					url:mainServer+"/admin/postage/delPostage",
    					data:{
    						"postageIdArray":postageIdArray
    					},
    					datatype:"json",
    					type:"post",
    					success:function(data){
    	                    if(data.result=='succ'){
    	                    	window.location.reload();
    	                    }else{
    	                    	$.messager.alert('提示',obj.msg,'error');
    	                    }
    						
    					}
    						
    				});
    			}
    		});
		}
	
		// 修改运费
	   	function toEditPostage() {
	   		var centerTabs = parent.centerTabs;
	   		var postage_id = boxDataGrid.datagrid('getSelected').postageId;
	   	    if(postage_id ==''){
	   	    	$.messager.alert('提示消息','<div style="position:relative;top:20px;">请选择要修改的记录</div>','error');
	   	    	return;
	   	    }
	   		var url = mainServer+"/admin/postage/toUpdatePostage?postageId="+postage_id;
	   		if (centerTabs.tabs('exists', '修改运费信息')) {
	   			centerTabs.tabs('select', '修改运费信息');
	   			var tab = centerTabs.tabs('getTab', '修改运费信息');
	   			var option = tab.panel('options');
	   			option.content = '<iframe class="page-iframe" src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
	   			centerTabs.tabs('update', {
	   				tab : tab,
	   				options : option
	   			});
	   		} else {
	   			centerTabs.tabs('add', {
	       			title : '修改运费信息',
	       			closable : true,
	       			content : '<iframe class="page-iframe" src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;" scrolling="auto"></iframe>'
	       		});
	   		}
	   	}
		
	   	function addCreateProduct(obj){
			  if($(obj).attr("class")=="waiteTea"){
			   	   $(obj).attr("class","chooseTea");
			   }else{
				   $(obj).attr("class","waiteTea");
			   }
		}
		
	   	function closeProduct(){
			   $("span[class='chooseTea']").each(function(i,ele){
				   $(this).attr("class","waiteTea");
			   });
			   $("span").each(function(i,ele){
				   $(this).show();
			   });
			   $("#postageId").val("");
			   $("#dlg").dialog("close");
		   }
	   	
	   	function clickDetailedEntity(postageId,productIds) {
	   		   closeProduct();
	   		   $("#postageId").val(postageId);
			   if(""!=productIds && null!=productIds){
				   var array=new Array();
				   array=productIds.split(",");
				 	
				   for (var i= 0; i< array.length; i++) {
					  $("#"+array[i]).attr("class","chooseTea");
				   }
			   }
			   
			   var postageMap = new Map();
			   var postageList = new Array();
			   
			   var screening = "";
			   if(postageString.lastIndexOf("|")!=-1){
				   postageString=postageString.substring(0,postageString.lastIndexOf("|"));
				}
			   postageList = postageString.split("|");
			   for (var i = 0; i < postageList.length; i++) {
				   postageMap.set(postageList[i].substring(0,postageList[i].lastIndexOf("=")),postageList[i].substring((postageList[i].lastIndexOf("=")+1),postageList[i].length));
			}
			   for (var [key,value] of postageMap){
				   if (key != postageId) {
					  screening = screening+value+",";
					}
				}
			   if(postageString.lastIndexOf("|")!=-1){
			  	 screening=screening.substring(0,screening.lastIndexOf(","));
			   }
			   if(""!=screening && null!=screening){
				   var arraySrc=new Array();
				   arraySrc=screening.split(",");
				 	
				   for (var i= 0; i< arraySrc.length; i++) {
					  $("#"+arraySrc[i]).hide();
				   }
			   }
			   
	   		$("#dlg").dialog("open");
	   	}
	   	
	   	function isSureProduct(){
			   var chooseProductIds=",";
			   $("span[class='chooseTea']").each(function(i,ele){
				   chooseProductIds+=$(this).attr("id")+",";
			   });
			   
			  /*
				 * if(chooseProductIds.lastIndexOf(",")){
				 * chooseProductIds=chooseProductIds.substr(0,chooseProductIds.lastIndexOf(",")); }
				 */

			   $.ajax({
					url:mainServer+"/admin/postage/editPostage",
					data:{
						"postageId":$("#postageId").val(),
						"chooseProductIds":chooseProductIds
					},
					datatype:"json",
					type:"post",
					success:function(data){
	                    if(data!=null&&''!=data){
	                    	if ("succ" == data.result) {
	                    		$.messager.alert('提示','分配商品成功',data.result);
							}else{
								$.messager.alert('提示',data.msg,data.result);
							}
	                    	
	                    }else{
	                    	$.messager.alert('提示','分配商品失败','error');
	                    }
	                    closeProduct();
	                    window.location.reload();
					}
						
				});
	   	}