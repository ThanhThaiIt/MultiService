package com.example.multiservice.mapper;

import com.example.multiservice.dto.request.PermissionRequest;
import com.example.multiservice.dto.request.PermissionUpdRequest;
import com.example.multiservice.dto.response.PermissionResponse;
import com.example.multiservice.entity.PermissionEntity;
import com.example.multiservice.utils.DateTimeUtils;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-24T11:32:36+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Autowired
    private DateTimeUtils dateTimeUtils;

    @Override
    public PermissionEntity toPermissionEntity(PermissionRequest permissionRequest) {
        if ( permissionRequest == null ) {
            return null;
        }

        PermissionEntity permissionEntity = new PermissionEntity();

        permissionEntity.setCreated_at( dateTimeUtils.parseStringToLocalDateTime( permissionRequest.created_at() ) );
        permissionEntity.setUpdated_at( dateTimeUtils.parseStringToLocalDateTime( permissionRequest.updated_at() ) );
        permissionEntity.setTitle( permissionRequest.title() );
        permissionEntity.setSlug( permissionRequest.slug() );
        permissionEntity.setDescription( permissionRequest.description() );
        permissionEntity.setContent( permissionRequest.content() );

        return permissionEntity;
    }

    @Override
    public PermissionEntity toPermissionEntityWithId(PermissionUpdRequest permissionUpdRequest) {
        if ( permissionUpdRequest == null ) {
            return null;
        }

        PermissionEntity permissionEntity = new PermissionEntity();

        permissionEntity.setCreated_at( dateTimeUtils.parseStringToLocalDateTime( permissionUpdRequest.created_at() ) );
        permissionEntity.setUpdated_at( dateTimeUtils.parseStringToLocalDateTime( permissionUpdRequest.updated_at() ) );
        permissionEntity.setId( permissionUpdRequest.id() );
        permissionEntity.setTitle( permissionUpdRequest.title() );
        permissionEntity.setSlug( permissionUpdRequest.slug() );
        permissionEntity.setDescription( permissionUpdRequest.description() );
        permissionEntity.setContent( permissionUpdRequest.content() );

        return permissionEntity;
    }

    @Override
    public PermissionResponse toPermissionResponse(PermissionEntity permissionEntity) {
        if ( permissionEntity == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.id( permissionEntity.getId() );
        permissionResponse.title( permissionEntity.getTitle() );
        permissionResponse.slug( permissionEntity.getSlug() );
        permissionResponse.description( permissionEntity.getDescription() );
        permissionResponse.created_at( permissionEntity.getCreated_at() );
        permissionResponse.updated_at( permissionEntity.getUpdated_at() );
        permissionResponse.content( permissionEntity.getContent() );

        return permissionResponse.build();
    }
}
