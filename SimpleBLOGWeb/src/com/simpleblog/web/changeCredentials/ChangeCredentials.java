package com.simpleblog.web.changeCredentials;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.jmtemplate.Template;
import com.simpleblog.Main;
import com.simpleblog.users.UserManager;
import com.simpleblog.utils.InputUtils;
import com.simpleblog.utils.QueryString;
import com.simpleblog.utils.TemplateLoader;
import com.simpleblog.web.AbstractPage;
import com.simpleblog.web.Message;
import com.simpleblog.web.MessageType;
import com.simpleblog.web.Response;

public class ChangeCredentials extends AbstractPage {
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.load("changeCredentials.template");
	
	public void doGet(Response response) throws IOException {
		doGet(response, Message.NULL_OBJECT);
	}
	
	public void doGet(Response response, Message message) throws IOException {
		Map<String, Object> data = new HashMap<>();
		
		data.put("entries", getMenuEntries(new QueryString("")));
		
		if (message.getType() != MessageType.NONE) {
			data.put(message.getType().getKey(), message.getRenderableData());
		}
		
		String toReturn = TEMPLATE.render(data);
		response.setContentType("text/html");
		response.sendResponse(200, toReturn.length());
		response.getOutputStream().write(toReturn.getBytes());
		response.getOutputStream().close();
	}
	
	public void doPost(ChangeCredentialsRequest request, Response response) throws IOException {
		Message message = new Message("Ooops!", "The credentials were not correct.", MessageType.ERROR);
		
		try {
			UserManager userManager = Main.INSTANCE.getUserManager();
			
			String uname = request.getUsername();
			String oldPsswd = request.getOldPassword();
			String newPsswd = request.getNewPassword();
			
			boolean isSanityCheckOk = InputUtils.isValidEmail(uname) && InputUtils.isValidPassword(oldPsswd) && InputUtils.isValidPassword(newPsswd);
			boolean isCorrect = userManager.isCorrectCredentials(uname, oldPsswd);
			
			if (isSanityCheckOk && isCorrect) {
				userManager.changePassword(uname, newPsswd);
				message = new Message("Success!", "The credentials were changed successfully.", MessageType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		doGet(response, message);
	}

}
