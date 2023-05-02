package com.terrylt.todoSpringVue.appuser;

import com.terrylt.todoSpringVue.appuser.email.EmailValidator;
import com.terrylt.todoSpringVue.appuser.password.PasswordValidator;
import com.terrylt.todoSpringVue.registration.token.ConfirmationToken;
import com.terrylt.todoSpringVue.registration.token.ConfirmationTokenRepository;
import com.terrylt.todoSpringVue.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final AppUserRepository appUserRepository;

    private final ConfirmationTokenService confirmationTokenService;

    private final EmailValidator emailValidator;

    private final PasswordValidator passwordValidator;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }
    public void enableUser(String email){
        appUserRepository.enableAppUser(email);
    }

    public String signUpUser(AppUser appUser){
        //Check if user exists
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists){
            throw new IllegalStateException("The user with such email already exists!");
        }
        //Check if email is valid
        boolean emailIsValid = emailValidator.test(appUser.getEmail());
        if (!emailIsValid){
            throw new IllegalStateException("The given email is not valid!");
        }
        //Check if password is valid
        boolean passwordIsValid = passwordValidator.test(appUser.getPassword());
        if (!passwordIsValid){
            throw new IllegalStateException("The given password is not valid!");
        }
        //Encode password and save password
        appUser.setPassword(
                bCryptPasswordEncoder.encode(appUser.getPassword())
        );
        //Save user
        appUserRepository.save(appUser);
        //Generate token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        //Save token in database
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        //Return token string
        return token;
    }
}
