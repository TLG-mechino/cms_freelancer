package vn.compedia.website.service;

import org.springframework.stereotype.Service;
import vn.compedia.website.dto.response.JobResponseDto;
import vn.compedia.website.dto.search.JobUserSearchDto;

import java.util.List;


@Service
public interface JobService {

    List<JobResponseDto> getAllJobRepository(String userName, JobUserSearchDto dto);
    int countSearchJob (String userName, JobUserSearchDto dto);


}
