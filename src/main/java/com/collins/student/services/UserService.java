package com.collins.student.services;

import com.collins.student.dtos.UserDto;
import com.collins.student.entities.User;
import com.collins.student.exceptions.EmailAlreadyExistsException;
import com.collins.student.exceptions.ResourceNotFoundException;
import com.collins.student.mappers.AutoUserMapper;
import com.collins.student.mappers.UserMapper;
import com.collins.student.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    public UserDto create(UserDto userDto){

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("This Email Already Exists!");
        }

        //convert UserDto into User JPA Entity
        // User user = UserMapper.mapToUser(userDto);
//        User user = modelMapper.map(userDto, User.class);


        User user = AutoUserMapper.MAPPER.mapToUser(userDto);

        User savedUser = userRepository.save(user);

        //Convert User JPA Entity to UserDto
        //UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);

//        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);

        UserDto savedUserDto = AutoUserMapper.MAPPER.mapToUserDto(savedUser);

        return savedUserDto;
    }

    public UserDto getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
//       return UserMapper.mapToUserDto(user);
//        return modelMapper.map(user, UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(user);
    }

    public List<UserDto> getAllUsers(){
        List<User> users = userRepository.findAll();
//        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());

//        return users.stream().map((user) -> modelMapper.map(user, UserDto.class))
//                .collect(Collectors.toList());

        return users.stream().map((user) -> AutoUserMapper.MAPPER.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    public UserDto updateUser(UserDto user){
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("This Email Already Exists!");
        }

        User existingUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", user.getId())
        );
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
//        return UserMapper.mapToUserDto(updatedUser);

//        return modelMapper.map(updatedUser, UserDto.class);

        return AutoUserMapper.MAPPER.mapToUserDto(updatedUser);
    }

    public void deleteUser(Long id){
        User ExistingUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        userRepository.deleteById(id);
    }
}

