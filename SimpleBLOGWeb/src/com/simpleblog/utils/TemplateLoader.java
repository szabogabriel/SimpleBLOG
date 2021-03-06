package com.simpleblog.utils;

import java.io.File;

import com.jmtemplate.Template;
import com.simpleblog.WebConfig;

public enum TemplateLoader {
	
	INSTANCE;
	
	private File templateRoot = null;
	private Boolean reload = null;
	
	public Template load(String template) {
		return new Template(getTemplateRoot(), template, getReload());		
	}
	
	private File getTemplateRoot() {
		if (templateRoot == null) {
			templateRoot = new File(WebConfig.DIR_TEMPLATES.toString());
		}
		return templateRoot;
	}
	
	private Boolean getReload() {
		if (reload == null) {
			reload = Boolean.parseBoolean(WebConfig.TEMPLATES_RELOAD.toString());
		}
		return reload;
	}

}
