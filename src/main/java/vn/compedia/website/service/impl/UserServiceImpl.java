package vn.compedia.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.compedia.website.model.User;
import vn.compedia.website.repository.UserRepository;
import vn.compedia.website.service.UserService;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByLongIdAccount(String userName) {
        if(userRepository.getUsersByUserName(userName)!=null) {
            return userRepository.getUsersByUserName(userName);
        }
        return null;
    }
}
