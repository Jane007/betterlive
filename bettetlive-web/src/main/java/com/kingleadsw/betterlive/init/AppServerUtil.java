package com.kingleadsw.betterlive.init;



/**
 * <p>
 * Title: 获取路径公共类
 * </p>
 * <p>
 * Description: 获取路径公共类
 * </p>
 * 
 * @version 1.0.0.0
 */
public class AppServerUtil
{

    private static String webRootPath = "";

    private static String classesPath = "";

    /**
     * 对外公开静态方法，获取应用程序根目录路径
     * 
     * @return
     */
    public static String getWebRoot()
    {
        return webRootPath;
    }

    public static void setWebRootPath(String webRootPath)
    {
        AppServerUtil.webRootPath = webRootPath;
    }

    /**
     * 对外公开静态方法，获取classes路径
     * 
     * @return
     */
    public static String getClassesPath()
    {
        return classesPath;
    }

    public static void setClassesPath(String classesPath)
    {
        AppServerUtil.classesPath = classesPath;
    }

}