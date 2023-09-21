package com.lagou.edu.server1;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * Minicat的主类
 */
public class Bootstrap {
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Minicat启动需要初始化展开的一些操作
     */
    public void start() throws Exception {

        // 加载解析相关配置，web.xml
        loadServlet();

        // 定义一个线程池
        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler);


        // 完成Minicat 1.0版本（浏览器请求http://localhost:8080 , 返回一个固定的字符串道页面）
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=============>>>Minicat start on port: " + port);
        /*while (true) {
            final Socket socket = serverSocket.accept();
            // 有了socket，接受请求
            final OutputStream output = socket.getOutputStream();
            String data = "Hello Minicat!";
            String responseText = HttpProtocolUtil.getHttpHeader200("Hello Minicat!".getBytes().length) + data;
            output.write(responseText.getBytes());
            socket.close();
        }*/

        /**
         * 完成Minicat2.0版本
         * 封装Request和Response对象，返回Html静态资源文件
         *
         */
        /*while (true) {
            Socket socket = serverSocket.accept();
            final InputStream input = socket.getInputStream();

            // 封装Request对象和Response对象

            final Request request = new Request(input);
            final Response response = new Response(socket.getOutputStream());

            response.outputHtml(request.getUrl());

            socket.close();
        }*/

        /**
         * Micat3.0
         * 请求动态资源
         */
        /*while (true) {
            Socket socket = serverSocket.accept();
            final InputStream input = socket.getInputStream();

            // 封装Request对象和Response对象
            final Request request = new Request(input);
            final Response response = new Response(socket.getOutputStream());

            // 静态资源处理
            if (servletMap.get(request.getUrl()) == null) {
                response.outputHtml(request.getUrl());
            } else {
                // 动态资源请求
                final HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request, response);
            }
            socket.close();
        }*/

        /**
         * 多线程改造（不使用线程池）
         */
        /*while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
            requestProcessor.start();
        }*/

        System.out.println("=========>>>>使用线程池进行多线程改造");
        /**
         * 多线程改造，线程池（使用线程池）
         */
        while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
            threadPoolExecutor.execute(requestProcessor);
        }
    }

    private Map<String, HttpServlet> servletMap = new HashMap<>();

    /**
     * 加载解析web.xml，初始化Servlet
     */
    private void loadServlet() {
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        final SAXReader saxReader = new SAXReader();

        try {
            final Document document = saxReader.read(inputStream);
            final Element rootElement = document.getRootElement();
            final List<Element> selectNodes = rootElement.selectNodes("//servlet");
            for (int i = 0; i < selectNodes.size(); i++) {
                final Element element = selectNodes.get(i);
                final Element servletNameElement = (Element) element.selectSingleNode("servlet-name");
                final String servletName = servletNameElement.getStringValue();

                final Element servletclassElement = (Element) element.selectSingleNode("servlet-class");
                final String servletClass = servletclassElement.getStringValue();

                // 根据servlet-name找到url-pattern
                final Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                // /lagou
                final String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 程序启动入口
     *
     * @param args
     */
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            // 启动Minicat　
            bootstrap.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
