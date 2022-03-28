package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.ComplainType;

import java.util.List;

@Repository
public interface ComplainTypeRepository extends JpaRepository<ComplainType, Long> {

    @Query("SELECT u FROM ComplainType u WHERE u.status = 1")
    List<ComplainType> findAllByStatus(Integer status);

}
