package ru.practicum.main.user.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.practicum.main.user.dto.NewUserDto;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public
class UserMapper {

    public User newUserDtoToUser(NewUserDto newUserDto) {
        return new User(
                newUserDto.getEmail(),
                newUserDto.getName()
        );
    }

    public UserDto userToUserDto(User user) {
        return new UserDto(
                user.getEmail(),
                user.getId(),
                user.getName()
        );
    }

    public List<UserDto> listUserToListUserDto(List<User> userList) {
        return userList.stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> pageUserToListUserDto(Page<User> userPage) {
        return userPage.stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }
}
