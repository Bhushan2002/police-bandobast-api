package com.app.policebandobastapi.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.app.policebandobastapi.enums.Enums.NoticeDecision;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowCauseNoticeDto {
    private Long id;
    private Long noticeId;
    private Long staffId;
    private Long attendanceId;
    private String noticeReason;
    private String subject;
    private Long bandobastId;
    private Long dutyPointId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate incidentDate;
    private int replyDueDays;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate replyDueDate;
    private String body;
    private Long issuedById;
    private String issuedByName;
    private String issuedByDesignation;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime issuedAt;
    private RecordStatus status;
    private String replyText;
    private String replyAttachmentUrl;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime repliedAt;
    private NoticeDecision decision;
    private String decisionRemark;
    private Long decidedById;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime decidedAt;
    private String pdfUrl;
}
