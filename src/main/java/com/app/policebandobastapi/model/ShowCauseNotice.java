package com.app.policebandobastapi.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.app.policebandobastapi.enums.Enums.NoticeDecision;
import com.app.policebandobastapi.enums.Enums.RecordStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowCauseNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long noticeId;
    private Long staffId;
    private Long attendanceId;
    private String noticeReason;
    private String subject;
    private Long bandobastId;
    private Long dutyPointId;
    private LocalDate incidentDate;
    private int replaceDueDays;
    private LocalDate repaceDueDate;
    private String body;
    private Long issueById;
    private String issuedByName;
    private String issuedByDesignation;
    private LocalTime issuedAt;
    private RecordStatus status;
    private String replyText;
    private String replyAttachmentUrl;
    private LocalTime repliedAt;
    private NoticeDecision decision;
    private String decisionRemark;
    private Long decidedById;
    private LocalTime decidedAt;
    private String pdfUrl;

}
