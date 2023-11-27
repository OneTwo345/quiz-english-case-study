package com.example.minicase.service.user;

import com.example.minicase.exception.ResourceNotFoundException;
import com.example.minicase.model.Customer;
import com.example.minicase.model.User;
import com.example.minicase.model.enums.ERole;
import com.example.minicase.repository.CustomerRepository;
import com.example.minicase.repository.UserRepository;
import com.example.minicase.service.CustomerService;
import com.example.minicase.service.user.request.UserCreateRequest;
import com.example.minicase.service.user.request.UserEditRequest;
import com.example.minicase.service.user.response.UserEditResponse;
import com.example.minicase.service.user.response.UserListResponse;
import com.example.minicase.util.AppMessage;
import com.example.minicase.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private CustomerRepository customerRepository;

    public Optional<User> findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(String loginName) {
//        return Optional.ofNullable(userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCaseOrPhoneNumber(loginName, loginName, loginName)
//                .orElseThrow(() -> new ResourceNotFoundException
//                        (String.format(AppMessage.ID_NOT_FOUND, "User"))));

        return Optional.ofNullable(userRepository.findUserByUsername(loginName)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "User"))));
    }



    public Optional<User> findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public Page<UserListResponse> findAllUser(String search, Pageable pageable){
        return userRepository.searchAllByUserName(search,pageable)
                .map(e->UserListResponse.builder()
                        .id(e.getId())
                        .name(e.getFullName())
                        .phone(e.getPhoneNumber())
                        .email(e.getEmail())
                        .dob(String.valueOf(e.getDob()))
                        .userName(e.getUsername())
                        .customerHeart(e.getCustomer().getHeart())
                        .customerScore(e.getCustomer().getScore())
                        .role(String.valueOf(e.getRole()))
                        .build());
    }
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (String.format(AppMessage.ID_NOT_FOUND, "User", id)));
    }
    public UserEditResponse showEdit(Long id){
        var user = findById(id);
        var userEditResponse = AppUtil.mapper.map(user, UserEditResponse.class);
        return userEditResponse;
    }
    public User create(UserCreateRequest userCreateRequest){
        User user = AppUtil.mapper.map(userCreateRequest, User.class);
        user.setRole(ERole.ROLE_USER);
        return userRepository.save(user);
    }
    public void update(UserEditRequest userEditRequest, Long id){
        var userDB = findById(id);
        userDB.setFullName(userEditRequest.getName());
        userDB.setPhoneNumber(userEditRequest.getPhone());
        userDB.setDob(LocalDate.parse(userEditRequest.getDob()));
        userRepository.save(userDB);
    }
    public void roleAdmin(Long id){
        User user = findById(id);
        user.setRole(ERole.ROLE_ADMIN);
        userRepository.save(user);
    }
    public void roleUser(Long id){
        User user = findById(id);
        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
    }

    public Optional<User> getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                String username = userDetails.getUsername();
                return findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(username);
            }
        }
        return Optional.empty();
    }

//    public CurrentUserResponse getCurrentUserDTO() {
//      Optional<User> user = this.getCurrentCustomer();
//      if(user.isPresent()){
//          return new CurrentUserResponse().setId(user.get().getId())
//                  .setName(user.get().getFullName());
//      }
//      return null;
//    }

    public void updateHeart(Integer heart, Long id){
        Customer customerDb = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer Not Found"));
        customerDb.setHeart(heart);
        customerRepository.save(customerDb);
    }
    public void updateScore(Integer score, Long id){
        Customer customerDb = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer Not Found"));
        customerDb.setScore(score);
        customerRepository.save(customerDb);
    }

    public void updateHeartAndScore(Integer heart, Integer score, Long id) {
        Customer customerDb = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer Not Found"));
       if(heart != null){
           customerDb.setHeart(heart);
       }
        if(score != null){
            customerDb.setScore(score);
        }
        customerRepository.save(customerDb);
    }
}
