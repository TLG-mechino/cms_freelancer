package vn.compedia.website.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.Job;

import java.util.List;

@Repository
public interface JobRepository extends CrudRepository<Job,Long>, JobRepositoryCustom {
    @Query(value = "select *" +
            " from job j" +
            "         inner join (select u.USERNAME" +
            "                     from user u," +
            "                          account ac" +
            "                     where u.USERNAME = ac.USERNAME" +
            "                       and u.USERNAME = :userName) result1 on j.USERNAME = result1.USERNAME",nativeQuery = true)
    Page<Job> getAllByUserName(@Param("userName")String userName, Pageable pageable);

    @Query(value = "select * from job j where j.JOB_ID = :jobId",nativeQuery = true)
    Job getJobByUserNameAndJobId(@Param("jobId")Long jobId);

}
