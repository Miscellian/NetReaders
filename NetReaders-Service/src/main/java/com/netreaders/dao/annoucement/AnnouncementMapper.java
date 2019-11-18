package com.netreaders.dao.annoucement;

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
        announcement.setAnnouncement_date(rs.getDate(1));
        announcement.setDescription(rs.getString(2));
        announcement.setPublished(rs.getBoolean(3));
        return announcement;
    }

    @Bean
    public AnnouncementMapper getAnnouncementMapper() {
        return new AnnouncementMapper();
    }
}
