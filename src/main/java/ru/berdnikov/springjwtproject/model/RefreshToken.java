package ru.berdnikov.springjwtproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author danilaberdnikov on RefreshToken.
 * @project SpringJWTProject
 */
@Data
@Entity
@Builder
@Table(name = "refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {
    @Id
    private String id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "value", nullable = false, unique = true)
    private String value;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;
}
