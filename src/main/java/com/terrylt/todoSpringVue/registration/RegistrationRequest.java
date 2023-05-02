package com.terrylt.todoSpringVue.registration;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class RegistrationRequest {
    private final String username;
    private final String email;
    private final String password;

}
