package co.istad.Banking.api.user;

import co.istad.Banking.api.user.web.Authority;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements GrantedAuthority {

    private Integer id;
    private  String name;

    Set<Authority> authorities;

    @Override
    public String getAuthority() {
        return  "ROLE_" + name;
    }
}
