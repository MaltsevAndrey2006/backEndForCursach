package andrey.dev.backendforcursach.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_transaction")
    private LocalDateTime dateOfTransaction;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "music_id")
    private Long musicId;
}
