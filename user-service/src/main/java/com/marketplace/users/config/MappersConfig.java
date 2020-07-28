package com.marketplace.users.config;

import com.marketplace.users.domain.UserEntity;
import com.marketplace.users.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class MappersConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public ModelMapper userToUserDto() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(UserEntity.class, UserDto.class)
                .addMappings(mapping -> mapping.skip(UserDto::setPassword));

        return modelMapper;
    }

    @Bean
    public ModelMapper userDtoToUser() {
        ModelMapper modelMapper = new ModelMapper();
        Converter<String, String> passwordEncoder = context -> bCryptPasswordEncoder.encode(context.getSource());
        modelMapper.typeMap(UserDto.class, UserEntity.class)
                .addMappings(mapping -> {
                    mapping.skip(UserEntity::setCreatedDate);
                    mapping.skip(UserEntity::setId);
                    mapping.skip(UserEntity::setLastModifiedDate);
                    mapping.skip(UserEntity::setRole);
                })
                .addMappings(mapping -> mapping.using(passwordEncoder)
                        .map(UserDto::getPassword, UserEntity::setBCryptPassword));

        return modelMapper;
    }
}
