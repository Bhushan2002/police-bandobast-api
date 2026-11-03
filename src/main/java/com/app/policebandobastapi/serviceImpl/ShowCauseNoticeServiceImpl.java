package com.app.policebandobastapi.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.ShowCauseNoticeDto;
import com.app.policebandobastapi.enums.Enums.NoticeReason;
import com.app.policebandobastapi.enums.Enums.RecordStatus;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.ShowCauseNotice;
import com.app.policebandobastapi.repository.BandobastRepository;
import com.app.policebandobastapi.repository.DutyPointRepository;
import com.app.policebandobastapi.repository.ShowCauseNoticeRepo;
import com.app.policebandobastapi.repository.StaffRepo;
import com.app.policebandobastapi.service.ShowCauseNoticeService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class ShowCauseNoticeServiceImpl implements ShowCauseNoticeService {

    // खुलासा मुदत options from the notice form
    private static final List<Integer> ALLOWED_REPLY_DUE_DAYS = List.of(1, 3, 7, 15);

    @Autowired
    private ShowCauseNoticeRepo repo;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private BandobastRepository bandobastRepo;

    @Autowired
    private DutyPointRepository dutyPointRepo;

    @Override
    public ShowCauseNoticeDto addShowCauseNotice(ShowCauseNoticeDto noticeDto) {
        validate(noticeDto);

        ShowCauseNotice notice = new ShowCauseNotice();
        copyToEntity(noticeDto, notice);
        if (notice.getStatus() == null) {
            notice.setStatus(RecordStatus.ACTIVE);
        }
        if (notice.getIssuedAt() == null) {
            notice.setIssuedAt(LocalTime.now());
        }
        if (notice.getRepaceDueDate() == null) {
            notice.setRepaceDueDate(LocalDate.now().plusDays(notice.getReplaceDueDays()));
        }
        return toDto(repo.save(notice));
    }

    @Override
    public List<ShowCauseNoticeDto> getAllShowCauseNotice() {
        List<ShowCauseNotice> notices = repo.findAll();

        return notices.stream()
                .map(notice -> toDto(notice))
                .toList();
    }

    @Override
    public ShowCauseNoticeDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public ShowCauseNoticeDto updateShowCauseNotice(Long id, ShowCauseNoticeDto noticeDto) {
        ShowCauseNotice existing = getExisting(id);
        validate(noticeDto);

        RecordStatus currentStatus = existing.getStatus();
        LocalTime currentIssuedAt = existing.getIssuedAt();
        LocalDate currentReplyDueDate = existing.getRepaceDueDate();
        copyToEntity(noticeDto, existing);
        if (noticeDto.getStatus() == null) {
            existing.setStatus(currentStatus);
        }
        if (noticeDto.getIssuedAt() == null) {
            existing.setIssuedAt(currentIssuedAt);
        }
        if (noticeDto.getReplyDueDate() == null) {
            existing.setRepaceDueDate(currentReplyDueDate);
        }
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteShowCauseNotice(Long id) {
        ShowCauseNotice existing = getExisting(id);
        repo.delete(existing);
    }

    private ShowCauseNotice getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show cause notice", id));
    }

    private void validate(ShowCauseNoticeDto dto) {
        if (dto == null) {
            throw new BadRequestException("Show cause notice details are required");
        }
        ValidationUtils.requireExists(staffRepo, dto.getStaffId(), "Staff");
        ValidationUtils.requireEnumValue(dto.getNoticeReason(), NoticeReason.class, "Notice reason");
        ValidationUtils.requireText(dto.getSubject(), "Notice subject", 300);
        ValidationUtils.optionalExists(bandobastRepo, dto.getBandobastId(), "Bandobast");
        ValidationUtils.optionalExists(dutyPointRepo, dto.getDutyPointId(), "Duty point");
        if (dto.getIncidentDate() == null) {
            throw new BadRequestException("Incident date is required");
        }
        if (!ALLOWED_REPLY_DUE_DAYS.contains(dto.getReplyDueDays())) {
            throw new BadRequestException("Reply due days must be one of " + ALLOWED_REPLY_DUE_DAYS);
        }
        if (dto.getBody() == null || dto.getBody().isBlank()) {
            throw new BadRequestException("Notice body is required");
        }
    }

    // The entity still uses the misspelled replaceDueDays / repaceDueDate / issueById fields
    private void copyToEntity(ShowCauseNoticeDto dto, ShowCauseNotice notice) {
        notice.setNoticeId(dto.getNoticeId());
        notice.setStaffId(dto.getStaffId());
        notice.setAttendanceId(dto.getAttendanceId());
        notice.setNoticeReason(dto.getNoticeReason());
        notice.setSubject(dto.getSubject().trim());
        notice.setBandobastId(dto.getBandobastId());
        notice.setDutyPointId(dto.getDutyPointId());
        notice.setIncidentDate(dto.getIncidentDate());
        notice.setReplaceDueDays(dto.getReplyDueDays());
        notice.setRepaceDueDate(dto.getReplyDueDate());
        notice.setBody(dto.getBody());
        notice.setIssueById(dto.getIssuedById());
        notice.setIssuedByName(dto.getIssuedByName());
        notice.setIssuedByDesignation(dto.getIssuedByDesignation());
        notice.setIssuedAt(dto.getIssuedAt());
        notice.setStatus(dto.getStatus());
        notice.setReplyText(dto.getReplyText());
        notice.setReplyAttachmentUrl(dto.getReplyAttachmentUrl());
        notice.setRepliedAt(dto.getRepliedAt());
        notice.setDecision(dto.getDecision());
        notice.setDecisionRemark(dto.getDecisionRemark());
        notice.setDecidedById(dto.getDecidedById());
        notice.setDecidedAt(dto.getDecidedAt());
        notice.setPdfUrl(dto.getPdfUrl());
    }

    private ShowCauseNoticeDto toDto(ShowCauseNotice notice) {
        return new ShowCauseNoticeDto(notice.getId(), notice.getNoticeId(), notice.getStaffId(),
                notice.getAttendanceId(), notice.getNoticeReason(), notice.getSubject(), notice.getBandobastId(),
                notice.getDutyPointId(), notice.getIncidentDate(), notice.getReplaceDueDays(),
                notice.getRepaceDueDate(), notice.getBody(), notice.getIssueById(), notice.getIssuedByName(),
                notice.getIssuedByDesignation(), notice.getIssuedAt(), notice.getStatus(), notice.getReplyText(),
                notice.getReplyAttachmentUrl(), notice.getRepliedAt(), notice.getDecision(),
                notice.getDecisionRemark(), notice.getDecidedById(), notice.getDecidedAt(), notice.getPdfUrl());
    }
}
