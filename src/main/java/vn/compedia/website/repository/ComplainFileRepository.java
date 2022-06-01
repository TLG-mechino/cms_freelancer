package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.model.ComplainFile;

import java.util.List;

public interface ComplainFileRepository extends CrudRepository<ComplainFile, Long> {

    @Query("select cf from ComplainFile cf where cf.complainId = :complainId ")
    List<ComplainFile> findAllByComplainId(@Param("complainId") Long complainId);
}
