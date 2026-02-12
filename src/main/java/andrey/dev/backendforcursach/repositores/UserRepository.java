package andrey.dev.backendforcursach.repositores;

import andrey.dev.backendforcursach.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "UPDATE User u SET u.balance = u.balance + :balance ,u.address = :address WHERE u.id = :id")
    void updateUser(@Param("id") Long id, @Param("balance") BigDecimal balance, @Param("address") String address);

    @Modifying
    @Query(value = "UPDATE User u SET u.balance = u.balance - :balance WHERE  u.id = :id")
    void changeBalance(@Param("id") Long id, @Param("balance") BigDecimal balance);

    @Query(value = "SELECT u  FROM User u WHERE u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query(value = "SELECT u FROM User u WHERE  u.role = 'ADMIN'  ")
    List<User> findAdmins();

    @Query(value = "SELECT u FROM User u WHERE u.id!=:id ")
    List<User> findAllUsersExceptAdmin(@Param("id") Long id);

}
