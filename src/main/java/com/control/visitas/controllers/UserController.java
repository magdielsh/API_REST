package com.control.visitas.controllers;

import com.control.visitas.dtos.CreateUserDTO;
import com.control.visitas.dtos.UserDTO;
import com.control.visitas.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/findUser")
    public ResponseEntity<UserDTO> findUserByUserName (
            @RequestParam(name = "userName", defaultValue = "") String userName){

        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserByUserName(userName));
    }

    @PostMapping("/saveUser")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(createUserDTO));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO){

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDTO));
    }

    @DeleteMapping("deleteUser/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId){

        userService.deleteUser(userId);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Usuario Eliminado");
        result.put("usuario con ID: ", userId.toString());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
