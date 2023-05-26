package co.istad.Banking.api.auth;


import co.istad.Banking.api.auth.web.AuthDto;
import co.istad.Banking.api.auth.web.LoginDto;
import co.istad.Banking.api.auth.web.RegisterDto;
import co.istad.Banking.api.user.User;
import co.istad.Banking.api.user.UserMapStruct;
import co.istad.Banking.util.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private final MailUtil mailUtil;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtEncoder jwtEncoder;

    @Value("${spring.mail.username}")
    private String appMail;

    @Transactional
    @Override
    public void register(RegisterDto registerDto) {
        User user = userMapStruct.registerDtoToUser(registerDto);
        user.setIsVerified(false);
        user.setPassword(encoder.encode(user.getPassword()));
        if ( authMapper.register(user)){
            System.out.println(user);
            //create user role
            for (Integer roleId : registerDto.roleIds()){
              authMapper.createUserRole(user.getId(),roleId);
            }
        }

    }


    @Override
    public void verify(String email) {
        System.out.println("email: "+email);
        System.out.println(authMapper.selectByEmail(email));

        User user = authMapper.selectByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Email is not Found"));
        System.out.println("2");

        String verifiedCode = UUID.randomUUID().toString();

        if (authMapper.updateVerifiedCode(email, verifiedCode)) {
            user.setVerifiedCode(verifiedCode);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User Cannot be Verified");
        }

        user.setVerifiedCode(verifiedCode);

        MailUtil.Meta<?> meta = MailUtil.Meta.builder()
                .to(email)
                .from(appMail)
                .templateUrl("auth/verify")
                .subject("Account Verify")
                .data(user)
                .build();

        try {
            mailUtil.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }


    @Override
    public void checkVerify(String email, String verifiedCode) {

        User user = authMapper.selectByEmailAndVerifiedCode(email, verifiedCode).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Is Not Found In DataBase"));

        if (!user.getIsVerified()) {
            authMapper.verify(email, verifiedCode);
        }


    }

    @Override
    public AuthDto login(LoginDto loginDto) {
        Authentication authentication=new UsernamePasswordAuthenticationToken(loginDto.email(),loginDto.password());
        authentication=daoAuthenticationProvider.authenticate(authentication);

        //Create time
//        Instant now= Instant.now();

        //Define SCope

//        String scope = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));

//        List<SimpleGrantedAuthority> authorities= new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("WRITE"));
//        authorities.add(new SimpleGrantedAuthority("UPDATE"));
//        authorities.add(new SimpleGrantedAuthority("READ"));
//        authorities.add(new SimpleGrantedAuthority("DELETE"));
//        authorities.add(new SimpleGrantedAuthority("FULL_CONTROL"));
        Instant now=Instant.now();
        //Define Scope
        String scope=authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth->!auth.startsWith("ROLE_"))
                .collect(Collectors.joining(" "));

//        String scope = authorities.stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();


        String  accessToken= jwtEncoder.encode(
                JwtEncoderParameters.from(jwtClaimsSet)
        ).getTokenValue();

        return new AuthDto("Bearer",accessToken);
    }
}
