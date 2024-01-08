package com.committers.snowflowerthon.domain.item.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private Long id; // 아이템 고유 번호
    
    @Column(nullable = false)
    private Long snowId; // 눈송이
    
    @Column(nullable = false)
    private Long hatId; // 모자
    
    @Column(nullable = false)
    private Long decoId; // 장식
}
