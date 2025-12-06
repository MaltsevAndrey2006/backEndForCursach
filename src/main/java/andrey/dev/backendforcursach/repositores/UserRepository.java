package andrey.dev.backendforcursach.repositores;

import andrey.dev.backendforcursach.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
