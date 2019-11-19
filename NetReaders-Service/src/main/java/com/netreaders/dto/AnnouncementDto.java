package com.netreaders.dto;

import com.netreaders.models.Announcement;
import com.netreaders.models.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {
    private Collection<Book> books;
    private Announcement announcement;
}
