package com.example.springbootpractice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "product_stock")
@Comment("상품 재고 정보 테이블")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ProductStockEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "product_seq", nullable = false)
    @Comment("상품 seq")
    private Long productSeq;

    @Column(name = "count", nullable = false)
    @Comment("상품 재고 수량")
    private long count;

}
