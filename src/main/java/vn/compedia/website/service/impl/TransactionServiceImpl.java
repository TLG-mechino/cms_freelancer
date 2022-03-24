package vn.compedia.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.compedia.website.dto.TransactionDto;
import vn.compedia.website.dto.TransactionSearchDto;
import vn.compedia.website.model.Transaction;
import vn.compedia.website.repository.TransactionRepository;
import vn.compedia.website.service.TransactionService;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<TransactionDto> getAllTransactionByUserName(String userName, TransactionSearchDto dto) {
        return transactionRepository.getAllByUserName(userName,dto);
    }

    @Override
    public int countSearchByUserName(String userName , TransactionSearchDto dto) {
        return transactionRepository.countSearchByUserName(userName,dto);
    }
}

