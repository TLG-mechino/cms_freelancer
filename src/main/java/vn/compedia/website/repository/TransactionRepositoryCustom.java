package vn.compedia.website.repository;

import vn.compedia.website.dto.TransactionDto;
import vn.compedia.website.dto.TransactionSearchDto;
import vn.compedia.website.dto.response.TotalTransactionByDateResponse;
import vn.compedia.website.dto.response.TotalUserByDateResponse;

import java.math.BigInteger;
import java.util.List;

public interface TransactionRepositoryCustom {
    List<TransactionDto> exportExcel(TransactionSearchDto SearchDto);

    List<TransactionDto> search(TransactionSearchDto searchDto);

    List<TransactionDto> getAllByUserName(String username,TransactionSearchDto dto);

    BigInteger countSearchByUserName(String username,TransactionSearchDto searchDto);

    BigInteger countSearch(TransactionSearchDto searchDto);

    List<TotalTransactionByDateResponse> countTransactionByDate(Integer month, Integer year);
}
