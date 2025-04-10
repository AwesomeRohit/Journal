package net.engineeringdigest.journalApp.service;

import java.util.ArrayList;
import net.engineeringdigest.journalApp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.repo.UserRepository;

@Component
public class UserDeatailService implements UserDetailsService {
    
        @Autowired
        private UserRepository userRepository;
        
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           
            User user = userRepository.findByUsername(username);
            if(user != null){
                UserDetails Build = org.springframework.security.core.userdetails.User.builder()
                        
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().toArray(new String[0]))
                        .accountExpired(false)
                        .accountLocked(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .build();

                        return Build;
            }
          throw new UsernameNotFoundException("User not found with username: " + username);
        }
}
