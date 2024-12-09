package com.zerobase.fastlms.banner.service;


import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.repository.BannerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class BannerService {

    private final BannerRepository bannerRepository;


    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<Banner> getBanner(){
        return bannerRepository.findAll(Sort.by(Sort.Direction.ASC, "imgorder"));
    }

    @Transactional
    public void insertBanner(BannerDto bannerDto){

        Banner banner = Banner.builder()
                .id(bannerDto.getId())
                .name(bannerDto.getName())
                .bannerType(bannerDto.getBannerType())
                .file(bannerDto.getFile())
                .link(bannerDto.getLink())
                .imgorder(bannerDto.getImgorder())
                .isPublic(bannerDto.isPublic())
                .registDt(LocalDate.now())
                .build();

        bannerRepository.save(banner);
    }

    public List<Banner> getPublicBanner(){
        return bannerRepository.findByIsPublicTrue();
    }

    public void deleteBanner(List<Long> ids) {
        bannerRepository.deleteAllById(ids);
    }
}
