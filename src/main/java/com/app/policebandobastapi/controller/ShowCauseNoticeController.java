package com.app.policebandobastapi.controller;

import com.app.policebandobastapi.dto.ShowCauseNoticeDto;
import com.app.policebandobastapi.service.ShowCauseNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Errors (not found, bad request, duplicate) are thrown by the service and handled by GlobalExceptionHandler
@RestController
@RequestMapping("/api/v1/show-cause-notice")
public class ShowCauseNoticeController {
    @Autowired
    private ShowCauseNoticeService service;

    @GetMapping
    public List<ShowCauseNoticeDto> findAll() {
        return service.getAllShowCauseNotice();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowCauseNoticeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ShowCauseNoticeDto> save(@RequestBody ShowCauseNoticeDto notice) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addShowCauseNotice(notice));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowCauseNoticeDto> update(@PathVariable Long id, @RequestBody ShowCauseNoticeDto notice) {
        return ResponseEntity.ok(service.updateShowCauseNotice(id, notice));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteShowCauseNotice(id);
        return ResponseEntity.noContent().build();
    }
}
