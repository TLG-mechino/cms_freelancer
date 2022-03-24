package vn.compedia.website.repository;

import org.springframework.data.repository.CrudRepository;
import vn.compedia.website.model.Post;

public interface PostRepository extends CrudRepository<Post,Long> , PostRepositoryCustom {
}
