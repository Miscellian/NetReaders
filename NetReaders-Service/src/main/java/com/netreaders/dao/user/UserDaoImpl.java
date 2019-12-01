package com.netreaders.dao.user;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.DuplicateModelException;
import com.netreaders.exception.classes.NoSuchModelException;
import com.netreaders.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
@Log4j
@PropertySource("classpath:query.properties")
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private final Environment env;
    private final JdbcTemplate template;
    private final UserMapper userMapper;

    @Override
    public User create(final User user) {

        final String sql_query = env.getProperty("user.create");

        KeyHolder holder = new GeneratedKeyHolder();

        try {
            template.update(creator(sql_query, user), holder);

            user.setId(retrieveId(holder));

            log.debug(String.format("Created a new user with id '%s'", user.getId()));
            return user;
        } catch (DuplicateKeyException e) {
            log.error(String.format("User '%s' is already exist", user.getUsername()));
            throw new DuplicateModelException("Internal sql exception");
        }
    }

    @Override
    public User getById(Integer id) {

        final String sql_query = env.getProperty("user.read");

        List<User> users = template.query(sql_query, userMapper, id);

        checkIfCollectionIsNull(users);

        if (users.isEmpty()) {
            log.debug(String.format("Didn't find any user by id '%s'", id));
            throw new NoSuchModelException(String.format("Didn't find any user by id '%s'", id));
        } else if (users.size() == 1) {
            log.debug(String.format("Found a user by id '%s'", id));
            return users.get(0);
        } else {
            log.error(String.format("Found more than one user by id '%s'", id));
            throw new DataBaseSQLException(String.format("Found more than one user by id '%s'", id));
        }
    }

    @Override
    public void update(User user) {

        final String sql_query = env.getProperty("user.update");

        int recordCount = template.update(sql_query,
                user.getUsername(),
                user.getUserPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePhoto(),
                user.getId());

        if (recordCount == 0) {
            log.debug(String.format("Didn't update any user by id '%d'", user.getId()));
        } else if (recordCount == 1) {
            log.debug(String.format("Updated user by id '%d'", user.getId()));
        } else {
            log.error(String.format("Updated more than one user by id '%d'", user.getId()));
            throw new DataBaseSQLException(String.format("Updated more than one user by id '%d'", user.getId()));
        }
    }

    @Override
    public void delete(User user) {

        final String sql_query = env.getProperty("user.delete");

        long id = user.getId();
        int recordCount = template.update(sql_query, id);

        if (recordCount == 0) {
            log.debug(String.format("Didn't delete any user by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Deleted user by id '%d'", id));
        } else {
            log.error(String.format("Deleted more than one user by '%d'", id));
            throw new DataBaseSQLException(String.format("Delete more than one user by '%d'", id));
        }
    }

    @Override
    public Collection<User> getAll() {

        final String sql_query = env.getProperty("user.readAll");

        List<User> users = template.query(sql_query, userMapper);
        if (users.isEmpty()) {
            log.debug("Didn't find any book");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d user(s)", users.size()));
            return users;
        }
    }

    @Override
    public User findByUsername(String username) {

        final String sql_query = env.getProperty("user.findByUsername");

        List<User> users = template.query(sql_query, userMapper, username);
        if (users.isEmpty()) {
            log.debug(String.format("Didn't find any user by username '%s'", username));
            throw new NoSuchModelException(String.format("Didn't find any user by username '%s'", username));
        } else if (users.size() == 1) {
            log.debug(String.format("Found a user by username '%s'", username));
            return users.get(0);
        } else {
            log.error(String.format("Found more than one user by username '%s'", username));
            throw new DataBaseSQLException(String.format("Found more than one user by username '%s'", username));
        }
    }
    
	@Override
	public boolean userExists(String username) throws DataBaseSQLException {
		final String sql_query = env.getProperty("user.findByUsername");

        List<User> users = template.query(sql_query, userMapper, username);
        if (users.isEmpty()) {
            log.debug(String.format("Didn't find any user by username '%s'", username));
            return false;
        } else if (users.size() == 1) {
            log.debug(String.format("Found a user by username '%s'", username));
            return true;
        } else {
            log.error(String.format("Found more than one user by username '%s'", username));
            throw new DataBaseSQLException(String.format("Found more than one user by username '%s'", username));
        }
	}

    @Override
    public Collection<User> getAdminsList() {
        final String sql_query = env.getProperty("user.getAdminsList");

        List<User> admins = template.query(sql_query, userMapper);
        if (admins.isEmpty()) {
            log.debug(String.format("Didn't find admins"));
            throw new NoSuchModelException(String.format("Didn't find admins"));
        } else {
            log.error(String.format("Found %d admins", admins.size()));
            return admins;
        }
    }

    @Override
    public Collection<User> getModeratorsList() {
        final String sql_query = env.getProperty("user.getModeratorsList");

        List<User> moderators = template.query(sql_query, userMapper);
        if (moderators.isEmpty()) {
            log.debug(String.format("Didn't find moderators"));
            throw new NoSuchModelException(String.format("Didn't find moderators"));
        } else {
            log.error(String.format("Found %d moderators", moderators.size()));
            return moderators;
        }
    }

    @Override
    public Collection<User> findByFirstName(String firstName) {

        final String sql_query = env.getProperty("user.findByFirstName");

        List<User> users = template.query(sql_query, userMapper, firstName);
        if (users.isEmpty()) {
            log.debug(String.format("Didn't find any user by first name '%s'", firstName));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d user(s) by first name '%s'", users.size(), firstName));
            return users;
        }
    }

    @Override
    public void deleteByUsername(String username) {

        final String sql_query = env.getProperty("user.deleteByUsername");

        int recordCount = template.update(sql_query, username);
        if (recordCount == 0) {
            log.debug(String.format("Dont delete any user by username '%s'", username));
        } else if (recordCount == 1) {
            log.debug(String.format("Delete user by username '%s'", username));
        } else {
            log.error(String.format("Delete more than one user by username '%s'", username));
            throw new DataBaseSQLException(String.format("Delete more than one user by username '%s'", username));
        }
    }

    private void checkIfCollectionIsNull(Collection<User> collection) {
        if (collection == null) {
            // unreachable, but who knows (:
            log.error("Get `null` reference from jdbcTemplate");
            throw new DataBaseSQLException("Get `null` reference from jdbcTemplate");
        }
    }

    private PreparedStatementCreator creator(String sql, User user) {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
        factory.setReturnGeneratedKeys(true);
        factory.addParameter(new SqlParameter(Types.VARCHAR));
        factory.addParameter(new SqlParameter(Types.VARCHAR));
        factory.addParameter(new SqlParameter(Types.VARCHAR));
        factory.addParameter(new SqlParameter(Types.VARCHAR));
        factory.addParameter(new SqlParameter(Types.VARCHAR));

        return factory.newPreparedStatementCreator(Arrays.asList(
                user.getUsername(),
                user.getUserPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()));
    }

    private Integer retrieveId(KeyHolder holder) {

        if (holder.getKeys() != null && holder.getKeys().size() > 0) {
            return (Integer) holder.getKeys().get("user_id");
        } else {
            return holder.getKey().intValue();
        }
    }


}
