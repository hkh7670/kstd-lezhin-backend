package com.example.springbootpractice.model.entity;

import com.example.springbootpractice.model.dto.CreateProductRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "product")
@Comment("상품 정보 테이블")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ProductEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "name", nullable = false)
    @Comment("상품 명")
    private String name;

    @Column(name = "code", nullable = false)
    @Comment("상품 코드")
    private String code;

    @Column(name = "release_date", nullable = false)
    @Comment("출시일")
    private LocalDate releaseDate;

    @Column(name = "price", nullable = false)
    @Comment("가격")
    private Integer price;

    @Column(name = "is_active", nullable = false)
    @Comment("사용 유무")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_seq")
    private ManufacturerEntity manufacturer;

    public static ProductEntity of(
        CreateProductRequest request,
        ManufacturerEntity manufacturer
    ) {
        return ProductEntity.builder()
            .name(request.name())
            .code(request.code())
            .releaseDate(request.releaseDate())
            .price(request.price())
            .manufacturer(manufacturer)
            .isActive(true)
            .build();
    }
}
