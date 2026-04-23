package com.control.visitas.services.interfaces;

import com.control.visitas.dtos.CreateUserDTO;
import com.control.visitas.dtos.UserDTO;

public interface IUserInterface {

   UserDTO findUserByUserName (String userName);

   UserDTO saveUser(CreateUserDTO createUserDTO);

   UserDTO updateUser(UserDTO userDTO);

   void deleteUser(Long userId);
}
