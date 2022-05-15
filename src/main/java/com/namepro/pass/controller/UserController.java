package com.namepro.pass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.namepro.pass.model.User;
import com.namepro.pass.model.UserPronunciation;
import com.namepro.pass.model.UserPronunciationDTO;
import com.namepro.pass.repository.UserRepository;
import com.namepro.pass.service.JwtUserDetailsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SecurityRequirement(name = "namepro")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUserDetailsService userService;

    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public User getUsersByUserId(@PathVariable String userId) {
        return userService.findById(userId); 
    }
    
    @GetMapping("/users/{searchParam}")
    public List<User> getUsersByName(@PathVariable String searchParam) {
        List<User> result = userService.findBySearchParam(searchParam);
        return result;
    }

    @GetMapping("/user/{userId}/recording")
    public List<UserPronunciation> getRecordingsByUser(@PathVariable String userId) {
        return userService.getRecordings(userId);
    }

    @RequestMapping(value = "/user/{userId}/recording/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePronunciation(@PathVariable String userId, @PathVariable long id) throws Exception {
        userService.deletePronunciation(userId, id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}/subscribe/{substr}", method = RequestMethod.POST)
    public ResponseEntity<?> subscribeUser(@PathVariable String userId, @PathVariable boolean substr) throws Exception {
        userService.updateUser(userId, substr);
        return new ResponseEntity<>("Successfully Updated", HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}/primary/{isPrimary}/recording", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> savePronunciationAsFile(@PathVariable String userId, @PathVariable boolean isPrimary, @RequestPart ("file") MultipartFile file) throws Exception {
        UserPronunciationDTO user = new UserPronunciationDTO();
        user.setUsername(userId);
        user.setPrimary(isPrimary);
        userService.savePronunciation(user, file);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
