package com.simpleblog.web.page;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jmtemplate.Template;
import com.simpleblog.Main;
import com.simpleblog.renderer.RenderableData;
import com.simpleblog.utils.QueryString;
import com.simpleblog.utils.TemplateLoader;
import com.simpleblog.web.AbstractPage;
import com.simpleblog.web.Response;

public class Page extends AbstractPage {
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.load("page.template");

    public Page() {
        super();
    }

	public void doGet(String queryString, Response response) throws IOException {
		Map<String, Object> data = new HashMap<>();
		QueryString qs = new QueryString(queryString);
		
		String locale = getLocale(qs);
		
		data.put("entries", getMenuEntries(qs));
		data.put("locales", Arrays.asList(Main.INSTANCE.getLocales().getLocaleNames()));
		data.putAll(Main.INSTANCE.getLocales().getStrings(locale));
		
		boolean useTemplate = false;
		
		RenderableData renderData = getRenderedEntry(qs);

		if (renderData.isNotEmpty()) {
			useTemplate = renderData.isEmbeddable();
			if (useTemplate) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				renderData.writeData(baos);
				data.put("content", baos.toString());
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
			response.sendResponse(200, renderData.getDataLength());
			renderData.writeData(response.getOutputStream());
			response.getOutputStream().close();
		}
	}
	
	private List<Map<String, Object>> getAvailableEntries(QueryString qs) {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		for (File in : Main.INSTANCE.getEntriesManager().getEntries(getLocale(qs), qs.getValue("category"))) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("entryFile", in.getName());
			tmp.put("entryFileHR", in.getName().substring(0, in.getName().lastIndexOf('.')));
			ret.add(tmp);
		}
		
		return ret;
	}

}
