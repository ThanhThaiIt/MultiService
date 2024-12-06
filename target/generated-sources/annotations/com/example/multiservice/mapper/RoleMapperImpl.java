package com.example.multiservice.mapper;

import com.example.multiservice.dto.request.RoleRequest;
import com.example.multiservice.dto.request.RoleUpdRequest;
import com.example.multiservice.dto.response.RoleResponse;
import com.example.multiservice.entity.RoleEntity;
import com.example.multiservice.utils.DateTimeUtils;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-01T14:13:51+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Autowired
    private DateTimeUtils dateTimeUtils;

    @Override
    public RoleEntity roleToRoleEntity(RoleRequest roleRequest) {
        if ( roleRequest == null ) {
            return null;
        }

        RoleEntity.RoleEntityBuilder roleEntity = RoleEntity.builder();

        roleEntity.created_at( dateTimeUtils.parseStringToLocalDateTime( roleRequest.created_at() ) );
        roleEntity.updated_at( dateTimeUtils.parseStringToLocalDateTime( roleRequest.updated_at() ) );
        roleEntity.title( roleRequest.title() );
        roleEntity.slug( roleRequest.slug() );
        roleEntity.description( roleRequest.description() );
        roleEntity.active( roleRequest.active() );
        roleEntity.content( roleRequest.content() );

        return roleEntity.build();
    }

    @Override
    public RoleEntity roleUpdToRoleEntity(RoleUpdRequest roleUpdRequest) {
        if ( roleUpdRequest == null ) {
            return null;
        }

        RoleEntity.RoleEntityBuilder roleEntity = RoleEntity.builder();

        roleEntity.created_at( dateTimeUtils.parseStringToLocalDateTime( roleUpdRequest.created_at() ) );
        roleEntity.updated_at( dateTimeUtils.parseStringToLocalDateTime( roleUpdRequest.updated_at() ) );
        roleEntity.id( roleUpdRequest.id() );
        roleEntity.title( roleUpdRequest.title() );
        roleEntity.slug( roleUpdRequest.slug() );
        roleEntity.description( roleUpdRequest.description() );
        roleEntity.active( roleUpdRequest.active() );
        roleEntity.content( roleUpdRequest.content() );

        return roleEntity.build();
    }

    @Override
    public RoleResponse roleToRoleResponse(RoleEntity roleEntity) {
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
}
