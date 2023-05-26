package co.istad.Banking.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    //    private final PasswordEncoder  encoder ;
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    //define in memory user
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails user = User.builder()
//                .username("admin")
//                .password(encoder.encode("1"))
//                .roles("ADMIN")
//                .build();
//        UserDetails golduser = User.builder()
//                .username("gold")
//                .password(encoder.encode("1"))
//                .roles("ACCOUNT")
//                .build();
//
//        userDetailsManager.createUser(user);
//        userDetailsManager.createUser(golduser);
//        return userDetailsManager;
//    }


    //define  security with   jdbc

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Disable csrf

        http.csrf(AbstractHttpConfigurer::disable);
//        http.csrf(token -> token.disable());
        http.authorizeHttpRequests(request -> {
            //Authorize URL mapping
            request.requestMatchers("/api/v1/auth/**").permitAll();
//            request.requestMatchers(HttpMethod.GET, "/api/v1/users/").hasAnyAuthority("SCOPE_ROLE_ADMIN");
//            request.requestMatchers(HttpMethod.POST, "/api/v1/users/")
//            .hasAnyAuthority("SCOPE_ROLE_CUSTOMER", "SCOPE_ROLE_SYSTEM");
//            request.requestMatchers(HttpMethod.GET, "/api/v1/users/").hasAnyAuthority("SCOPE_READ", "SCOPE_FULL_CONTROL");
//            request.requestMatchers(HttpMethod.POST, "/api/v1/users/").hasAnyAuthority("SCOPE_WRITE", "SCOPE_FULL_CONTROL");
//            request.requestMatchers(HttpMethod.PUT, "/api/v1/users/").hasAnyAuthority("SCOPE_UPDATE", "SCOPE_FULL_CONTROL");
//            request.requestMatchers(HttpMethod.DELETE, "/api/v1/users/").hasAnyAuthority("SCOPE_DELETE", "SCOPE_FULL_CONTROL");
            request.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyAuthority("SCOPE_user:read");
            request.requestMatchers(HttpMethod.POST,"/api/v1/users/**").hasAnyAuthority("SCOPE_user:write");
            request.requestMatchers(HttpMethod.PUT,"/api/v1/users/**").hasAnyAuthority("SCOPE_user:delete");
            request.requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasAnyAuthority("SCOPE_user:update");
            request.requestMatchers(HttpMethod.GET,"/api/v1/users/**").hasAnyAuthority("SCOPE_account:read");
            request.anyRequest().authenticated();
        });


        //Security mechanism
        //   http.httpBasic();
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt());


        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //excepttion status code
        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);

        //http.exceptionHandling().defaultAccessDeniedHandlerFor(jwtFailureEvents);

        // make security stateless
//                .formLog/in(); // using for normal web without api
        return http.build();
    }

    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator= KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }


    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        JWKSet jwkSet= new JWKSet(rsaKey);
        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
    }




    @Bean
    JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }


    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }





}
