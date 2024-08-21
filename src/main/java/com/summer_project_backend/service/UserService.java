package com.summer_project_backend.service;

import com.summer_project_backend.dto.UserAddDTO;
import com.summer_project_backend.dto.UserDTO;
import com.summer_project_backend.dto.UserRegisterDTO;
import com.summer_project_backend.exception.IllegalAccessException;
import com.summer_project_backend.exception.UserAlreadyExistsException;
import com.summer_project_backend.exception.NotFoundException;
import com.summer_project_backend.model.Role;
import com.summer_project_backend.model.User;
import com.summer_project_backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.modelMapper = modelMapper;
    }

    public void addUser(UserAddDTO userAddDTO) {
        User existingUser = userRepository.findByEmail(userAddDTO.getEmail());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User with email " + userAddDTO.getEmail() + " already exists");
        }

        String hashedPassword = encoder.encode(userAddDTO.getPassword());
        User user = new User(userAddDTO.getName(), userAddDTO.getEmail(),
                hashedPassword, userAddDTO.getRole(), userAddDTO.isVerified(), new ArrayList<>());
        userRepository.save(user);
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        UserAddDTO userAddDTO = new UserAddDTO(userRegisterDTO.getPassword(), userRegisterDTO.getEmail(),
                userRegisterDTO.getName(), Role.USER, false);
        addUser(userAddDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    public Page<UserDTO> getAllUsers(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> pagedUsers;
        if (search == null)
            pagedUsers = userRepository.findAll(pageable);
        else
            pagedUsers = userRepository.findAllByNameContaining(search, pageable);

        return pagedUsers.map(user -> modelMapper.map(user, UserDTO.class));
    }

    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        return modelMapper.map(currentUser, UserDTO.class);
    }

    public UserDTO getUserById(UUID id) {
        return modelMapper.map(userRepository.findById(id), UserDTO.class);
    }

    public void deleteUser(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new NotFoundException("User doesn't exist");

        User u = user.get();
        if (u.getRole().equals(Role.ADMIN))
            throw new IllegalAccessException("Cannot delete an admin account");

        userRepository.deleteById(userId);
    }
}
