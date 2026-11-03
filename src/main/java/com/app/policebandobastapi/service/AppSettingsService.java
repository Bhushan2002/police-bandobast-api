package com.app.policebandobastapi.service;

import com.app.policebandobastapi.dto.AppSettingsDto;

public interface AppSettingsService {

    public AppSettingsDto getSettings();

    public AppSettingsDto updateSettings(AppSettingsDto settings);

}
