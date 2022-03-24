package vn.compedia.website.repository;

import vn.compedia.website.dto.ExamDto;
import vn.compedia.website.dto.ExamSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface TestRepositoryCustom {
    List<ExamDto> search(ExamSearchDto searchDto);

    BigInteger countSearch(ExamSearchDto searchDto);
}
