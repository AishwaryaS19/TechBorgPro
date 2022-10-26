package com.emb.ecom.service;

import java.util.List;

import com.emb.ecom.model.User;

public interface UserService {

	public void saveUser(User user);
    public List<Object> isUserPresent(User user);
}
