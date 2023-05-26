package co.istad.Banking.api.auth;

import co.istad.Banking.api.auth.web.AuthDto;
import co.istad.Banking.api.auth.web.LoginDto;
import co.istad.Banking.api.auth.web.RegisterDto;
import org.springframework.stereotype.Service;



public interface AuthService {
    void   register(RegisterDto registerDto);


    void verify(String email);


    void checkVerify(String email, String verifiedCode);


    AuthDto login(LoginDto loginDto);

}
