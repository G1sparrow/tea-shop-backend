package com.teashop.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tea_tasting")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaTasting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String fragrance;

    @Column(columnDefinition = "TEXT")
    private String taste;

    @Column(name = "steep_resistance")
    private Integer steepResistance;

    @Column(name = "suitable_tea_set")
    private String suitableTeaSet;

    @Column(name = "brewing_suggestion", columnDefinition = "TEXT")
    private String brewingSuggestion;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
