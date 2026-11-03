package com.app.policebandobastapi.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.AppSettingsDto;
import com.app.policebandobastapi.enums.Enums.GpsTagMode;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.AppSettings;
import com.app.policebandobastapi.repository.AppSettingsRepo;
import com.app.policebandobastapi.service.AppSettingsService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class AppSettingsServiceImpl implements AppSettingsService {

    @Autowired
    private AppSettingsRepo repo;

    @Override
    public AppSettingsDto getSettings() {
        return toDto(repo.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new ResourceNotFoundException("App settings have not been saved yet")));
    }

    // Creates the settings row on the first save, then keeps updating that same row
    @Override
    public AppSettingsDto updateSettings(AppSettingsDto settingsDto) {
        validate(settingsDto);

        AppSettings settings = repo.findFirstByOrderByIdAsc().orElse(new AppSettings());
        copyToEntity(settingsDto, settings);
        return toDto(repo.save(settings));
    }

    private void validate(AppSettingsDto dto) {
        if (dto == null) {
            throw new BadRequestException("App settings details are required");
        }
        ValidationUtils.requireEnumValue(dto.getGpsTagMode(), GpsTagMode.class, "GPS tag mode");
        ValidationUtils.requireText(dto.getTopLine(), "Top line", 200);
        ValidationUtils.requireText(dto.getShortName(), "Short name", 100);
        ValidationUtils.requireText(dto.getOfficeName(), "Office name", 250);
        ValidationUtils.optionalText(dto.getAddress(), "Address", 500);
        ValidationUtils.optionalText(dto.getPhone(), "Phone", 30);
        ValidationUtils.requireText(dto.getOrderPrefix(), "Order prefix", 50);
        ValidationUtils.requireText(dto.getNoticePrefix(), "Notice prefix", 50);
        validateWatermarkOpacity(dto.getWatermarkOpacity());
    }

    // Stored as a String in the model, but the spec defines it as a 0–100 percentage
    private void validateWatermarkOpacity(String opacity) {
        if (opacity == null || opacity.isBlank()) {
            throw new BadRequestException("Watermark opacity is required");
        }
        int value;
        try {
            value = Integer.parseInt(opacity.trim());
        } catch (NumberFormatException e) {
            throw new BadRequestException("Watermark opacity must be a number between 0 and 100");
        }
        if (value < 0 || value > 100) {
            throw new BadRequestException("Watermark opacity must be a number between 0 and 100");
        }
    }

    // The entity still uses shortLine / noricePrefix / logourl; the DTO exposes shortName / noticePrefix / logoUrl
    private void copyToEntity(AppSettingsDto dto, AppSettings settings) {
        settings.setLivenessCheck(dto.isLivenessCheck());
        settings.setGpsTagMode(dto.getGpsTagMode());
        settings.setTopLine(dto.getTopLine().trim());
        settings.setShortLine(dto.getShortName().trim());
        settings.setOfficeName(dto.getOfficeName().trim());
        settings.setAddress(dto.getAddress());
        settings.setPhone(dto.getPhone());
        settings.setOrderPrefix(dto.getOrderPrefix().trim());
        settings.setNoricePrefix(dto.getNoticePrefix().trim());
        settings.setWatermarkEnabled(dto.isWatermarkEnabled());
        settings.setWatermarkOpacity(dto.getWatermarkOpacity());
        settings.setLogourl(dto.getLogoUrl());
    }

    private AppSettingsDto toDto(AppSettings settings) {
        return new AppSettingsDto(settings.getId(), settings.isLivenessCheck(), settings.getGpsTagMode(),
                settings.getTopLine(), settings.getShortLine(), settings.getOfficeName(), settings.getAddress(),
                settings.getPhone(), settings.getOrderPrefix(), settings.getNoricePrefix(),
                settings.isWatermarkEnabled(), settings.getWatermarkOpacity(), settings.getLogourl());
    }
}
