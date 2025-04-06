package net.engineeringdigest.journalApp.controllers;

import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {


    @Autowired
    JournalEntryService journalEntryService;
    @Autowired
    UserService userService;

    @GetMapping("/getJournals/{username}")
    public ResponseEntity<?> getAll( @PathVariable String username){
        User user = userService.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntries();
        if( all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>("No data found for this username --> " + username , HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> CreateEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
        try {
            myEntry.setDate(LocalDateTime.now());
            User user = userService.findByUserName(username);
            journalEntryService.saveAll(myEntry, username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}") // Added leading slash for correct path mapping
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);

        return journalEntry
                .map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)) // Return ResponseEntity directly
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{username}/{id}")
    public ResponseEntity<JournalEntry> DeleteById(@PathVariable String username, @PathVariable ObjectId id) {
         try {
             journalEntryService.deleteById(username,id);
             User user = userService.findByUserName(username);
             return new ResponseEntity<>(HttpStatus.OK);
         }catch (Exception e){
             return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }
   @PutMapping("id/ {username}/{myId}")
   public ResponseEntity<JournalEntry> update(@PathVariable ObjectId myId, @PathVariable String username, @RequestBody JournalEntry myEntry){
      try {
          JournalEntry old = journalEntryService.findById(myId).orElse(null);
          if(old != null){
              old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
              old.setDescription(myEntry.getDescription() != null && !myEntry.getTitle().equals("") ? myEntry.getDescription() : old.getDescription());
              journalEntryService.saveAll(old);
              return new ResponseEntity<>(old, HttpStatus.OK);
          }

      }catch (Exception e){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
     return null;
   
    } 
}

