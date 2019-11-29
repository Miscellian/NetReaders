package com.netreaders.dao.registrationtoken;


import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.DuplicateModelException;
import com.netreaders.exception.classes.NoSuchModelException;
import com.netreaders.models.RegistrationToken;
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

import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
@Log4j
@PropertySource("classpath:query.properties")
@AllArgsConstructor
public class RegistrationTokenDaoImpl implements RegistrationTokenDao {

    private Environment env;
    private RegistrationTokenMapper tokenMapper;
    private JdbcTemplate template;

    @Override
    public RegistrationToken create(RegistrationToken token) {

        final String sql_query = env.getProperty("registrationToken.create");

        KeyHolder holder = new GeneratedKeyHolder();

        try {
            template.update(creator(sql_query, token), holder);

            token.setTokenId(retrieveId(holder));

            log.debug(String.format("Created a new token with id '%s'", token.getTokenId()));
            return token;
        } catch (DuplicateKeyException e) {
            log.error(String.format("Token '%s' is already exist", token.getCreatedDateTime()));
            throw new DuplicateModelException(String.format("Token '%s' is already exist", token.getCreatedDateTime()));
        } catch (SQLException e) {
            log.error("Token creation fail!");
            throw new DataBaseSQLException("Token creation fail!");
        }
    }

    @Override
    public RegistrationToken getById(Integer id) {

        final String sql_query = env.getProperty("registrationToken.read");

        List<RegistrationToken> tokens = template.query(sql_query, tokenMapper, id);
        if (tokens.isEmpty()) {
            log.debug(String.format("Didn't find any token by tokenId '%s'", id));
            throw new NoSuchModelException(String.format("Didn't find any token by tokenId '%s'", id));
        } else if (tokens.size() == 1) {
            log.debug(String.format("Found a token by tokenId '%s'", id));
            return tokens.get(0);
        } else {
            log.error(String.format("Found more than one token by tokenId '%s'", id));
            throw new DataBaseSQLException(String.format("Found more than one token by tokenId '%s'", id));
        }
    }

    @Override
    public void update(RegistrationToken token) {
        // TODO
        throw new IllegalArgumentException();
    }

    @Override
    public void delete(RegistrationToken token) {

        final String sql_query = env.getProperty("registrationToken.delete");

        long id = token.getTokenId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Didn't delete any token by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Deleted token by id '%d'", id));
        } else {
            log.error(String.format("Deleted more than one token by id '%d'", id));
            throw new DataBaseSQLException(String.format("Deleted more than one token by id '%d'", id));
        }

    }

    @Override
    public Collection<RegistrationToken> getAll() {
        // TODO
        throw new IllegalArgumentException();
    }

    @Override
    public RegistrationToken findByToken(String token) {

        String sql_query = env.getProperty("registrationToken.findByToken");

        List<RegistrationToken> tokens = template.query(sql_query, tokenMapper, token);

        if (tokens.isEmpty()) {
            log.debug(String.format("Didn't find any token by tokenName '%s'", token));
            return null;
        } else if (tokens.size() == 1) {
            log.debug(String.format("Found a token by tokenName '%s'", token));
            return tokens.get(0);
        } else {
            log.error(String.format("Found more than one token by tokenName '%s'", token));
            throw new DataBaseSQLException(String.format("Found more than one token by tokenName '%s'", token));
        }
    }

    @Override
    public RegistrationToken findByUser(Integer userId) {

        String sql_query = env.getProperty("registrationToken.findByUser");

        List<RegistrationToken> tokens = template.query(sql_query, tokenMapper, userId);

        if (tokens.isEmpty()) {
            log.debug(String.format("Didn't find any token by userId '%s'", userId));
            return null;
        } else if (tokens.size() == 1) {
            log.debug(String.format("Found a token by userId '%s'", userId));
            return tokens.get(0);
        } else {
            log.error(String.format("Found more than one token by userId '%s'", userId));
            throw new DataBaseSQLException(String.format("Found more than one token by userId '%s'", userId));
        }
    }

    private void checkIfCollectionIsNull(Collection<RegistrationToken> collection) {
        if (collection == null) {
            // unreachable, but who knows (:
            log.error("Get `null` reference from jdbcTemplate");
            throw new DataBaseSQLException("Get `null` reference from jdbcTemplate");
        }
    }

    private PreparedStatementCreator creator(String sql, RegistrationToken token) throws SQLException {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
        factory.setReturnGeneratedKeys(true);
        factory.addParameter(new SqlParameter(Types.VARCHAR));
        factory.addParameter(new SqlParameter(Types.INTEGER));
        factory.addParameter(new SqlParameter(Types.DATE));

        return factory.newPreparedStatementCreator(Arrays.asList(
                token.getToken(),
                token.getUserId(),
                token.getCreatedDateTime()));
    }

    private Integer retrieveId(KeyHolder holder) throws SQLException {

        if (holder.getKeys() != null && holder.getKeys().size() > 0) {
            return (Integer) holder.getKeys().get("registration_token_id");
        } else {
            return holder.getKey().intValue();
        }
    }
}
