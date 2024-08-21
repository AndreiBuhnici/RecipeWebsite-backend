package com.summer_project_backend.controller;

import com.summer_project_backend.dto.UserAddDTO;
import com.summer_project_backend.dto.UserDTO;
import com.summer_project_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<UserDTO>> getUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size,
                                                  @RequestParam(required = false) String search) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, search));
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addUser(@RequestBody UserAddDTO userAddDTO) {
        userService.addUser(userAddDTO);
        return ResponseEntity.ok("User added successfully");
    }

    @GetMapping("/getCurrentUser")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/getUserById")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@RequestParam UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@RequestParam UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }
}
