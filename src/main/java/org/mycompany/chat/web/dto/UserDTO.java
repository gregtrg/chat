package org.mycompany.chat.web.dto;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
}
