package com.czhang.dashboard.dashboard.service;

import com.czhang.dashboard.dashboard.model.User;
import com.czhang.dashboard.dashboard.repositories.UserRespository;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log
public class UserService {

    private final UserRespository userRespository;

    public UserService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    public User findUserByUsername(@NonNull String username) {
        Optional<User> result= Optional.ofNullable(userRespository.findByUsername(username));
        return result.orElseThrow();
    }

    public boolean registerNewUser(@NonNull String username, @NonNull String password) {
        if(userRespository.findByUsername(username) != null) {
            log.warning("username exists : " + username );
            return false;
        }
        //MD5
        ByteSource byteSource = ByteSource.Util.bytes(username);
        String newPwd = new SimpleHash("MD5", password, byteSource, 1024).toHex();
        User newUser = new User();
        newUser.setPassword(newPwd);
        newUser.setUsername(username);
        userRespository.saveAndFlush(newUser);
        log.info("new user is created : " + username);
        return true;
    }
}
