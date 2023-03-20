package com.lagou.edu.server;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Minicat主类
 */
public class BootStrap {

    /**
     * 定义socket监听端口
     */
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * MiniCat启动需要初始化展开的一些操作
     */
    public void start() throws Exception {
        // 完成minicat1.0， 浏览器请求http://localhost:8080, 返回一个固定的字符串到页面"Hello world"
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("------->>>>minicat start on port: " + port);
/*        while (true) {
            final Socket socket = serverSocket.accept();
            final OutputStream outputStream = socket.getOutputStream();
            String data = "Hello Minicat!";
            String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
            outputStream.write(responseText.getBytes());
            socket.close();
        }*/
        /*
         * MiniCat2.0版本
         * 封装request和response对象，返回html静态资源文件
         */
//        while (true) {
//            Thread.sleep(10000);
//            final Socket sock = serverSocket.accept();
//            final InputStream inputStream = sock.getInputStream();
//            // 封装Request
//            final Request request = new Request(inputStream);
//            final Response response = new Response(sock.getOutputStream());
//            response.outputHtml(request.getUrl());
//            sock.close();
//        }

        // 加载解析相关的配置 web.xml
        loadServlet();
        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);

        /*
         * MiniCat3.0版本
         * 请求动态资源
         */
//        while (true) {
//            final Socket sock = serverSocket.accept();
//            final InputStream inputStream = sock.getInputStream();
//            // 封装Request
//            final Request request = new Request(inputStream);
//            final Response response = new Response(sock.getOutputStream());
//
//            final HttpServlet httpServlet = servletMap.get(request.getUrl());
//            if (httpServlet == null) {
//                response.outputHtml(request.getUrl());
//            } else {
//                httpServlet.service(request, response);
//            }
//            sock.close();
//        }

        /*
         *多线程改造
         * */
//        while (true) {
//            final Socket sock = serverSocket.accept();
//            final RequestProcessor requestProcessor = new RequestProcessor(sock, servletMap);
//            requestProcessor.start();
//        }
        // 使用线程池
        while (true) {
            System.out.println("======>>线程池进行多线程改造");
            final Socket sock = serverSocket.accept();
            final RequestProcessor requestProcessor = new RequestProcessor(sock, servletMap);
            //requestProcessor.start();
            threadPoolExecutor.execute(requestProcessor);
        }
    }

    private Map<String, HttpServlet> servletMap = new HashMap<>();

    /**
     * 加载解析web.xml 初始化Servlet
     */
    private void loadServlet() {
        final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        final SAXReader saxReader = new SAXReader();
        try {
            final Document document = saxReader.read(resourceAsStream);
            final Element rootElement = document.getRootElement();
            final List<Element> list = rootElement.selectNodes("//servlet");
            for (int i = 0; i < list.size(); i++) {
                final Element element = list.get(i);
                final Element serlvetNameElement = (Element) element.selectSingleNode("servlet-name");
                String servletName = serlvetNameElement.getStringValue();

                final Element serlvetClassElement = (Element) element.selectSingleNode("servlet-class");
                String servletClass = serlvetClassElement.getStringValue();

                // 根据Servlet-name的值找到url-pattern
                final Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                final String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * miniCat 的程序启动入口
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        BootStrap bootStrap = new BootStrap();
        try {
            bootStrap.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
