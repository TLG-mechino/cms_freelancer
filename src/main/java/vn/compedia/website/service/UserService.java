package vn.compedia.website.service;


import org.springframework.stereotype.Service;
import vn.compedia.website.model.User;

@Service
public interface UserService {

    User getUserByLongIdAccount(String username);
}
