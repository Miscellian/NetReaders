package com.netreaders.dao.announcement;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.NoSuchModelException;
import com.netreaders.models.Announcement;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Repository
@Log4j
@AllArgsConstructor
@PropertySource("classpath:query.properties")
public class AnnouncementDaoImpl implements AnnouncementDao {

    private final Environment env;
    private final JdbcTemplate template;
    private final AnnouncementMapper announcementMapper;
    private final KeyHolder holder;

    @Override
    public Announcement create(Announcement announcement) {

        final String sql_query = env.getProperty("announcement.create");

        try {
            template.update(creator(sql_query, announcement), holder);

            announcement.setId(retrieveId(holder));

            log.debug(String.format("Created a new announcement with id '%s'", announcement.getId()));
            return announcement;

        } catch (SQLException e) {
            log.error("Announcement creation fail!");
            throw new DataBaseSQLException("Announcement creation fail!");
        }
    }

    @Override
    public Announcement getById(Integer id) {

        final String sql_query = env.getProperty("announcement.read");

        List<Announcement> announcements = template.query(sql_query, announcementMapper, id);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.error(String.format("Didn't find any announcement by id '%s'", id));
            throw new NoSuchModelException(String.format("Didn't find any announcement by id '%s'", id));
        } else if (announcements.size() == 1) {
            log.debug(String.format("Found a announcement by id '%s'", id));
            return announcements.get(0);
        } else {
            log.error(String.format("Found more than one announcement by id '%s'", id));
            throw new DataBaseSQLException(String.format("Found more than one announcement by id '%s'", id));
        }
    }

    @Override
    public void update(Announcement announcement) {

        final String sql_query = env.getProperty("announcement.update");

        long id = announcement.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Didn't update any announcement by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Updated announcement by id '%d'", id));
        } else {
            log.error(String.format("Updated more than one announcement by id '%d'", id));
            throw new DataBaseSQLException(String.format("Updated more than one announcement by id '%d'", id));
        }
    }

    @Override
    public void delete(Announcement announcement) {

        final String sql_query = env.getProperty("announcement.delete");

        long id = announcement.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Didn't delete any announcement by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Deleted announcement by id '%d'", id));
        } else {
            log.error(String.format("Deleted more than one announcement by id '%d'", id));
            throw new DataBaseSQLException(String.format("Deleted more than one announcement by id '%d'", id));
        }
    }

    @Override
    public Collection<Announcement> getAll() {

        final String sql_query = env.getProperty("announcement.readAll");

        List<Announcement> announcements = template.query(sql_query, announcementMapper);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.debug("Didn't find any announcement");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d announcement(s)", announcements.size()));
            return announcements;
        }
    }

    @Override
    public Collection<Announcement> findByGenre(Integer genreId, Integer year, Integer month) {

        final String sql_query = env.getProperty("announcement.findAnnouncementsByGenre");

        List<Announcement> announcements = template.query(sql_query, announcementMapper, genreId, year, month);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.debug(String.format("Didn't find any announcement by genreId '%d'", genreId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d announcement(s) by genreId '%d'", announcements.size(), genreId));
            return announcements;
        }
    }

    @Override
    public Collection<Announcement> findByAuthor(Integer authorId, Integer year, Integer month) {

        final String sql_query = env.getProperty("announcement.findAnnouncementsByAuthor");

        List<Announcement> announcements = template.query(sql_query, announcementMapper, authorId, year, month);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.debug(String.format("Didn't find any announcement by authorID '%d'", authorId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d announcement(s) by authorID '%d'", announcements.size(), authorId));
            return announcements;
        }
    }

    @Override
    public Collection<Announcement> findAll(Integer year, Integer month) {

        final String sql_query = env.getProperty("announcement.getByIdWithOffset");

        List<Announcement> announcements = template.query(sql_query, announcementMapper, year, month);

        checkIfCollectionIsNull(announcements);

        if (announcements.isEmpty()) {
            log.debug(String.format("Didn't find any announcement year=%d and month=%d ", year, month));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d announcement(s) year=%d and month=%d ", announcements.size(), year, month));
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

    private PreparedStatementCreator creator(String sql, Announcement announcement) throws SQLException {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
        factory.setReturnGeneratedKeys(true);
        factory.addParameter(new SqlParameter(Types.TIMESTAMP));
        factory.addParameter(new SqlParameter(Types.VARCHAR));
        factory.addParameter(new SqlParameter(Types.BOOLEAN));

        return factory.newPreparedStatementCreator(Arrays.asList(
                announcement.getAnnouncement_date(),
                announcement.getDescription(),
                announcement.getPublished()));
    }

    private Integer retrieveId(KeyHolder holder) throws SQLException {

        if (holder.getKeys() != null && holder.getKeys().size() > 0) {
            return (Integer) holder.getKeys().get("announcement_id");
        } else {
            return holder.getKey().intValue();
        }
    }
}
