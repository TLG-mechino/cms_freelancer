//package vn.compedia.website.service.impl;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import vn.compedia.website.dto.response.JobResponseDto;
//import vn.compedia.website.dto.search.JobUserSearchDto;
//import vn.compedia.website.repository.JobRepository;
//import vn.compedia.website.service.JobService;
//
//import java.util.List;
//
//public class JobServiceImpl implements JobService {
//
//
//    @Autowired
//    JobRepository jobRepository;
//
//
//    @Override
//    public List<JobResponseDto> getAllJobRepository(String userName, JobUserSearchDto dto) {
//        return jobRepository.getAllJobRpByUserName(userName,dto);
//    }
//
//    @Override
//    public int countSearchJob(String userName, JobUserSearchDto dto) {
//        return 0;
//    }
//}
