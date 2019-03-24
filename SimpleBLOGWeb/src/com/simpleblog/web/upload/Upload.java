package com.simpleblog.web.upload;

import java.io.IOException;
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
	
	public void doGet(Response response) throws IOException {
		doGet(response, Message.NULL_OBJECT);
	}
	
	public void doGet(Response response, Message message) throws IOException {
		Map<String, Object> data = new HashMap<>();
		
		data.put("entries", getMenuEntries(new QueryString("")));
		data.put("existingCategories", getCategoriesData());
		
		if (message.getType() != MessageType.NONE) {
			data.put(message.getType().getKey(), message.getRenderableData());
		}
		
		String toReturn = TEMPLATE.render(data);
		response.setContentType("text/html");
		response.sendResponse(200, toReturn.length());
		response.getOutputStream().write(toReturn.getBytes());
		response.getOutputStream().close();
	}
	
	public void doPost(UploadRequest request, Response response) throws IOException {
		Message message = new Message("Ooops!", "The file couldn't be uploaded.", MessageType.ERROR);
		
		try {
			if (isRequestOk(request) && isUserCorrect(request.getUsername(), request.getPassword())) {
				checkCategory(request.getCategory());
				Main.INSTANCE.getEntriesManager().createEntry(request.getCategory(), request.getFileName(), request.getFile());
				message = new Message("Success!", "The file " + request.getFileName() + "was uploaded successfully.", MessageType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		doGet(response, message);
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
	
	private void checkCategory(String category) {
		if (!Main.INSTANCE.getEntriesManager().isKnownCategory(category)) {
			Main.INSTANCE.getEntriesManager().createCategory(category);
		}
	}
	
	private List<Map<String, Object>> getCategoriesData() {
		List<Map<String, Object>> ret = getCategories(true);
		
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
