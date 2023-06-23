package ru.practicum.main.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.model.User;

@UtilityClass
public
class UserMapper {

    public User NewUserRequestToUser(NewUserRequest newUserRequest) {
        return new User(
                newUserRequest.getEmail(),
                newUserRequest.getName()
        );
    }
}
