package com.example.multiservice.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity(name = "invalidate_token")
public class InvalidateToken {

    @Id
    String id;

    @Column(name = "expiry_date")
    Date expiryDate;
}
