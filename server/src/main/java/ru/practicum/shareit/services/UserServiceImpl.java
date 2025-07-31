package ru.practicum.shareit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.dto.UserCreateDto;
import ru.practicum.shareit.dto.UserUpdateDto;
import ru.practicum.shareit.exceptions.AlreadyExistsException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.mappers.UserMapper;
import ru.practicum.shareit.models.User;
import ru.practicum.shareit.repositoryes.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User createUser(UserCreateDto userCreateDto) {
        this.existsByEmail(userCreateDto.getEmail());
        return userRepository.save(UserMapper.INSTANCE.toUser(userCreateDto));
    }

    @Override
    @Transactional
    public User updateUser(long id, UserUpdateDto userUpdateDto) {
        User existingUser = getUserById(id);

        if (userUpdateDto.getName() != null) {
            existingUser.setName(userUpdateDto.getName());
        }

        if (userUpdateDto.getEmail() != null) {
            this.existsByEmail(userUpdateDto.getEmail());
            existingUser.setEmail(userUpdateDto.getEmail());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден"));
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public void existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("Пользователь с " + email + " уже существует");
        }
    }
}