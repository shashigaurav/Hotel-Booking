package org.hotel.hotelbookingsystem.service;


import org.hotel.hotelbookingsystem.dto.UserDto;

import org.hotel.hotelbookingsystem.dto.UserReqSignUp;



import java.util.List;

public interface UserService {

  List<UserDto> getAllUsers();
  UserDto createNewUser( UserReqSignUp userReqSignUp);
  UserDto getUserById(Long id);

    UserDto login(String email, String password);

    UserDto getProfile(String email);
}
