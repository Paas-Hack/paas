package com.namepro.pass.controller;

import com.namepro.pass.model.User;
import com.namepro.pass.model.UserDTO;
import com.namepro.pass.model.UserPronunciation;
import com.namepro.pass.model.UserPronunciationDTO;
import com.namepro.pass.repository.UserRepository;
import com.namepro.pass.service.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/users/{userId}")
    public List<User> getUsersByName(@PathVariable String userId) {
        List<User> result = userService.findByName(userId);
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


}
