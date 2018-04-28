package com.kingleadsw.betterlive.util;

import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.dao.CustomerAgentMapper;
import com.kingleadsw.betterlive.init.StringUtil;
import com.kingleadsw.betterlive.model.CustomerAgent;

/**
 * 代理工具类
 * @author Administrator
 *
 */
public class AgentUtil {
	
	/**
	 * 生成代理标识
	 * @return
	 */
	public static String createAgentCode(){
		String code = StringUtil.genCodes(6, 1).get(0);
		return code;
	}
	
	public static String getAgentCode(CustomerAgentMapper mapper){
		String code = AgentUtil.createAgentCode();
		PageData checkCodeParam = new PageData();
		checkCodeParam.put("agentCode", code);
		CustomerAgent checkAgentCode = mapper.queryOne(checkCodeParam);
		if(checkAgentCode != null){ //code已存在
			code = createAgentCode();
		}
		return code;
	}
}
