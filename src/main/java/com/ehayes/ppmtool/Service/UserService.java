package com.ehayes.ppmtool.Service;

import com.ehayes.ppmtool.Exceptions.SameUsernameException;
import com.ehayes.ppmtool.Repository.UserRepository;
import com.ehayes.ppmtool.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //username has to be unique custom exception
                 newUser.setUsername(newUser.getUsername());
            //make sure password and confirm password match

            //we don't persist or show the confirm password in the database or on json
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        }catch (Exception ex){
            throw new SameUsernameException("username " + newUser.getUsername() + " Already exist");

        }
        }

}
