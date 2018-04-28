package com.kingleadsw.betterlive.core.ctl;

import org.springframework.beans.BeansException;

/**
 * 
 * class Name [WebController].
 * description [控制器的顶层接,用来处理对特定<code>WebComponent</code>的请求的控制器] 
 * @version 1.0
 * @since JDK 1.7
 */
public interface WebController {

    /**
     * 
     * init:(这里用一句话描述这个方法的作用). <br/> 
     * 
     * @author Administrator  
     *
     */
    void init();

    /**
     * 
     * onRefreshContext:(这里用一句话描述这个方法的作用). <br/> 
     * 
     * @author Administrator 
     * @throws BeansException 
     *
     */
    void onRefreshContext() throws BeansException;

    /**
     * 
     * onFinishedProcessContext:(这里用一句话描述这个方法的作用). <br/> 
     * 
     * @author Administrator  
     *
     */
    void onFinishedProcessContext();

//    boolean service(RequestContext requestContext) throws Exception;
    
}
