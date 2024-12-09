package com.zerobase.fastlms.banner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BannerDto {

    private Long id;
    private String name;
    private String file;
    private String link;
    private String bannerType;
    private int imgorder;
    private boolean isPublic;
    private LocalDate registDt;
}
