//每次webview重新打开H5首页，就把server time记录本地存储
	var SERVER_TIME = document.getElementById("SERVER_TIME"); 
	var REMOTE_VER = SERVER_TIME && SERVER_TIME.value;
	if(REMOTE_VER){
	    var LOCAL_VER = sessionStorage && sessionStorage.PAGEVERSION;
	    if(LOCAL_VER && parseInt(LOCAL_VER) >= parseInt(REMOTE_VER)){
	        //说明html是从本地缓存中读取的
	        location.reload(true);
	    }else{
	        //说明html是从server端重新生成的，更新LOCAL_VER
	        sessionStorage.PAGEVERSION = REMOTE_VER;
	    }
	}