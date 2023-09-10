package com.minis.spring;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class TomcatStarter {
    private static int port = 8080;

    private static String contextPath = "/";

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        // 设置工作目录
        String baseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        // 设置端口
        tomcat.setPort(port);
        // 设置contextPath
        tomcat.addWebapp(contextPath, baseDir);
        tomcat.enableNaming();
        tomcat.start();
        // 该tomcat版本在start()时未初始化连接器，需要手动初始化连接器，可能也是为了让用户自己拓展，这里使用默认的
        tomcat.getConnector();
        // 若不想使用默认的，可以自己创建，并调用tomcat.setConnector(connector)
        // Connector connector = new Connector("HTTP/1.1");
        // connector.setPort(port);
        // tomcat.setConnector(connector);
        tomcat.getServer().await();
    }
}
