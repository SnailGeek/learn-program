package com.snail.controller;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Servlet implementation class GetOpenId
 */
@WebServlet("/GetOpenId")
public class GetOpenId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOpenId() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub  GetOpenId
		
		response.setContentType("text/html;charset=utf-8");
        /*设置响应头允许ajax跨域访问*/
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		/* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        
        
      //获取微信小程序get的参数值并打印
        String code = request.getParameter("code");
     
        //参数
        String appid = "wxe58dae72fd9e34b9";//自己找自己的
        // 记得发给别人前删掉这个
        String secret = "3e4f02e7e369a2ad319f9785e7678bdc";//自己找自己的
        
        // 调用方位微信服务器工具方法 
        
 
		GetOpenIdUtil getopenid=new GetOpenIdUtil();
		//调用访问微信服务器工具方法，传入三个参数获取带有openid、session_key的json字符串
		String jsonId=getopenid.getopenid(appid,code,secret);
		JSONObject jsonObject = JSONObject.parseObject(jsonId);
		//从json字符串获取openid和session_key
		String openid=jsonObject.getString("openid");
		String session_key=jsonObject.getString("session_key");
		
		System.out.println("openid"+openid);
		
		//返回值给微信小程序
		response.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        //out.write("进入后台了"+json);
        
        out.write(jsonObject.toString());
        out.flush();
 
		
		
	}
 
}