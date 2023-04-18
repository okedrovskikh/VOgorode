package ru.tinkoff.academy.user;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.account.AccountService;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Setter
    protected AccountService accountService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", expression = "java(createDto.getAccountsId() != null ? accountService.findAllByIds(createDto.getAccountsId()) : null)")
    public abstract User dtoToUser(UserCreateDto createDto);

    public User updateUser(User user, UserUpdateDto updateDto) {
        user.setName(updateDto.getName());
        user.setSurname(updateDto.getSurname());
        user.setSkills(updateDto.getSkills());
        user.setEmail(updateDto.getEmail());
        user.setTelephone(updateDto.getTelephone());
        if (updateDto.getAccountsId() != null) {
            user.setAccounts(accountService.findAllByIds(updateDto.getAccountsId()));
        } else {
            user.setAccounts(null);
        }
        user.setPhoto(updateDto.getPhoto());
        return user;
    }
}
