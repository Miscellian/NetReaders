package com.netreaders.dao.role;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Role;
import com.netreaders.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@Log4j
@PropertySource("classpath:query.properties")
@AllArgsConstructor
public class RoleDaoImpl implements RoleDao {

    private Environment env;
    private RoleMapper roleMapper;
    private JdbcTemplate template;

    @Override
    public Role create(Role newInstance) {
        // TODO
        throw new IllegalArgumentException();
    }

    @Override
    public Role getById(Integer id) {
        String sql_query = env.getProperty("role.read");

        List<Role> roles = template.query(sql_query, roleMapper, id);
        if (roles.isEmpty()) {
            log.debug(String.format("Dont find any role by id '%s'", id));
            return null;
        } else if (roles.size() == 1) {
            log.debug(String.format("Find a role by id '%s'", id));
            return roles.get(0);
        } else {
            log.error(String.format("Find more than one role by id '%s'", id));
            throw new DataBaseSQLException("Internal sql exception");
        }
    }

    @Override
    public void update(Role role) {
        // TODO
        throw new IllegalArgumentException();
    }

    @Override
    public void delete(Role role) {
        // TODO
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<Role> getAll() {
        // TODO
        throw new IllegalArgumentException();
    }

    @Override
    public Role findByRoleName(String roleName) {

        final String sql_query = env.getProperty("role.findByRoleName");


        List<Role> roles = template.query(sql_query, roleMapper, roleName);
        if (roles.isEmpty()) {
            log.debug(String.format("Dont find any role by roleName '%s'", roleName));
            return null;
        } else if (roles.size() == 1) {
            log.debug(String.format("Find a role by roleName '%s'", roleName));
            return roles.get(0);
        } else {
            log.error(String.format("Find more than one role by roleName '%s'", roleName));
            throw new DataBaseSQLException("Internal sql exception");
        }

    }

    @Override
    public Collection<Role> findByUserId(Integer id) {
        String sql_query = env.getProperty("role.findByUserId");
        List<Role> roles = template.query(sql_query, roleMapper, id);
        if (roles.isEmpty()) {
            log.debug(String.format("Dont find any role by userid '%d'", id));
            return null;
        } else {
            log.debug(String.format("Found roles by userId '%d'", id));
            return roles;
        }
    }

    @Override
    public Boolean addUserToRole(Role role, User user) {
        String sql_query = env.getProperty("role.addUserToRole");
        int rows = template.update(sql_query, user.getId(), role.getId());
        return rows > 0;
    }

    @Override
    public Collection<Role> findByUsername(String moderatorUsername) {
        String sql_query = env.getProperty("role.findByUsername");
        List<Role> roles = template.query(sql_query, roleMapper, moderatorUsername);
        if (roles.isEmpty()) {
            log.debug(String.format("Dont find any role by username '%s'", moderatorUsername));
            return null;
        } else {
            log.debug(String.format("Found roles by username '%s'", moderatorUsername));
            return roles;
        }
    }
}
