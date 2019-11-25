package com.netreaders.dao.annoucement.mapper;

import com.netreaders.models.Announcement;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AnnouncementMapper implements RowMapper<Announcement> {

    @Override
    public Announcement mapRow(ResultSet rs, int rowNum) throws SQLException {

        Announcement announcement = new Announcement();
        announcement.setId(rs.getInt("announcement_id"));
        announcement.setAnnouncement_date(rs.getDate("announcement_date"));
        announcement.setDescription(rs.getString("description"));
        announcement.setPublished(rs.getBoolean("published"));

        return announcement;
    }

    @Bean
    public AnnouncementMapper getAnnouncementMapper() {
        return new AnnouncementMapper();
    }
}
