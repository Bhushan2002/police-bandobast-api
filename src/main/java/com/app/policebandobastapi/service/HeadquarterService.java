package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.HeadquarterDto;

public interface HeadquarterService {

    public HeadquarterDto addHeadquarter(HeadquarterDto headquarter);

    public List<HeadquarterDto> getAllHeadquarter();

    public HeadquarterDto findById(Long id);

    public HeadquarterDto updateHeadquarter(Long id, HeadquarterDto headquarter);

    public void deleteHeadquarter(Long id);

}
