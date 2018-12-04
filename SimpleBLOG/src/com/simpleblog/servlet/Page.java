package com.simpleblog.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmtemplate.Template;
import com.simpleblog.Main;
import com.simpleblog.renderer.RenderedData;
import com.simpleblog.utils.QueryString;
import com.simpleblog.utils.TemplateLoader;

@WebServlet("/page")
public class Page extends HttpServlet {
	private static final long serialVersionUID = -2218888967722431724L;
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.load("page.template");

    public Page() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> data = new HashMap<>();
		QueryString qs = new QueryString(request.getQueryString());
		
		data.put("entries", getMenuEntries(qs));
		
		boolean useTemplate = false;
		
		RenderedData renderData = getRenderedEntry(qs);
		byte[] content = renderData.getData();
		if (content.length > 0) {
			useTemplate = renderData.getMimeType() != null && renderData.getMimeType().startsWith("text");
			if (useTemplate) {
				data.put("content", new String(content));
			} else {
				data.put("content", content);
			}
		} else {
			useTemplate = true;
			if (qs.containsKey("category")) {
				data.put("entriesList", getAvailableEntries(qs));
				data.put("currentCategory", qs.getValue("category"));
				data.put("showEntriesList", Boolean.TRUE);
			} else {
				data.put("showIndex", Boolean.TRUE);
			}
		}
		
		if (useTemplate) {
			response.setContentType("text/html");
			response.getWriter().append(TEMPLATE.render(data));
		} else {
			response.setContentType(renderData.getMimeType());
			response.getOutputStream().write(content);
		}
	}
	
	private List<Map<String, Object>> getMenuEntries(QueryString qs) {
		List<Map<String, Object>> ret = new ArrayList<>();
		String[] categories = Main.INSTANCE.getEntriesLoader().getEntryCategories();
		
		for (String it : categories) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("category", it);
			tmp.put("files", getEntries(it));
			if (qs.containsKey("category") && qs.getValue("category").equals(it)) {
				tmp.put("active", Boolean.TRUE);
			}
			ret.add(tmp);
		}
		
		return ret;
	}
	
	private List<Map<String, Object>> getEntries(String category) {
		List<Map<String, Object>> ret = new ArrayList<>();
		File [] files = Main.INSTANCE.getEntriesLoader().getEntries(category);
		
		for (File it : files) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("file", it.getName());
			ret.add(tmp);
		}
		
		return ret;
	}
	
	private RenderedData getRenderedEntry(QueryString qs) {
		RenderedData ret = new RenderedData(new byte [] {}, "text/html");
		if (qs.containsKey("category") && qs.containsKey("name")) {
			ret = Main.INSTANCE.getRenderedEntry(qs.getValue("category"), qs.getValue("name"));
		}
		return ret;
	}
	
	private List<Map<String, Object>> getAvailableEntries(QueryString qs) {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		for (File in : Main.INSTANCE.getEntriesLoader().getEntries(qs.getValue("category"))) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("entryFile", in.getName());
			tmp.put("entryFileHR", in.getName().substring(0, in.getName().lastIndexOf('.')));
			ret.add(tmp);
		}
		
		return ret;
	}

}
