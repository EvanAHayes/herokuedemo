package com.ehayes.ppmtool.Service;

import com.ehayes.ppmtool.Repository.UserRepository;
import com.ehayes.ppmtool.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) new UsernameNotFoundException("No User Found with name ");

        return user;
    }

    //going to need this to filter the token
    @Transactional
    public User loadUserById(Long id){
        User user = userRepository.getById(id);
       if(user == null) new UsernameNotFoundException("No User Found with name ");
        return user;
    }
}
