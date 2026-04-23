package com.control.visitas.services;

import com.control.visitas.dtos.CreateUserDTO;
import com.control.visitas.dtos.UserDTO;
import com.control.visitas.models.entities.Role;
import com.control.visitas.models.entities.UserEntity;
import com.control.visitas.models.enums.ERole;
import com.control.visitas.repository.UserRepository;
import com.control.visitas.services.interfaces.IUserInterface;
import com.control.visitas.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserInterface {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findUserByUserName(String userName) {

        return Mapper.userToDTO(userRepository.findByEmail(userName)
                .orElseThrow(() -> new RuntimeException("Usuario no Encontrado desde UserService")));
    }

    @Override
    @Transactional
    public UserDTO saveUser(CreateUserDTO createUserDTO) {

        Set<Role> roles = createUserDTO.getRoles()
                .stream()
                .map(role -> Role.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());


        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        return Mapper.userToDTO(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public UserDTO updateUser(UserDTO userDTO) {

        UserEntity user = userRepository.findByEmail(userDTO.getUsername()).get();

        Set<Role> roles = userDTO.getRoles()
                .stream()
                .map(role -> Role.builder()
                        .id(role.getId())
                        .name(ERole.valueOf(role.getName().name()))
                        .build())
                .collect(Collectors.toSet());

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRoles(roles);

        return Mapper.userToDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {

        userRepository.deleteById(userId);

    }
}
