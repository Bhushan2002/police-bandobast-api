package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.ShowCauseNoticeDto;

public interface ShowCauseNoticeService {

    public ShowCauseNoticeDto addShowCauseNotice(ShowCauseNoticeDto notice);

    public List<ShowCauseNoticeDto> getAllShowCauseNotice();

    public ShowCauseNoticeDto findById(Long id);

    public ShowCauseNoticeDto updateShowCauseNotice(Long id, ShowCauseNoticeDto notice);

    public void deleteShowCauseNotice(Long id);

}
