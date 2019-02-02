package com.simpleblog.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simpleblog.Main;
import com.simpleblog.renderer.RenderedData;
import com.simpleblog.utils.QueryString;

public abstract class PageBase {

	protected List<Map<String, Object>> getMenuEntries(QueryString qs) {
		List<Map<String, Object>> ret = new ArrayList<>();
		String[] categories = Main.INSTANCE.getEntriesManager().getEntryCategories();

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

	protected List<Map<String, Object>> getCategories() {
		return getCategories(false);
	}
	
	protected List<Map<String, Object>> getCategories(boolean startWithEmpty) {
		String[] categories = Main.INSTANCE.getEntriesManager().getEntryCategories();
		List<Map<String, Object>> ret = new ArrayList<>();

		if (startWithEmpty) {
			String [] tmp = new String[categories.length + 1];
			tmp[0] = "";
			System.arraycopy(categories, 0, tmp, 1, categories.length);
			categories = tmp;
		}
		
		for (String it : categories) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("category", it);
			ret.add(tmp);
		}
		
		return ret;
	}

	private List<Map<String, Object>> getEntries(String category) {
		List<Map<String, Object>> ret = new ArrayList<>();
		File[] files = Main.INSTANCE.getEntriesManager().getEntries(category);

		for (File it : files) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("file", it.getName());
			ret.add(tmp);
		}

		return ret;
	}

	protected RenderedData getRenderedEntry(QueryString qs) {
		RenderedData ret = new RenderedData(new byte[] {}, "text/html");
		if (qs.containsKey("category") && qs.containsKey("name")) {
			ret = Main.INSTANCE.getRenderedEntry(qs.getValue("category"), qs.getValue("name"));
		}
		return ret;
	}

}
