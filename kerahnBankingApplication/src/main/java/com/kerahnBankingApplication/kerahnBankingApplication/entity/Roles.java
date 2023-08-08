package com.kerahnBankingApplication.kerahnBankingApplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Enumerated(EnumType.STRING)
    private String  roleName;

}
