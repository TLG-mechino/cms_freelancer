package vn.compedia.website.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.website.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long>, TransactionRepositoryCustom {
    @Query("select t from Transaction t where t.code = :code and t.transactionId <> :transactionId")
    List<Transaction> findByCodeExists(@Param("code") String code, @Param("transactionId") Long transactionId);

    @Query(value = "select * from Transaction tran where tran.SENDER =:userName or tran.RECIPIENT =:userName",nativeQuery = true)
    Page<Transaction> getAllTransactionByUserName(@Param("userName")String userName, Pageable pageable);
}
