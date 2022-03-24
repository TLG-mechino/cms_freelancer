package vn.compedia.website.repository;

import vn.compedia.website.dto.UserExamDto;
import vn.compedia.website.dto.UserExamSearchDto;
import vn.compedia.website.model.UserExam;

import java.math.BigInteger;
import java.util.List;

public interface TestEvaluateRepositoryCustom {

    List<UserExamDto> search(UserExamSearchDto searchDto);

    BigInteger countSearch(UserExamSearchDto searchDto);

    UserExamDto findByIdAndExamCode(Long userExamId);
}
