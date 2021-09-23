package com.giovanni.bettingapp.mapper;

import com.giovanni.bettingapp.dto.UserDto;
import com.giovanni.bettingapp.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> users);
}
