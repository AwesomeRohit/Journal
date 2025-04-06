package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repo.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void saveAll(User userEntry){
        userRepository.save(userEntry);

    }
    public List<User> getAll(){
        return  userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id){
        return Optional.ofNullable(userRepository.findById(id).orElse(null));
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public User findByUserName(String username){
        return  userRepository.findByUsername(username);

    }
}
