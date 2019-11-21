package com.netreaders.dao.annoucement;

import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Announcement;
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
public class AnnouncementDaoImpl implements AnnouncementDao {

    private final JdbcTemplate template;

    private final Environment env;

    private final AnnouncementMapper announcementMapper;

    public AnnouncementDaoImpl(JdbcTemplate template, Environment env, AnnouncementMapper announcementMapper) {
        this.template = template;
        this.env = env;
        this.announcementMapper = announcementMapper;
    }

    @Override
    public Announcement create(Announcement announcement) {

        final String sql_query = env.getProperty("announcement.create");

        KeyHolder holder = new GeneratedKeyHolder();

        // save object into DB and return auto generated PK via KeyHolder
        // or throws DuplicateKeyException if record exist in table
        try {
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                    ps.setDate(1, announcement.getAnnouncement_date());
                    ps.setString(2, announcement.getDescription());
                    ps.setBoolean(3, announcement.getPublished());
                    return ps;
                }
            }, holder);

            Integer newId;
            if (holder.getKeys() != null && holder.getKeys().size() > 1) {
                newId = (Integer) holder.getKeys().get("announcement_id");
            } else {
                newId = holder.getKey().intValue();
            }
            announcement.setId(newId);
            log.debug(String.format("Create a new announcement with id '%s'", newId));

            return announcement;

        } catch (DuplicateKeyException e) {
            log.error(String.format("Announcement '%s' is already exist", announcement.getId()));
            throw new DataBaseSQLException(String.format("Announcement '%s' is already exist", announcement.getId()));
        }
    }

    @Override
    public Announcement getById(Integer id) {

        String sql_query = env.getProperty("announcement.read");

        List<Announcement> announcements = template.query(sql_query, announcementMapper, id);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.debug(String.format("Dont find any announcement by id '%s'", id));
            return null;
        } else if (announcements.size() == 1) {
            log.debug(String.format("Find a announcement by id '%s'", id));
            return announcements.get(0);
        } else {
            log.error(String.format("Find more than one announcement by id '%s'", id));
            throw new DataBaseSQLException(String.format("Find more than one announcement by id '%s'", id));
        }
    }

    @Override
    public void update(Announcement announcement) {

        String sql_query = env.getProperty("announcement.update");

        long id = announcement.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Dont update any announcement by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Update announcement by id '%d'", id));
        } else {
            log.error(String.format("Update more than one announcement by id '%d'", id));
            throw new DataBaseSQLException(String.format("Update more than one announcement by id '%d'", id));
        }
    }

    @Override
    public void delete(Announcement announcement) {

        String sql_query = env.getProperty("announcement.delete");

        long id = announcement.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Dont delete any announcement by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Delete announcement by id '%d'", id));
        } else {
            log.error(String.format("Delete more than one announcement by id '%d'", id));
            throw new DataBaseSQLException(String.format("Delete more than one announcement by id '%d'", id));
        }
    }

    @Override
    public Collection<Announcement> getAll() {

        String sql_query = env.getProperty("announcement.readAll");

        List<Announcement> announcements = template.query(sql_query, announcementMapper);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.debug("Dont find any announcement");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d announcement(s)", announcements.size()));
            return announcements;
        }
    }

    @Override
    public Collection<Announcement> findAnnouncementsByGenre(int genre_id, int amount, int offset) {

        String sql_query = env.getProperty("announcement.findAnnouncementsByGenre");

        List<Announcement> announcements = template.query(sql_query, announcementMapper, genre_id, amount, offset);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.debug(String.format("Dont find any announcement by genreId '%d'", genre_id));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d announcement(s) by genreId '%d'", announcements.size(), genre_id));
            return announcements;
        }
    }

    @Override
    public Collection<Announcement> findAnnouncementsByAuthor(int author_id, int amount, int offset) {

        String sql_query = env.getProperty("announcement.findAnnouncementsByAuthor");

        List<Announcement> announcements = template.query(sql_query, announcementMapper, author_id, amount, offset);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.debug(String.format("Dont find any announcement by authorID '%d'", author_id));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d announcement(s) by authorID '%d'", announcements.size(), author_id));
            return announcements;
        }
    }


    @Override
    public Collection<Announcement> getById(int amount, int offset) {

        String sql_query = env.getProperty("announcement.getByIdWithOffset");

        List<Announcement> announcements = template.query(sql_query, announcementMapper, amount, offset);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.debug(String.format("Dont find any announcement with offset '%d'", offset));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d announcement(s) with offset '%d'", announcements.size(), offset));
            return announcements;
        }
    }

    private void checkIfCollectionIsNull(Collection<Announcement> collection) {
        if (collection == null) {
            // unreachable, but who knows (:
            log.error("Get `null` reference from jdbcTemplate");
            throw new DataBaseSQLException("Get `null` reference from jdbcTemplate");
        }
    }
}
