package com.ehayes.ppmtool.Repository;

import com.ehayes.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {


    User findByUsername(String username);

    User getById(Long id);


}
