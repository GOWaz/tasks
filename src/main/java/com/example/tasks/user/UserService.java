package com.example.tasks.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public Optional<User> getUserById(Long id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        } else {
            return userRepository.save(user);
        }
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(
                user -> {
                    user.setName(updatedUser.getName());
                    if (userRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
                        throw new RuntimeException("User with email " + updatedUser.getEmail() + " already exists");
                    } else {
                        user.setEmail(updatedUser.getEmail());
                    }
                    return userRepository.save(user);
                }
        ).orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }
}
