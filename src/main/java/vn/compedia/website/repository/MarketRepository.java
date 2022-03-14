package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.model.Market;

import java.util.List;

public interface MarketRepository extends CrudRepository<Market, Long>, MarketRepositoryCustom {

    @Query("select m from Market m where m.code = :code and m.marketId <> :marketId")
    List<Market> findByCodeExists(@Param("code") String code, @Param("marketId") Long marketId);

    @Query("select m from Market m where m.prefix = :prefix and m.marketId <> :marketId")
    List<Market> findByPrefixExists(@Param("prefix") Integer prefix, @Param("marketId") Long marketId);

    List<Market> findAllByStatus(Integer status);
}
