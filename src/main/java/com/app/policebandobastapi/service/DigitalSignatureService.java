package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.DigitalSignatureDto;

public interface DigitalSignatureService {

    public DigitalSignatureDto addDigitalSignature(DigitalSignatureDto signature);

    public List<DigitalSignatureDto> getAllDigitalSignature();

    public DigitalSignatureDto findById(Long id);

    public DigitalSignatureDto updateDigitalSignature(Long id, DigitalSignatureDto signature);

    public void deleteDigitalSignature(Long id);

}
