package com.zyc.service;

import com.zyc.model.User;

import java.util.List;

public interface UserService {
	String findUsername(Integer id);
	User findUser(Integer id);
	int insertuUser(User user);
	User logIn(User user);
	User findByName(String name);
	void modifyUserInfo(User user);
	List<User> findUserByEmail(String email);
	public void modifyPasswordWithNewUser(User user,String temp);
}
