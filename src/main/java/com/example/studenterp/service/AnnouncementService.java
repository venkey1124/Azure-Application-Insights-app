package com.example.studenterp.service;

import com.example.studenterp.dto.AnnouncementDto;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AnnouncementService {

    private final RestClient restClient;
    private final String announcementsUrl;

    public AnnouncementService(RestClient restClient, @Value("${app.announcements.url}") String announcementsUrl) {
        this.restClient = restClient;
        this.announcementsUrl = announcementsUrl;
    }

    public List<AnnouncementDto> getAnnouncements() {
        AnnouncementDto[] announcements = restClient.get()
                .uri(announcementsUrl)
                .retrieve()
                .body(AnnouncementDto[].class);

        if (announcements == null) {
            return List.of();
        }

        return Arrays.asList(announcements);
    }
}
