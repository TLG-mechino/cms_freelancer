package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.Sticker;

import java.util.List;

@Repository
public interface StickersRepository extends CrudRepository<Sticker,Long>, StickersRepositoryCustom {
    @Query("select s from Sticker s where s.code = :code and s.stickersId <> :stickerId")
    List<Sticker> findByCodeExists(@Param("code") String code, @Param("stickerId") Long stickerId);

    @Query("select count(s.stickersId) from Sticker s where s.status = 1 ")
    Integer totalStickers();

    @Query("select s.position from Sticker s where s.position is not null")
    List<Integer> listPositionRemove();

}
