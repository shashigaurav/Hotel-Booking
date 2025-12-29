package org.hotel.hotelbookingsystem.service.imp;

import lombok.RequiredArgsConstructor;
import org.hotel.hotelbookingsystem.dto.UserDto;

import org.hotel.hotelbookingsystem.dto.UserReqSignUp;
import org.hotel.hotelbookingsystem.model.User;
import org.hotel.hotelbookingsystem.repository.UserRepo;
import org.hotel.hotelbookingsystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

   @Override
    public List<UserDto> getAllUsers() {
       List<User> users = userRepo.findAll();
       List<UserDto> userDtos = users
                                .stream()
                                .map(user ->new UserDto(user.getId(),user.getFullName(),user.getEmail()))
                                .toList();
       return userDtos;

   }

   @Override
    public UserDto createNewUser(UserReqSignUp userReqSignUp) {
       User newUser = modelMapper.map(userReqSignUp,User.class);
       String encodedPassword = encoder.encode(userReqSignUp.getPassword());
       newUser.setPassword(encodedPassword);
       newUser.setStatus("ACTIVE");
       User user = userRepo.save(newUser);
       return modelMapper.map(user,UserDto.class);
   }

   @Override
    public UserDto getUserById(Long id) {
       User user = userRepo.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("User not found"));
       return modelMapper.map(user,UserDto.class);

   }

    @Override
    public UserDto login(String email, String password) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean matches = encoder.matches(password,user.getPassword());
        if(!matches) {
            throw new RuntimeException("Invalid password");
        }

        return modelMapper.map(user,UserDto.class);


    }

    @Override
    public UserDto getProfile(String email) {
       User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
       return modelMapper.map(user,UserDto.class);
    }

}
