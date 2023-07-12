package com.kerahnBankingApplication.kerahnBankingApplication.entity;

import com.kerahnBankingApplication.kerahnBankingApplication.KerahnBankingApplication;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KerahnBankingApplication")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private BigDecimal accountBalance;
    private String accountNumber;
    private String phoneNumber;
    private String nextOfKin;
    private String stateOfOrigin;
    private LocalDate dateOfBirth;
    private String city;
    private String status;
    private Boolean deleteStatus;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
