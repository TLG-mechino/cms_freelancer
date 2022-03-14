package vn.compedia.website.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.model.Account;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long>, AccountRepositoryCustom {

    Account getByUsername(String username);

    List<Account> findAccountByUsername(String username);

    List<Account> findAccountByEmail(String email);

    @Query("select ac from Account ac where ac.accountId <> :accountId and ac.username = :username")
    List<Account> findAccountByUsernameExists(@Param("accountId") Long accountId, @Param("username") String username);

    @Query("select ac from Account ac where ac.accountId <> :accountId and ac.email = :email")
    List<Account> findAccountByEmailExists(@Param("accountId") Long accountId, @Param("email") String email);

    @Query("SELECT new vn.compedia.website.dto.AccountDto(ac.accountId, ac.roleId, ac.fullName, ac.email, ac.username, ac.password, ac.salt, ac.status, rl.name )" +
            " from Account ac, Role  rl where ac.accountId =:id and ac.roleId = rl.roleId")
    AccountDto getAccountInfo(Long id);
}
