package co.istad.Banking.security;

import co.istad.Banking.api.auth.AuthMapper;
import co.istad.Banking.api.user.Role;
import co.istad.Banking.api.user.User;
import co.istad.Banking.api.user.web.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authMapper.loadUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not valid."));
        log.info("User:{}",user.getRoles());
        for(Role role:user.getRoles()){
            for(Authority authority:role.getAuthorities()){
                System.out.println(authority.getName());
            }
        }

        CustomUserDetails customUserDetail = new CustomUserDetails();
        customUserDetail.setUser(user);
        log.info("Custom User Details:{}",customUserDetail.getAuthorities());

        return customUserDetail;
    }
}
