package ru.practicum.shareit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.dto.user.UserCreateDto;
import ru.practicum.shareit.dto.user.UserDto;
import ru.practicum.shareit.dto.user.UserUpdateDto;
import ru.practicum.shareit.exeptions.AlreadyExistsException;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.mappers.UserMapping;
import ru.practicum.shareit.models.User;
import ru.practicum.shareit.repositoryes.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapping userMapping;

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        return userMapping.toDto(findUserById(id));
    }

    public UserDto createUser(UserCreateDto userDto) {
        checkEmail(userDto.getEmail());
        User user = userMapping.fromDto(userDto);
        return userMapping.toDto(userRepository.save(user));
    }

    public UserDto updateUser(Long id, UserUpdateDto userDto) {
        User user = findUserById(id);
        if (userDto.getEmail() != null && !user.getEmail().equals(userDto.getEmail())) {
            checkEmail(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null && !userDto.getName().equals(user.getName())) {
            user.setName(userDto.getName());
        }
        return userMapping.toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private void checkEmail(String email) {
        Optional<User> maybeUser = userRepository.findByEmail(email);
        if (maybeUser.isPresent()) {
            String errorMessage = String.format("Пользователь с email '%s' уже существует", email);
            throw new AlreadyExistsException(errorMessage);
        }
    }
}