package com.example.minicase.service.user.response;
import com.example.minicase.model.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Getter
@Setter
@Builder
public class UserListResponse implements Validator {
    private Long id;
    private String name;
    private String userName;
    private String email;
    private String phone;
    private String dob;
    private String role;
    private Integer customerScore;
    private Integer customerHeart;
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
    @Override
    public void validate(Object target, Errors errors) {
    }
}
