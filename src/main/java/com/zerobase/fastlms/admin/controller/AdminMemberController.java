package com.zerobase.fastlms.admin.controller;


import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.model.MemberInput;
import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.user.dto.LoginHistoryDto;
import com.zerobase.fastlms.user.entity.LoginHistory;
import com.zerobase.fastlms.user.service.LoginHistoryService;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminMemberController extends BaseController {
    
    private final MemberService memberService;
    private final LoginHistoryService loginHistoryService;
    private final BannerService bannerService;
    
    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberParam parameter) {
        
        parameter.init();
        List<MemberDto> members = memberService.list(parameter);
        
        long totalCount = 0;
        if (members != null && members.size() > 0) {
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
        
        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);
        
        return "admin/member/list";
    }

    @GetMapping("/admin/banner/list.do")
    public String list(Model model, HttpServletResponse response){

        List<Banner> banners = bannerService.getBanner();

        model.addAttribute("banners",banners);
        long totalCount = 0;
        if(banners != null && banners.size() > 0) {
            totalCount = banners.size();
        }

        model.addAttribute("totalCount",totalCount);

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return "/admin/banner/list";
    }

    @GetMapping("/admin/banner/add.do")
    public String listAddBanner(Model model){

        List<Banner> banners = bannerService.getBanner();
        model.addAttribute("banners",banners);

        return "/admin/banner/add";
    }

    @PostMapping("/admin/banner/add.do")
    public String addBanner(@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("link") String link,
                            @RequestParam("bannerType") String bannerType,
                            @RequestParam("imgorder") int imgorder,
                            @RequestParam("isPublic") boolean isPublic){


        bannerService.insertBanner(BannerDto.builder()
                                    .name(name)
                                    .file(String.valueOf(file.getOriginalFilename()))
                                    .link(link)
                                    .bannerType(bannerType)
                                    .imgorder(imgorder)
                                    .isPublic(isPublic)
                                    .build());


        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/admin/banner/delete.do")
    public String deleteBanner(@RequestBody List<Long> ids){

        System.out.println("deleteBanner "+ids);
        bannerService.deleteBanner(ids);
        return "redirect:/admin/banner/list.do";
    }
    
    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam parameter) {
        
        parameter.init();
        
        MemberDto member = memberService.detail(parameter.getUserId());

        List<LoginHistory> login = loginHistoryService.selectLoginHistory(parameter.getUserId());

        model.addAttribute("member", member);
        model.addAttribute("login", login);

       
        return "admin/member/detail";
    }
    
    @PostMapping("/admin/member/status.do")
    public String status(Model model, MemberInput parameter) {
    
        
        boolean result = memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());
        
        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }
    
    
    @PostMapping("/admin/member/password.do")
    public String password(Model model, MemberInput parameter) {
        
        
        boolean result = memberService.updatePassword(parameter.getUserId(), parameter.getPassword());
        
        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }
    
    
    
    
}
