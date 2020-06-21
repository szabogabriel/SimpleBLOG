package com.simpleblog.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simpleblog.CoreConfig;
import com.simpleblog.Main;
import com.simpleblog.renderer.RenderableData;
import com.simpleblog.utils.QueryString;

public abstract class AbstractPage {

	protected List<Map<String, Object>> getMenuEntries(QueryString qs) {
		List<Map<String, Object>> ret = new ArrayList<>();
		String locale = getLocale(qs);
		String[] categories = Main.INSTANCE.getEntriesManager().getEntryCategories(locale);

		for (String it : categories) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("category", it);
			tmp.put("files", getEntries(locale, it));
			if (qs.containsKey("category") && qs.getValue("category").equals(it)) {
				tmp.put("active", Boolean.TRUE);
			}
			ret.add(tmp);
		}

		return ret;
	}
	
	protected String getLocale(QueryString qs) {
		String locale = CoreConfig.LOCALE_DEFAULT.toString();
		if (qs.containsKey("locale")) {
			String tmp = qs.getValue("locale");
			if (Main.INSTANCE.getLocales().isKnownLocale(tmp)) {
				locale = tmp;
			}
		}
		return locale;
	}

	protected List<Map<String, Object>> getCategories(String locales) {
		return getCategories(locales, false);
	}
	
	protected List<Map<String, Object>> getCategories(String locales, boolean startWithEmpty) {
		String[] categories = Main.INSTANCE.getEntriesManager().getEntryCategories(locales);
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

	private List<Map<String, Object>> getEntries(String locale, String category) {
		List<Map<String, Object>> ret = new ArrayList<>();
		File[] files = Main.INSTANCE.getEntriesManager().getEntries(locale, category);

		for (File it : files) {
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("file", it.getName());
			ret.add(tmp);
		}

		return ret;
	}

	protected RenderableData getRenderedEntry(QueryString qs) {
		RenderableData ret = new RenderableData("", "text/html", false);
		if (qs.containsKey("category") && qs.containsKey("name")) {
			ret = Main.INSTANCE.getRenderedEntry(getLocale(qs), qs.getValue("category"), qs.getValue("name"));
		}
		return ret;
	}
	
}
