package com.example.multiservice.configuration;

import com.example.multiservice.entity.RoleEntity;
import com.example.multiservice.entity.UserEntity;
 import com.example.multiservice.exception.enums.UserRole;
import com.example.multiservice.repository.RolePermissionRepository;
import com.example.multiservice.repository.UserRepository;
 import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {


     PasswordEncoder passwordEncoder ;

     @Bean
     @ConditionalOnProperty(prefix = "spring",value = "datasource.driver-class-name",havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RolePermissionRepository rolePermissionRepository){

         log.info(" Running in ApplicationInitConfig");
        return args -> {
            var role = UserRole.ADMIN.getId();
            if (userRepository.findByEmail("admin@admin.com").isEmpty()){
                UserEntity userEntity = UserEntity.builder()
                        .email("admin@admin.com")
                        .password_hash(passwordEncoder.encode("admin"))
                        // set id Role
                        .registered_at(LocalDateTime.now())
                        .active(1)
                        .build();
                userRepository.save(userEntity);

                //userRoleRepository.insertUserRole(userEntity.getId(),1);


                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }

}
