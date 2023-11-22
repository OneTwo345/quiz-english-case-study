package com.example.minicase.service.auth;

import com.example.minicase.model.User;
import com.example.minicase.model.enums.ERole;
import com.example.minicase.repository.UserRepository;
import com.example.minicase.service.auth.request.RegisterRequest;
import com.example.minicase.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;
@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request){
        var user = AppUtil.mapper.map(request, User.class);
        user.setRole(ERole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean checkUsernameOrPhoneNumberOrEmail(RegisterRequest request, BindingResult result){
        boolean check = false;
        if(userRepository.existsByUsernameIgnoreCase(request.getUsername())){
            result.rejectValue("username", "username", "Tên người dùng đã tồn tại!");
            check = true;
        }
        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            result.rejectValue("email", "email", "Email đã tồn tại!");
            check = true;
        }
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            result.rejectValue("phoneNumber","phoneNumber","Số điện thoại đã tồn tại!");
            check = true;
        }
        return check;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCaseOrPhoneNumber(username,username,username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Exist") );
        var role = new ArrayList<SimpleGrantedAuthority>();
        role.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), role);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
    public Optional<User> findByUserName(String username){
        return userRepository.findUserByUsername(username);
    }
}
