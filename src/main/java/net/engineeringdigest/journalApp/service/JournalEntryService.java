package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repo.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    public JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;
    
    @Transactional
    public void saveAll(JournalEntry journalEntry, String username){
        try {
            User user = userService.findByUserName(username);
            journalEntryRepository.save(journalEntry);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            user.setJournalEntries(user.getJournalEntries());
            userService.saveAll(user);
        } catch (Exception e) {
           System.out.println("Error saving journal entry: " + e.getMessage());
           e.printStackTrace();
        } finally {
            // Optional cleanup or finalization code can go here
            System.out.println("Save operation completed.");
        }
    }

    public void saveAll(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    
    public List<JournalEntry> getAll(){
        return  journalEntryRepository.findAll();
    }
    
    public Optional<JournalEntry> findById(ObjectId id){
        return Optional.ofNullable(journalEntryRepository.findById(id).orElse(null));
    }
    
    public void deleteById(String username, ObjectId id){
        User user = userService.findByUserName(username);
        JournalEntry journalEntry = journalEntryRepository.findById(id).orElse(null);
        if (journalEntry != null) {
            user.getJournalEntries().remove(journalEntry);
            userService.saveAll(user);
            journalEntryRepository.deleteById(id);
        }
        
    }

}
