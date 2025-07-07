package com.example.springbootpractice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "manufacturer")
@Comment("제조사 정보 테이블")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ManufacturerEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "name", nullable = false)
    @Comment("제조사 명")
    private String name;

    @Column(name = "is_active", nullable = false)
    @Comment("사용 유무")
    private Boolean isActive;

}
