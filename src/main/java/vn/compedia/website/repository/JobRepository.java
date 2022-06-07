package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.dto.response.TotalJobByDateResponse;
import vn.compedia.website.model.Job;

import java.util.List;

@Repository
public interface JobRepository extends CrudRepository<Job,Long>, JobRepositoryCustom {

    @Query(value = "select * from job j where j.JOB_ID = :jobId", nativeQuery = true)
    Job getJobByJobId(@Param("jobId") Long jobId);

    @Query("select count(j) from Job j")
    Integer totalJob();

//    @Query(value = "select count(j.JOB_ID) as total, date_format(j.CREATE_DATE, '%d')  as date " +
//            " from job j " +
//            " where date_format(j.CREATE_DATE, '%m') = :month and date_format(j.CREATE_DATE, '%Y') = :year group by date_format(j.CREATE_DATE, '%d') ", nativeQuery = true)
//    List<TotalJobByDateResponse> countJobByDate(@Param("month") Integer month, @Param("year") Integer year);
}
