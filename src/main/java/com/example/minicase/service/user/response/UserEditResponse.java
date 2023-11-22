package com.example.minicase.service.user.response;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@NoArgsConstructor
public class UserEditResponse {

    private Long id;

    private String name;

    private String email;

    private String userName;

    private String password;

    private LocalDate dob;

    private String phone;

    private String eRole;
}
