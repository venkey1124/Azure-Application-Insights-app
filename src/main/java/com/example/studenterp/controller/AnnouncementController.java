package com.example.studenterp.controller;

import com.example.studenterp.dto.AnnouncementDto;
import com.example.studenterp.service.AnnouncementService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    public List<AnnouncementDto> getAnnouncements() {
        return announcementService.getAnnouncements();
    }
}
