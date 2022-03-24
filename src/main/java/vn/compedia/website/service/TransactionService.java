package vn.compedia.website.service;

import org.springframework.stereotype.Service;
import vn.compedia.website.dto.TransactionDto;
import vn.compedia.website.dto.TransactionSearchDto;
import vn.compedia.website.model.Transaction;

import java.util.List;


@Service
public interface TransactionService {

    List<TransactionDto> getAllTransactionByUserName(String userName, TransactionSearchDto dto);

    int countSearchByUserName(String userName, TransactionSearchDto dto);
}
