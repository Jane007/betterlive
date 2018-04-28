package com.kingleadsw.betterlive.controller.web.systemconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingleadsw.betterlive.biz.SysDictManager;
import com.kingleadsw.betterlive.core.ctl.AbstractWebController;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.page.PageView;
import com.kingleadsw.betterlive.vo.SysDictVo;


/**
 * 系统参数管理关的action
 * 
 * @author
 */
@Controller
@RequestMapping(value = "/admin/sysconfig")
public class SysconfigController extends AbstractWebController {
    private Logger logger = Logger.getLogger(SysconfigController.class);
    
    @Autowired
    private SysDictManager sysDictManager;

    /**
     * 跳转到系统参数页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView sysDictInit(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/systemconfig/sysconfig");
        return mv;
    }

    /**
     * 系统参数列表
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sysDictList")
    public void sysDictList(HttpServletRequest request,HttpServletResponse response) {
        logger.info("开始调用/admin/sysconfig/initParamInfo --->");
        PageData pd = this.getPageData();
        if (pd.get("pageView") == null) {
            PageView pageView = new PageView(30, 1);
            pd.put("pageView", pageView);
        }

        List<SysDictVo> list = sysDictManager.querySysDictListPage(pd);

        this.outEasyUIDataToJson(pd, list, response);
        logger.info("--->结束调用/admin/sysconfig/sysDictList");
    }

    /**
     * 根据id查找字典表
     * @param httpRequest
     * @param response
     * @return
     */
    @RequestMapping(value="/queryDicById",method={RequestMethod.POST},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryDicById(HttpServletRequest httpRequest,HttpServletResponse response){
		String msg = "queryDicById";
		logger.info("/admin/sysconfig/"+msg+" begin");
		String sys_dict_id = httpRequest.getParameter("sys_dict_id");
	    
	    SysDictVo sysDictVo = sysDictManager.querySysDictBysysDictId(Integer.valueOf(sys_dict_id));
		JSONObject json=new JSONObject();
		json.put("sysDictVo", sysDictVo);
		logger.info("--->结束调用/admin/sysconfig/"+msg);
		return json.toString();
	    
	}
    
    /**
     * 系统参数新增，修改
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(HttpServletRequest request,HttpServletResponse response, SysDictVo sysDictVo) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        PageData pd=this.getPageData();
        SysDictVo vo = sysDictManager.selectByCode(pd);

        if (vo != null && vo.getSysDictId()!=sysDictVo.getSysDictId()) {
            result.put("result", "exit");
            result.put("message", "该编码已经存在！");
            return result;
        }
        
        int ret=0;
        if (sysDictVo.getSysDictId() != null) {
        	ret=sysDictManager.updateByPrimaryKey(sysDictVo);
        	
        } else {
        	ret=sysDictManager.insert(sysDictVo);
        }
        if(ret>0){
        	result.put("result","succ");
        	result.put("message", "操作成功！");
        }else{
        	result.put("result","fail");
        	result.put("message", "操作失败！");
        }
        
        return result;
    }
    
    /**
     * 系统参数删除
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response) {
    	logger.info("删除系统参数....开始");
    	
        Map<String, Object> result = new HashMap<String, Object>();
        PageData pd=this.getPageData();
        int sys_dict_id=pd.getInteger("sys_dict_id");
        SysDictVo sysDictVo = sysDictManager.querySysDictBysysDictId(sys_dict_id);
        
        try {
        	sysDictVo.setStatus(-1);
        	sysDictManager.updateByPrimaryKeySelective(sysDictVo);
        	result.put("result", true);
		} catch (Exception e) {
			logger.error("删除系统参数异常....", e);
			
			result.put("result", false);
		}
        
        logger.info("删除系统参数...结束");
        
        return result;
    }

}
