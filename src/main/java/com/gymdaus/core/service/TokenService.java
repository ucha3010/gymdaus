package com.gymdaus.core.service;


import com.gymdaus.core.model.TokenModel;
import com.gymdaus.core.model.UserModel;

import java.util.Date;

public interface TokenService {

    TokenModel findById(String id);

    void deleteExpired();

    void add(TokenModel tokenModel);

    void update(TokenModel tokenModel);

    void delete(String id);

    boolean isExpired(Date expired);

    TokenModel fillTokenToSend(TokenModel tokenModel, UserModel userModel);
}
