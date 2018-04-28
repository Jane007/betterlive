$.extend($.fn.datagrid.methods, {
	
	/**
	 * 添加 按钮
	 * @param jq
	 * @param items
	 * @returns
	 */
	addToolbarItem : function (jq, items) {
		return jq.each(function () {
			var dpanel = $(this).datagrid('getPanel');
			var toolbar = dpanel.children("div.datagrid-toolbar");
			if (!toolbar.length) {
				toolbar = $("<div class=\"datagrid-toolbar\"><table cellspacing=\"0\" cellpadding=\"0\"><tr></tr></table></div>").prependTo(dpanel);
				$(this).datagrid('resize');
			}
			var tr = toolbar.find("tr");
			for (var i = 0; i < items.length; i++) {
				var btn = items[i];
				if (btn == "-") {
					$("<td><div class=\"datagrid-btn-separator\"></div></td>").appendTo(tr);
				} else {
					var td = $("<td></td>").appendTo(tr);
					var b = $("<a href=\"javascript:void(0)\"></a>").appendTo(td);
					b[0].onclick = eval(btn.handler || function () {});
					b.linkbutton($.extend({}, btn, {
							plain : true
						}));
				}
			}
		});
	},
    
    /**
     * 根据按钮ID删除
     * @param jq
     * @param param
     * @returns
     */
	removeToolbarItem : function (jq, param) {
		return jq.each(function () {
			var dpanel = $(this).datagrid('getPanel');
			var toolbar = dpanel.children("div.datagrid-toolbar");
			var cbtn = null;
			if (typeof param == "number") {
				cbtn = toolbar.find("td").eq(param).find('span.l-btn-text');
			} else if (typeof param == "string") {
				//cbtn = toolbar.find("span.l-btn-text:contains('" + param + "')");
				
				cbtn = toolbar.find("#"+param);
				cbtnSep=cbtn.closest('td').prev();
			}
			if (cbtn && cbtn.length > 0) {
				cbtn.closest('td').remove();
				cbtnSep.remove();
				cbtn = null;
			}
		});
	},
    
    /**
     * 设置按钮为不可用状态
     * @param jq
     * @param param
     * @returns
     */
    disableToolbarBtn : function(jq, param) {
        return jq.each(function() { 
            var btns = $(this).parent().prev("div.datagrid-toolbar").find("a");
            if(typeof param == "string"){
                var text = null;
                btns.each(function() {
                    text = $(this).data().linkbutton.options.id;
                    if(text == param) {
                    	$('#'+text).linkbutton('disable');
                        return;
                    }
                });
            }
        });
    },
    
    /**
     * 设置 按钮为可用状态
     * @param jq
     * @param param
     * @returns
     */
    enableToolbarBtn : function(jq, param) {
        return jq.each(function() {
            var btns = $(this).parent().prev("div.datagrid-toolbar").find("a");
            if(typeof param == "string"){
                var text = null;
                btns.each(function() {
                    text = $(this).data().linkbutton.options.id;
                    if(text == param) {
                    	$('#'+text).linkbutton('enable');
                        return;
                    }
                });
            }
        });
    }
});

function enableBtn(object, id) {
	object.find('#'+id).linkbutton('enable');
}
function disableBtn(object, id) {
	object.find('#'+id).linkbutton('disable');
}

/*在弹出panel之前移除表格按钮焦点*/
function preventKeyboard(){
	$('.datagrid-toolbar  a').each(function(){
		$(this).keydown(function(){return false;});
	});
}