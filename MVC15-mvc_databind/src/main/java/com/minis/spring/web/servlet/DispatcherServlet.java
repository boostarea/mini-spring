package com.minis.spring.web.servlet;

import com.minis.spring.web.AnnotationConfigWebApplicationContext;
import com.minis.spring.web.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private WebApplicationContext webApplicationContext;
    private WebApplicationContext parentApplicationContext;

    private String sContextConfigLocation;
    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";


    public DispatcherServlet() {
        super();
    }


    //并且当前业务 程序不用再固定写死在 doGet() 方法里面，可以按照自身的业务需求随意使用任何方法名， 也为今后提供多种请求方式，例如 POST、PUT、DELETE 等提供了便利。
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try{
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerMethod handlerMethod = null;
        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }
        HandlerAdapter ha = this.handlerAdapter;
        ha.handle(processedRequest, response, handlerMethod);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //从 sevletContext 里获取属性，拿到 Listener 启动的时候注 册好的 WebApplicationContext
        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        //把 DispatcherServlet 里一部分和扫描包相关的代码移到 AnnotationConfigWebApplicationContext 里
        this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation,
                this.parentApplicationContext);
        Refresh();
    }

    protected void Refresh() {
        //初始化Controller
        //DispatcherServlet中的controller相关bean的初始化已经交给AnnotationConfigWebApplicatio nContext管理了，它的init方法不用在调用initController了
        //initController();
        //初始化URL映射
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
        //initMapping();
    }

    protected void initHandlerMappings(WebApplicationContext wac) {
        this.handlerMapping = new RequestMappingHandlerMapping(wac);
    }

    protected void initHandlerAdapters(WebApplicationContext wac) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(wac);
    }
}
