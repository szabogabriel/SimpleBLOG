package com.simpleblog.web.upload;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jmtemplate.Template;
import com.simpleblog.Main;
import com.simpleblog.utils.InputUtils;
import com.simpleblog.utils.QueryString;
import com.simpleblog.utils.TemplateLoader;
import com.simpleblog.web.AbstractPage;
import com.simpleblog.web.Message;
import com.simpleblog.web.MessageType;
import com.simpleblog.web.Response;

public class Upload extends AbstractPage {
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.load("upload.template");
	
	public void doGet(QueryString qs, Response response) throws IOException {
		doGet(qs, response, Message.NULL_OBJECT);
	}
	
	public void doGet(QueryString qs, Response response, Message message) throws IOException {
		Map<String, Object> data = new HashMap<>();
		
		String locale = getLocale(qs);
		
		data.put("entries", getMenuEntries(new QueryString("")));
		data.put("existingCategories", getCategoriesData(getLocale(qs)));
		data.put("locales", Arrays.asList(Main.INSTANCE.getLocales().getLocaleNames()));
		data.put("current.locale", locale);
		data.putAll(Main.INSTANCE.getLocales().getStrings(locale));
		
		if (message.getType() != MessageType.NONE) {
			data.put(message.getType().getKey(), message.getRenderableData());
		}
		
		String toReturn = TEMPLATE.render(data);
		response.setContentType("text/html");
		response.sendResponse(200, toReturn.length());
		response.getOutputStream().write(toReturn.getBytes());
		response.getOutputStream().close();
	}
	
	public void doPost(QueryString qs, UploadRequest request, Response response) throws IOException {
		Message message = new Message("Ooops!", "The file couldn't be uploaded.", MessageType.ERROR);
		
		try {
			if (isRequestOk(request) && isUserCorrect(request.getUsername(), request.getPassword())) {
				checkCategory(request.getLocale(), request.getCategory());
				Main.INSTANCE.getEntriesManager().createEntry(request.getLocale(), request.getCategory(), request.getFileName(), request.getFile());
				message = new Message("Success!", "The file " + request.getFileName() + "was uploaded successfully.", MessageType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		doGet(qs, response, message);
	}
	
	private boolean isRequestOk(UploadRequest request) {
		return 
			request != null && 
			request.getUsername() != null && 
			request.getPassword() != null && 
			request.getFileName() != null && 
			request.getFile() != null &&
			request.getCategory() != null;
	}
	
	private void checkCategory(String locale, String category) {
		if (!Main.INSTANCE.getEntriesManager().isKnownCategory(locale, category)) {
			Main.INSTANCE.getEntriesManager().createCategory(locale, category);
		}
	}
	
	private List<Map<String, Object>> getCategoriesData(String locale) {
		List<Map<String, Object>> ret = getCategories(locale, true);
		
		return ret;
	}
	
	private boolean isUserCorrect(String uname, String psswd) {
		boolean ret = false;
		
		try {
			ret = 
					InputUtils.isValidEmail(uname) &&
					InputUtils.isValidPassword(psswd) &&
					Main.INSTANCE.getUserManager().isCorrectCredentials(uname, psswd);
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		}
		
		return ret;
	}

}
