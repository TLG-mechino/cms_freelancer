package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long>, AccountRepositoryCustom {

    Account getByUsername(String username);

    Account findAccountByUsername(String username);

    Account findAccountByEmail(String email);

    Account findAccountByPhone(String phone);

    @Query("select ac from Account ac where ac.accountId <> :accountId and ac.username = :username")
    Account findAccountByUsernameExists(@Param("accountId") Long accountId, @Param("username") String username);

    @Query("select ac from Account ac where ac.accountId <> :accountId and ac.email = :email")
    Account findAccountByEmailExists(@Param("accountId") Long accountId, @Param("email") String email);

    @Query("select ac from Account ac where ac.accountId <> :accountId and ac.phone = :phone")
    Account findAccountByPhoneExists(@Param("accountId") Long accountId, @Param("phone") String phone);

    @Query("SELECT new vn.compedia.website.dto.AccountDto(ac.accountId, ac.email, ac.username, ac.password, ac.salt, ac.status )" +
            " from Account ac where ac.accountId =:id ")
    AccountDto getAccountInfo(Long id);

}
