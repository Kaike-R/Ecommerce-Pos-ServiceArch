package br.com.kaikedev.auth.Repository;

import br.com.kaikedev.auth.Entity.UserEntity;
import br.com.kaikedev.auth.ValueObject.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    
    public UserEntity findByUsername(String username) {
        return new UserEntity(1, "username", "password", "email@email", "teste", UserRole.ADMIN);
    }

}
