package com.teashop.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tea_tracing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaTracing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private LocalDate harvestTime;

    @Column(columnDefinition = "TEXT")
    private String process;

    @Column(name = "tea_variety")
    private String teaVariety;

    @Column(name = "quality_report")
    private String qualityReport;

    @Column(name = "tea_images", columnDefinition = "TEXT")
    private String teaImages;

    @Column(name = "growth_video")
    private String growthVideo;

    @Column(name = "qr_code")
    private String qrCode;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
