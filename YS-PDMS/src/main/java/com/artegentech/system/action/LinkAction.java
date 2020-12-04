package com.artegentech.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONObject;

import java.io.*;
import java.util.*;

@Controller
@ResponseBody
@RequestMapping("link/*")
public class LinkAction extends AbstractAction {
	
	@RequiresUser
	@RequestMapping("images")
	public List<String> images(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getParameter("path");
		String filePath = request.getServletContext().getRealPath(path);
		
        File file=new File(filePath);
        File[] tempList = file.listFiles();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < tempList.length; i++) {
        	if(tempList[i].isFile()) {
        		//递归：
        		//getList(tempList[i].getPath());
        		 list.add(tempList[i].getPath());
        	};
        }
        return  list;
    }
	
	@RequiresUser
	@RequestMapping("images2")
	public JSONObject images2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getParameter("path");
		String logdate = request.getParameter("logdate");
		String no = request.getParameter("no");
		String filePath = request.getServletContext().getRealPath(path);
		
        File file=new File(filePath);
        File[] tempList = file.listFiles();
        List<String> list = new ArrayList<String>();
        if(tempList != null) {
        	for (int i = 0; i < tempList.length; i++) {
            	if(tempList[i].isFile()) {
            		//递归：
            		//getList(tempList[i].getPath());
            		 list.add(tempList[i].getPath());
            	};
            }
        }
        
        Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("list", list);
		map2.put("logdate", logdate);
		map2.put("no", no);
		JSONObject json3 = JSONObject.fromObject(map2);
		return json3;
    }
	
	
	@RequiresUser
	@RequestMapping("images3")
	public JSONObject images3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getParameter("path");
		String no = request.getParameter("no");
		String filePath = request.getServletContext().getRealPath(path);
		
        File file=new File(filePath);
        File[] tempList = file.listFiles();
        List<String> list = new ArrayList<String>();
        if(tempList != null) {
        	for (int i = 0; i < tempList.length; i++) {
            	if(tempList[i].isFile()) {
            		//递归：
            		//getList(tempList[i].getPath());
            		 list.add(tempList[i].getPath());
            	};
            }
        }
        
        Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("list", list);
		map2.put("no", no);
		JSONObject json3 = JSONObject.fromObject(map2);
		return json3;
    }

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}