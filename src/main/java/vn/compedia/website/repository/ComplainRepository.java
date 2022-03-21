package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.Complain;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Long>, ComplainRepositoryCustom {
}
