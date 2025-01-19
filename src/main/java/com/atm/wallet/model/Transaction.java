package com.atm.wallet.model;

import com.atm.wallet.enums.Categories;
import com.atm.wallet.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID accountId;
    private TransactionType type;
    private BigDecimal amount;
    private Categories category;

    @CreatedDate
    private LocalDateTime createdAt;
}
