package com.simpleblog.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jmtemplate.Template;
import com.simpleblog.Main;
import com.simpleblog.renderer.RenderedData;
import com.simpleblog.utils.QueryString;
import com.simpleblog.utils.TemplateLoader;

public class Page extends PageBase {
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.load("page.template");

    public Page() {
        super();
    }

	public void doGet(String queryString, Response response) throws IOException {
		Map<String, Object> data = new HashMap<>();
		QueryString qs = new QueryString(queryString);
		
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
			String respData = TEMPLATE.render(data);
			response.setContentType("text/html");
			response.sendResponse(200, respData.length());
			response.getOutputStream().write(respData.getBytes());
			response.getOutputStream().close();
		} else {
			response.setContentType(renderData.getMimeType());
			response.sendResponse(200, content.length);
			response.getOutputStream().write(content);
			response.getOutputStream().close();
		}
	}
	
	private List<Map<String, Object>> getAvailableEntries(QueryString qs) {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		for (File in : Main.INSTANCE.getEntriesManager().getEntries(qs.getValue("category"))) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("entryFile", in.getName());
			tmp.put("entryFileHR", in.getName().substring(0, in.getName().lastIndexOf('.')));
			ret.add(tmp);
		}
		
		return ret;
	}

}
