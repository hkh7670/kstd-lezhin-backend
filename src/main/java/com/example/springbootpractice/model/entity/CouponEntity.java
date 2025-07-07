package com.example.springbootpractice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "coupon")
@Comment("쿠폰 정보 테이블")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class CouponEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "name", nullable = false)
    @Comment("쿠폰 명")
    private String name;

    @Column(name = "discount_type", nullable = false)
    @Comment("할인 타입") // RATE: 비율, PRICE: 가격
    private String discountType;

    @Column(name = "discount_rate")
    @Comment("할인 비율 (%)")
    private Integer discountRate;

    @Column(name = "discount_price")
    @Comment("할인 가격")
    private Integer discountPrice;

    @Column(name = "valid_days", nullable = false)
    @Comment("유효 기간")
    private int validDays;

    @Column(name = "is_active", nullable = false)
    @Comment("사용 유무")
    private Boolean isActive;

}
