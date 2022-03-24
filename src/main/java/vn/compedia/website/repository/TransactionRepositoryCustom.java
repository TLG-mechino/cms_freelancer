package vn.compedia.website.repository;

import vn.compedia.website.dto.TransactionDto;
import vn.compedia.website.dto.TransactionSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface TransactionRepositoryCustom {
    List<TransactionDto> exportExcel(TransactionSearchDto SearchDto);

    List<TransactionDto> search(TransactionSearchDto searchDto);

    BigInteger countSearch(TransactionSearchDto searchDto);
}
