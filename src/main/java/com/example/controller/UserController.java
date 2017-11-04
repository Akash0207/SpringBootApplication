package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;

@RestController
public class UserController {

	static int id = 1;
	static List<User> userList = new ArrayList<User>();

	@RequestMapping(path = "/createUser", method = RequestMethod.POST)
	public Map<String,Object> createUser(@RequestBody User user) {
		Map<String, Object> response = new HashMap<>();
		
		//validation for birth date format
		boolean formatCheck = checkDateFormat("dd-MMM-yyyy",user.getBirthDate());
		if(!formatCheck){
			response.put("valErrors", "Birth date is not in correct format i.e. DD-MMM-YYYY");
			return response;
		}
		
		user.setId(id++);
		userList.add(user);
		response.put("resMsg", "User is created with user id:" + user.getId());
		return response;
	}

	@RequestMapping(path = "/getUser")
	public Map<String, Object> getUser() {

		Map<String, Object> result = new HashMap<>();
		result.put("userList", userList);
		return result;
	}

	@RequestMapping(path = "/updateUser", method = RequestMethod.POST)
	public Map<String, Object> updateUser(@RequestBody User user) {
		
		HashMap<String, Object> response = new HashMap<String, Object>();

		// validation to check if user list is empty
		if (userList.isEmpty()) {
			response.put("message", "No users are avalible in list! Please add a user");
			return response;
		}
		
		int indexOfObject = 0;
		boolean isUserExist = false;
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getId() == user.getId()) {
				isUserExist = true;
				indexOfObject = i;
			}
		}
		
		// validation to check user with input user id exist in user list
		if (isUserExist) {
			userList.get(indexOfObject).setBirthDate(user.getBirthDate());
			userList.get(indexOfObject).setPinCode(user.getPinCode());
		} else {
			response.put("valError", "User with User ID:" + user.getId() + " is not exist! Create User First.");
			return response;
		}

		// return success response on successfully updating user details
		response.put("resMsg", "User Details of User ID:" + user.getId() + " is updated ");
		return response;
	}

	@RequestMapping(path = "/deleteUser", method = RequestMethod.POST)
	public Map<String, Object> deleteUser(@RequestBody User user) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		// validation to check if user list is empty
		if (userList.isEmpty()) {
			response.put("message", "No users are avalible in list! Please add a user");
			return response;
		}

		int indexOfObject = 0;
		boolean isUserExist = false;
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getId() == user.getId()) {
				isUserExist = true;
				indexOfObject = i;
			}
		}

		// validation to check user with input user id exist in user list
		if (isUserExist) {
			userList.get(indexOfObject).setIsActive(false);
		} else {
			response.put("valError", "User with User ID:" + user.getId() + " is not exist! Create User First.");
			return response;
		}

		// return success response on successfully updating user details
		response.put("resMsg", "User with User ID:" + user.getId() + " is deleted ");
		return response;
	}
	
	private boolean checkDateFormat(String format, String value){
		Date date = null;
	    try {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	        date = sdf.parse(value);
	        
	        if (!value.equalsIgnoreCase(sdf.format(date))) {
	            date = null;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return date != null;
	    
	}
}
