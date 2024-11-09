package com.gymdaus.core.service.impl;

import com.gymdaus.core.entity.Token;
import com.gymdaus.core.exception.ValidationException;
import com.gymdaus.core.mapper.MapperToken;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.TokenModel;
import com.gymdaus.core.repository.TokenRepository;
import com.gymdaus.core.service.TokenService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service()
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private MapperToken mapperToken;

    @Override
    public TokenModel findById(String id) {
        return mapperToken.entity2Model(tokenRepository.findById(id).orElse(null));
    }

    @Override
    public void deleteExpired() {
        List<Token> tokenList = tokenRepository.findAllByOrderByExpirationAsc();
        if (tokenList != null) {
            for (Token token : tokenList) {
                if (isExpired(token.getExpiration())) {
                    tokenRepository.delete(token);
                }
            }
        }
    }

    @Override
    public void add(TokenModel tokenModel) {
        tokenRepository.save(mapperToken.model2Entity(tokenModel));
    }

    @Override
    public void update(TokenModel tokenModel) {
        tokenRepository.save(mapperToken.model2Entity(tokenModel));
    }

    @Override
    public void delete(String id) {
        tokenRepository.delete(tokenRepository.findById(id).get());
    }

    @Override
    public boolean isExpired(Date expired) {
        return expired.before(new Date());
    }

    @Override
    public TokenModel getNewToken(int minutes, String password, GymModel gymModel, String username,
                                  String methodToBeUse, String usernameSendChange) {

        TokenModel tokenModel = new TokenModel();
        tokenModel.setId(UUID.randomUUID().toString());
        tokenModel.setAttempts(0);
        tokenModel.setExpiration(Utils.addSubtractMinutes(minutes));
        tokenModel.setPassword(password);
        tokenModel.setGymModel(gymModel);
        tokenModel.setUsername(username);
        tokenModel.setMethodToBeUse(methodToBeUse);
        tokenModel.setUsernameSendChange(usernameSendChange);
        add(tokenModel);
        return tokenModel;
    }

    @Override
    public TokenModel verifyToken(String tokenId, String methodInvoked) throws ValidationException {

        TokenModel tokenModel = findById(tokenId);
        if (tokenModel == null) {
            throw new AccessDeniedException("Token id not found: " + tokenId);
        } else {
            if (isExpired(tokenModel.getExpiration())) {
                throw new ValidationException(Constants.TOKEN_EXPIRED, "Token expired on " + tokenModel.getExpiration());
            } else if (tokenModel.getAttempts() > 2) {
                throw new ValidationException(Constants.TOKEN_MAXIMUM_ATTEMPTS,
                        "Token used more than maximum attempts: " + tokenModel.getAttempts());
            } else if (!tokenModel.getMethodToBeUse().equals(methodInvoked)) {
                throw new AccessDeniedException("Token used in different method. Must be used in  " + tokenModel.getMethodToBeUse() + " and " +
                        "has been used in " + methodInvoked);
            }
            return tokenModel;
        }
    }
}
