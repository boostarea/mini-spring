package com.minis.spring.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WebApplicationContext webApplicationContext;
    private String sContextConfigLocation;
    private List<String> packageNames = new ArrayList<>();
    private Map<String,Object> controllerObjs = new HashMap<>();
    private List<String> controllerNames = new ArrayList<>();
    private Map<String,Class<?>> controllerClasses = new HashMap<>();

	/**
	 * @RequestMapping名称列表
	 */
	//三个mapping正好提供了反射的三个条件
	private List<String> urlMappingNames = new ArrayList<>();
    private Map<String,Object> mappingObjs = new HashMap<>();
    private Map<String,Method> mappingMethods = new HashMap<>();

    public DispatcherServlet() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
		//从 sevletContext 里获取属性，拿到 Listener 启动的时候注 册好的 WebApplicationContext
		this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        
        URL xmlPath = null;
		try {
			xmlPath = this.getServletContext().getResource(sContextConfigLocation);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        //写入packageNames
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        Refresh();
    }
    
    protected void Refresh() {
		//初始化Controller
    	initController();
		//初始化URL映射
    	initMapping();
    }

	/**
	 *对扫描到的每一个类进行加载和实例化，与类的 名字建立映射关系
	 */
	protected void initController() {
    	this.controllerNames = scanPackages(this.packageNames);
    	for (String controllerName : this.controllerNames) {
    		Object obj = null;
    		Class<?> clz = null;

			try {
				clz = Class.forName(controllerName);
				this.controllerClasses.put(controllerName,clz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				obj = clz.newInstance();
				this.controllerObjs.put(controllerName, obj);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
    	}
    }
    
    private List<String> scanPackages(List<String> packages) {
    	List<String> tempControllerNames = new ArrayList<>();
    	for (String packageName : packages) {
    		tempControllerNames.addAll(scanPackage(packageName));
    	}
    	return tempControllerNames;
    }
    
    private List<String> scanPackage(String packageName) {
    	List<String> tempControllerNames = new ArrayList<>();
		//将以.分隔的包名换成以/分隔的uri
        URL url  =this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
			//对子目录递归扫描
            if(file.isDirectory()){
            	scanPackage(packageName+"."+file.getName());
            }else{
                String controllerName = packageName +"." +file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }
    
    protected void initMapping() {
    	for (String controllerName : this.controllerNames) {
    		Class<?> clazz = this.controllerClasses.get(controllerName);
    		Object obj = this.controllerObjs.get(controllerName);
    		Method[] methods = clazz.getDeclaredMethods();
    		if(methods!=null){
    			for(Method method : methods){
    				boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
    				if (isRequestMapping){
    					String methodName = method.getName();
    					String urlmapping = method.getAnnotation(RequestMapping.class).value();
    					this.urlMappingNames.add(urlmapping);
    					this.mappingObjs.put(urlmapping, obj);
    					this.mappingMethods.put(urlmapping, method);
    				}
    			}
    		}
    	}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sPath = request.getServletPath();
System.out.println(sPath);		
		if (!this.urlMappingNames.contains(sPath)) {
			return;
		}
		
		Object obj = null;
		Object objResult = null;
		try {
			Method method = this.mappingMethods.get(sPath);
			obj = this.mappingObjs.get(sPath);
			objResult = method.invoke(obj);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append(objResult.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
