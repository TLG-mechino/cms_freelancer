package vn.compedia.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.compedia.website.repository.AccountRepository;
import vn.compedia.website.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean checkExistEmail(String email) {
        return accountRepository.checkExistEmail(email);
    }
}
