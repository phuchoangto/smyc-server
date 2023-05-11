package to.phuc.smyc.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import to.phuc.smyc.domain.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

}
