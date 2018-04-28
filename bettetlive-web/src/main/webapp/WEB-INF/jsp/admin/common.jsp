<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<script type="text/javascript">
	var mainServer = '${mainserver}';
    var resourcepath='${resourcepath}';
    var qiniulink = '${qiniulink}';
    window.EDITOR_INIT_SPRING_PATH = mainServer;
    window.UEDITOR_HOME_URL = resourcepath + "/js/ueditor/";
</script>

<link href="${resourcepath}/admin/css/base.css" rel="stylesheet" type="text/css"/>
<link href="${resourcepath}/plugin/custom/uimaker/easyui.css" rel="stylesheet" type="text/css"/>
<link href="${resourcepath}/plugin/custom/uimaker/icon.css" rel="stylesheet" type="text/css"/>
<link href="${resourcepath}/admin/css/platform.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.min.js"></script>
<script type="text/javascript" src="${resourcepath}/plugin/custom/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${resourcepath}/plugin/custom/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/main.js"></script>
<script type="text/javascript" src="${resourcepath}/admin/js/common/toolbar.js"></script>