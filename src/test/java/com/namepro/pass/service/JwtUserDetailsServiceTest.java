package com.namepro.pass.service;

import com.namepro.pass.model.User;
import com.namepro.pass.model.UserDTO;
import com.namepro.pass.model.UserLogin;
import com.namepro.pass.model.UserPronunciationDTO;
import com.namepro.pass.repository.UserLoginRepository;
import com.namepro.pass.repository.UserPronunciationRepository;
import com.namepro.pass.repository.UserRepository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    @InjectMocks
    JwtUserDetailsService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLoginRepository userLoginRepository;

    @Mock
    UserPronunciationRepository userPronunciationRepository;

    @Test
    void loadUserByUsername() {
        User user = getUser();
        UserLogin loginUser = getUserLogin(user);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userLoginRepository.findByUser(user)).thenReturn(loginUser);
        UserDetails userDetails = userService.loadUserByUsername("test");
        Assertions.assertEquals(userDetails.getUsername(), "test");
    }

    @Test
    void testNoUserFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails userDetails = userService.loadUserByUsername("test");
        });
    }

    @Test
    void testLoadUserByUsername() {
        User user = getUser();
        UserLogin loginUser = getUserLogin(user);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userLoginRepository.findByUser(any())).thenReturn(loginUser);
        when(userLoginRepository.save(any())).thenReturn(loginUser);
        UserDetails userDetails = userService.loadUserByUsername("test", true);
        Assertions.assertEquals(userDetails.getUsername(), "test");
    }

    @Test
    void saveUser() {
        UserDTO userDto = new UserDTO();
        userDto.setUsername("test");
        userDto.setPassword("testpass");
        User user = getUser();
        UserLogin loginUser = getUserLogin(user);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userLoginRepository.save(any())).thenReturn(loginUser);
        UserLogin login = userService.saveUser(userDto);
        Assertions.assertEquals(login.getUser().getFirstName(), "testFN");
    }

    @Test
    void saveLoginDetails() {
        User user = getUser();
        userService.saveLoginDetails(user, true);
    }

    @Test
    void findByName() {
        userService.findBySearchParam("2002440");
    }

    //@Test
    void savePronunciation() throws IOException {
        UserPronunciationDTO pronunciationDTO = new UserPronunciationDTO();
        pronunciationDTO.setUsername("testUser");
        pronunciationDTO.setPrimary(true);
        pronunciationDTO.setRecording("DEMO".getBytes(StandardCharsets.UTF_8));
        User user = getUser();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        userService.savePronunciation(pronunciationDTO, null);
    }

    @Test
    void getRecordings() {
        User user = getUser();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        userService.getRecordings("2002440");
    }

    @Test
    void deletePronunciation() {
        User user = getUser();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        userService.deletePronunciation("2002440", 1L);
    }

    private User getUser() {
        User user = new User();
        user.setAdmin(false);
        user.setUid("2002440");
        user.setFirstName("testFN");
        user.setLastName("testLn");
        user.setLanId("U849298");
        return user;
    }

    private UserLogin getUserLogin(User user) {
        UserLogin loginUser = new UserLogin();
        loginUser.setUser(user);
        loginUser.setLoggedIn(true);
        loginUser.setPassword("testpass");
        return loginUser;
    }
}