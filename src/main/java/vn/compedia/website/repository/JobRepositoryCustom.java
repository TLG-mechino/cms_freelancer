package vn.compedia.website.repository;

import vn.compedia.website.dto.JobDto;
import vn.compedia.website.dto.JobSearchDto;
import vn.compedia.website.dto.response.JobResponseDto;
import vn.compedia.website.dto.search.JobUserSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface JobRepositoryCustom {

    List<JobDto> getAllJobRpByUserName(String userName, JobSearchDto jobUserSearchDto);

    BigInteger countSearchRpByUserName(String userName, JobSearchDto jobSearchDto);

}
