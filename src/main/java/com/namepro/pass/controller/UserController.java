package com.namepro.pass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.namepro.pass.model.User;
import com.namepro.pass.model.UserPronunciation;
import com.namepro.pass.model.UserPronunciationDTO;
import com.namepro.pass.repository.UserRepository;
import com.namepro.pass.service.JwtUserDetailsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

    @RequestMapping(value = "/user/recording", method = RequestMethod.POST)
    public ResponseEntity<?> savePronunciation(@RequestBody UserPronunciationDTO user) throws Exception {
        userService.savePronunciation(user);
        return new ResponseEntity<>("Success", HttpStatus.OK);
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

}
