package com.teashop.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "brewing_guide")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrewingGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "water_temperature", nullable = false)
    private Integer waterTemperature;

    @Column(name = "tea_amount", nullable = false, precision = 5, scale = 2)
    private BigDecimal teaAmount;

    @Column(name = "brewing_time", nullable = false)
    private Integer brewingTime;

    @Column(name = "brewing_times", nullable = false)
    private Integer brewingTimes;

    @Column(name = "tea_set_recommendation")
    private String teaSetRecommendation;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
