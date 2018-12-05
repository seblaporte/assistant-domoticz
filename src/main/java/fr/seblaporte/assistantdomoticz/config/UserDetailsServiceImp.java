package fr.seblaporte.assistantdomoticz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Value("${oauth.password}")
    private String userPassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserBuilder builder = User.withUsername(username);
        builder.password(new BCryptPasswordEncoder().encode(userPassword));
        builder.roles("USER");

        return builder.build();
    }


}
