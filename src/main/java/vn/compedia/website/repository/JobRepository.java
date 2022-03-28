package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.Job;

@Repository
public interface JobRepository extends CrudRepository<Job,Long>, JobRepositoryCustom {

    @Query(value = "select * from JOB j where j.JOB_ID = :jobId", nativeQuery = true)
    Job getJobByJobId(@Param("jobId") Long jobId);

}
