package andrey.dev.backendforcursach.repositores;

import andrey.dev.backendforcursach.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "UPDATE User u SET u.balance = u.balance + :balance ,u.address = :address WHERE u.id = :id")
    void updateUser(@Param("id") Long id, @Param("balance") BigDecimal balance, @Param("address") String address);
}
