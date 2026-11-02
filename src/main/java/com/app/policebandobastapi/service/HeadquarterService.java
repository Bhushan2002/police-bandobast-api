package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.HeadquarterDto;

public interface HeadquarterService {

    public HeadquarterDto addHeadquarter(HeadquarterDto headquarter);

    public List<HeadquarterDto> getAllHeadquarter();

    public HeadquarterDto findById(int id);

    public HeadquarterDto updateHeadquarter(int id, HeadquarterDto headquarter);

    public void deleteHeadquarter(int id);

}
