package com.committers.snowflowerthon.committersserver.domain.item.entity;

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

    @Builder.Default
    @Column(nullable = false)
    private Long snowId = 0L; // 눈송이

    @Builder.Default
    @Column(nullable = false)
    private Long hatId = 0L; // 모자

    @Builder.Default
    @Column(nullable = false)
    private Long decoId = 0L; // 장식
}
