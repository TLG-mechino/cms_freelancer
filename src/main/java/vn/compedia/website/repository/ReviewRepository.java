package vn.compedia.website.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review,Long>,ReviewRepositoryCustom {
}
