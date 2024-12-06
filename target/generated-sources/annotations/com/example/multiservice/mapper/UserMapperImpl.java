package com.example.multiservice.mapper;

import com.example.multiservice.dto.request.RegisterRequest;
import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.request.UserUpdateRequest;
import com.example.multiservice.dto.response.RoleResponse;
import com.example.multiservice.dto.response.UserResponse;
import com.example.multiservice.entity.RoleEntity;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.utils.DateTimeUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-01T14:13:51+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private DateTimeUtils dateTimeUtils;

    @Override
    public UserEntity toUser(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.registered_at( dateTimeUtils.parseStringToLocalDateTime( userRequest.registered_at() ) );
        userEntity.last_login( dateTimeUtils.parseStringToLocalDateTime( userRequest.last_login() ) );
        userEntity.first_name( userRequest.first_name() );
        userEntity.middle_name( userRequest.middle_name() );
        userEntity.last_name( userRequest.last_name() );
        userEntity.dob( userRequest.dob() );
        userEntity.mobile( userRequest.mobile() );
        userEntity.email( userRequest.email() );
        userEntity.password_hash( userRequest.password_hash() );
        userEntity.intro( userRequest.intro() );
        userEntity.bio( userRequest.bio() );
        userEntity.avatar_url( userRequest.avatar_url() );
        userEntity.social_links( userRequest.social_links() );

        return userEntity.build();
    }

    @Override
    public UserEntity toUserUpdate(UserUpdateRequest userUpdateRequest) {
        if ( userUpdateRequest == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.registered_at( dateTimeUtils.parseStringToLocalDateTime( userUpdateRequest.registered_at() ) );
        userEntity.last_login( dateTimeUtils.parseStringToLocalDateTime( userUpdateRequest.last_login() ) );
        userEntity.id( userUpdateRequest.id() );
        userEntity.first_name( userUpdateRequest.first_name() );
        userEntity.middle_name( userUpdateRequest.middle_name() );
        userEntity.last_name( userUpdateRequest.last_name() );
        userEntity.dob( userUpdateRequest.dob() );
        userEntity.mobile( userUpdateRequest.mobile() );
        userEntity.email( userUpdateRequest.email() );
        userEntity.password_hash( userUpdateRequest.password_hash() );
        userEntity.intro( userUpdateRequest.intro() );
        userEntity.bio( userUpdateRequest.bio() );
        userEntity.avatar_url( userUpdateRequest.avatar_url() );
        userEntity.social_links( userUpdateRequest.social_links() );
        userEntity.active( userUpdateRequest.active() );

        return userEntity.build();
    }

    @Override
    public UserEntity toRegisterUser(RegisterRequest registerRequest) {
        if ( registerRequest == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.dob( dateTimeUtils.parseStringToLocalDate( registerRequest.dob() ) );
        userEntity.first_name( registerRequest.first_name() );
        userEntity.middle_name( registerRequest.middle_name() );
        userEntity.last_name( registerRequest.last_name() );
        userEntity.mobile( registerRequest.mobile() );
        userEntity.email( registerRequest.email() );
        userEntity.intro( registerRequest.intro() );
        userEntity.bio( registerRequest.bio() );
        userEntity.social_links( registerRequest.social_links() );

        return userEntity.build();
    }

    @Override
    public UserResponse toUserResponse(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( userEntity.getId() );
        userResponse.first_name( userEntity.getFirst_name() );
        userResponse.middle_name( userEntity.getMiddle_name() );
        userResponse.last_name( userEntity.getLast_name() );
        userResponse.mobile( userEntity.getMobile() );
        userResponse.email( userEntity.getEmail() );
        userResponse.dob( userEntity.getDob() );
        userResponse.registered_at( userEntity.getRegistered_at() );
        userResponse.last_login( userEntity.getLast_login() );
        userResponse.intro( userEntity.getIntro() );
        userResponse.bio( userEntity.getBio() );
        userResponse.avatar_url( userEntity.getAvatar_url() );
        userResponse.social_links( userEntity.getSocial_links() );
        userResponse.roles( roleEntityListToRoleResponseList( userEntity.getRoles() ) );

        return userResponse.build();
    }

    protected RoleResponse roleEntityToRoleResponse(RoleEntity roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.id( roleEntity.getId() );
        roleResponse.title( roleEntity.getTitle() );
        roleResponse.slug( roleEntity.getSlug() );
        roleResponse.description( roleEntity.getDescription() );
        roleResponse.active( roleEntity.getActive() );
        roleResponse.created_at( roleEntity.getCreated_at() );
        roleResponse.updated_at( roleEntity.getUpdated_at() );
        roleResponse.content( roleEntity.getContent() );

        return roleResponse.build();
    }

    protected List<RoleResponse> roleEntityListToRoleResponseList(List<RoleEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleResponse> list1 = new ArrayList<RoleResponse>( list.size() );
        for ( RoleEntity roleEntity : list ) {
            list1.add( roleEntityToRoleResponse( roleEntity ) );
        }

        return list1;
    }
}
