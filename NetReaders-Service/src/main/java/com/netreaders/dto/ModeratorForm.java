package com.netreaders.dto;

import com.netreaders.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeratorForm {
    private User user;
    private String[] roles;
}
