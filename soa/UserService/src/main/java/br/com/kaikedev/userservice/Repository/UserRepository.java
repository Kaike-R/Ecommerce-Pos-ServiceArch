package br.com.kaikedev.userservice.Repository;

import br.com.kaikedev.userservice.Entity.Roles;
import br.com.kaikedev.userservice.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.Collection;

@Repository
public class UserRepository {


    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<UserEntity> findAll() {

        String sql = "SELECT id, name, email, phone, roles FROM users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UserEntity user = new UserEntity();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setRoles(Roles.valueOf(rs.getString("roles")));
            return user;
        });
    }

    public UserEntity findById(Integer id) {
        String sql = "SELECT id, name, email, phone, roles FROM users WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->{
           UserEntity user = new UserEntity();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setRoles(Roles.valueOf(rs.getString("roles")));
            return user;
        }, id);
    }

    public UserEntity findByEmail(String email) {
        String sql = "SELECT id, name, email, phone, roles FROM users WHERE email = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->{
            UserEntity user = new UserEntity();

            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setRoles(Roles.valueOf(rs.getString("roles")));
            return user;
        }, email);
    }


    @Transactional
    public Integer update(UserEntity user) {

        String sql = "UPDATE users SET name = ?, email = ?, phone = ?, password = ? WHERE id = ?";

        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPhone(), user.getId());

    }

    public Boolean deleteUserById(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Transactional
    public Boolean bulkInsert(Collection<UserEntity> users) {

        String sql = "INSERT INTO users (name, email, phone, roles) VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.batchUpdate(sql, users, users.size(), (PreparedStatement ps, UserEntity user) -> {
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPhone());
                ps.setString(4, user.getRoles().name());
            });
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }

        return true;
    }



}
