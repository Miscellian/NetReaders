package com.netreaders.dao.user;

import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.User;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Log4j
@PropertySource("classpath:query.properties")
@Repository
public class UserDaoImpl implements UserDao {

    private final Environment env;

    private final JdbcTemplate template;

    private final UserMapper userMapper;

    public UserDaoImpl(Environment env, JdbcTemplate template, UserMapper userMapper) {
        this.env = env;
        this.template = template;
        this.userMapper = userMapper;
    }

    @Override
    public User create(final User user) {

        final String sql_query = env.getProperty("user.create");

        KeyHolder holder = new GeneratedKeyHolder();

        // save object into DB and return auto generated PK via KeyHolder
        // or throws DuplicateKeyException if record exist in table
        try {
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, user.getUserNickname());
                    ps.setString(2, user.getUserPassword());
                    ps.setString(3, user.getEmail());
                    ps.setString(4, user.getFirstName());
                    ps.setString(5, user.getLastName());
                    return ps;
                }
            }, holder);

            Integer newId;
            if (holder.getKeys() != null && holder.getKeys().size() > 1) {
                newId = (Integer) holder.getKeys().get("user_id");
            } else {
                newId = holder.getKey().intValue();
            }
            user.setUserId(newId);
            log.debug(String.format("Create a new user with id '%s'", newId));

            return user;

        } catch (DuplicateKeyException e) {
            log.error(String.format("User '%s' is already exist", user.getUserNickname()));
            throw new DataBaseSQLException(String.format("User '%s' is already exist", user.getUserNickname()));
        }
    }

    @Override
    public User getById(Long id) {

        String sql_query = env.getProperty("user.read");

        List<User> users = template.query(sql_query, userMapper, id);

        checkIfCollectionIsNull(users);

        if (users.isEmpty()) {
            log.debug(String.format("Dont find any user by id '%s'", id));
            return null;
        } else if (users.size() == 1) {
            log.debug(String.format("Find a user by id '%s'", id));
            return users.get(0);
        } else {
            log.error(String.format("Find more than one user by id '%s'", id));
            throw new DataBaseSQLException(String.format("Find more than one user by id '%s'", id));
        }
    }

    @Override
    public void update(User user) {

        String sql_query = env.getProperty("user.update");

        int recordCount = template.update(sql_query,
                user.getUserNickname(),
                user.getUserPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePhoto(),
                user.getUserId());

        if (recordCount == 0) {
            log.debug(String.format("Dont update any user by id '%d'", user.getUserId()));
        } else if (recordCount == 1) {
            log.debug(String.format("Update user by id '%d'", user.getUserId()));
        } else {
            log.error(String.format("Update more than one user by id '%d'", user.getUserId()));
            throw new DataBaseSQLException(String.format("Update more than one user by id '%d'", user.getUserId()));
        }
    }

    @Override
    public void delete(User user) {

        String sql_query = env.getProperty("user.delete");

        long id = user.getUserId();
        int recordCount = template.update(sql_query, id);

        if (recordCount == 0) {
            log.debug(String.format("Dont delete any user by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Delete user by id '%d'", id));
        } else {
            log.error(String.format("Delete more than one user by '%d'", id));
            throw new DataBaseSQLException(String.format("Delete more than one user by '%d'", id));
        }
    }

    @Override
    public Collection<User> getAll() {

        String sql_query = env.getProperty("user.readAll");

        List<User> users = template.query(sql_query, userMapper);

        checkIfCollectionIsNull(users);

        if (users.isEmpty()) {
            log.debug("Dont find any user");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d user(s)", users.size()));
            return users;
        }
    }

    @Override
    public User findByNickname(String nickname) {

        String sql_query = env.getProperty("user.findByNickname");

        List<User> users = template.query(sql_query, userMapper, nickname);

        checkIfCollectionIsNull(users);

        if (users.isEmpty()) {
            log.debug(String.format("Dont find any user by nickname '%s'", nickname));
            return null;
        } else if (users.size() == 1) {
            log.debug(String.format("Find a user by nickname '%s'", nickname));
            return users.get(0);
        } else {
            log.error(String.format("Find more than one user by nickname '%s'", nickname));
            throw new DataBaseSQLException(String.format("Find more than one user by nickname '%s'", nickname));
        }
    }

    @Override
    public Collection<User> findByFirstName(String firstName) {

        String sql_query = env.getProperty("user.findByFirstName");

        List<User> users = template.query(sql_query, userMapper, firstName);

        checkIfCollectionIsNull(users);

        if (users.isEmpty()) {
            log.debug(String.format("Dont find any user by first name '%s'", firstName));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d user(s) by first name '%s'", users.size(), firstName));
            return users;
        }
    }

    @Override
    public void deleteByNickname(String nickname) throws DataBaseSQLException {

        String sql_query = env.getProperty("user.deleteByNickname");

        int recordCount = template.update(sql_query, nickname);
        if (recordCount == 0) {
            log.debug(String.format("Dont delete any user by nickname '%s'", nickname));
        } else if (recordCount == 1) {
            log.debug(String.format("Delete user by nickname '%s'", nickname));
        } else {
            log.error(String.format("Delete more than one user by nickname '%s'", nickname));
            throw new DataBaseSQLException(String.format("Delete more than one user by nickname '%s'", nickname));
        }
    }

    private void checkIfCollectionIsNull(Collection<User> collection) {
        if (collection == null) {
            // unreachable, but who knows (:
            log.error("Get `null` reference from jdbcTemplate");
            throw new DataBaseSQLException("Get `null` reference from jdbcTemplate");
        }
    }
}
