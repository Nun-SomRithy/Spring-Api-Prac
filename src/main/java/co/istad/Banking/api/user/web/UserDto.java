package co.istad.Banking.api.user.web;

import co.istad.Banking.api.user.Role;
import lombok.Builder;

import java.util.List;


public record UserDto(String name,
                      String gender,
                      String studentCardId,
                      Boolean isStudent,
                      List<Role> roles

) {

}
