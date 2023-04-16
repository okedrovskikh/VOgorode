package ru.tinkoff.academy.user;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.account.AccountService;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class UserMapper {
    protected final AccountService accountService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", expression = "java(accountService.findAllByIds(createDto.getAccountsId()))")
    public abstract User dtoToUser(UserCreateDto createDto);

    public User updateUser(User user, UserUpdateDto updateDto) {
        user.setName(updateDto.getName());
        user.setSurname(updateDto.getSurname());
        user.setSkills(updateDto.getSkills());
        user.setEmail(updateDto.getEmail());
        user.setTelephone(updateDto.getTelephone());
        user.setAccounts(accountService.findAllByIds(updateDto.getAccountsId()));
        user.setPhoto(updateDto.getPhoto());
        return user;
    }
}
