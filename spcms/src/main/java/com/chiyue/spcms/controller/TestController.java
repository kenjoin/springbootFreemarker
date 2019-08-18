package com.chiyue.spcms.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
public class TestController {
	
	@Autowired
    private Configuration cfg;
	
	@RequestMapping("/t1")
	public Object test(Model model) {

		Map<String, Object> map = new HashMap<>();
		map.put("value1", "I'm Ken. 周昆");
		
		freeMarkerContent(map);
		
		model.addAttribute("value1", "I'm Ken. 周昆");
		return "test";
	}

	/**
	 * 生成html静态文件
	 * @param root
	 */
	private void freeMarkerContent(Map<String, Object> root) {
		Writer file = null;
		try {
			cfg.setDefaultEncoding("utf-8");
			cfg.setOutputEncoding("utf-8");
			Template template = cfg.getTemplate("test.ftl");
			// 以classpath下面的static目录作为静态页面的存储目录，同时命名生成的静态html文件名称
			String path = this.getClass().getResource("/").toURI().getPath() + "static/test.html";
			File outputFile = new File(path.substring(path.indexOf("/")));
			if(!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}
			file =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
			template.process(root, file);
			System.out.println(outputFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(file!=null) {
					file.flush();
					file.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
