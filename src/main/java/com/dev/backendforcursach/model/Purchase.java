package com.dev.backendforcursach.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "date_of_transaction")
    private LocalDateTime dateOfTransaction;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "music_id")
    private Long musicId;
}
