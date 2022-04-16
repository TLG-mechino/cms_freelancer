package vn.compedia.website.repository;

import vn.compedia.website.dto.JobDto;
import vn.compedia.website.dto.JobSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface JobRepositoryCustom {

    List<JobDto> getAllJobRpByUserName(String username, JobSearchDto jobUserSearchDto);

    BigInteger countSearchRpByUserName(String username, JobSearchDto jobSearchDto);

    List<JobDto> getAllJobRecipient(String username, JobSearchDto jobSearchDto);

    BigInteger countSearchRecipient(String username, JobSearchDto jobSearchDto);

}
