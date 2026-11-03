package com.app.policebandobastapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.policebandobastapi.dto.DigitalSignatureDto;
import com.app.policebandobastapi.enums.Enums.InkColor;
import com.app.policebandobastapi.exception.BadRequestException;
import com.app.policebandobastapi.exception.ResourceNotFoundException;
import com.app.policebandobastapi.model.DigitalSignature;
import com.app.policebandobastapi.repository.DigitalSignatureRepo;
import com.app.policebandobastapi.service.DigitalSignatureService;
import com.app.policebandobastapi.util.ValidationUtils;

@Service
public class DigitalSignatureServiceImpl implements DigitalSignatureService {

    @Autowired
    private DigitalSignatureRepo repo;

    @Override
    public DigitalSignatureDto addDigitalSignature(DigitalSignatureDto signatureDto) {
        validate(signatureDto);

        DigitalSignature signature = new DigitalSignature();
        copyToEntity(signatureDto, signature);
        return toDto(repo.save(signature));
    }

    @Override
    public List<DigitalSignatureDto> getAllDigitalSignature() {
        List<DigitalSignature> signatures = repo.findAll();

        return signatures.stream()
                .map(signature -> toDto(signature))
                .toList();
    }

    @Override
    public DigitalSignatureDto findById(Long id) {
        return toDto(getExisting(id));
    }

    @Override
    public DigitalSignatureDto updateDigitalSignature(Long id, DigitalSignatureDto signatureDto) {
        DigitalSignature existing = getExisting(id);
        validate(signatureDto);

        copyToEntity(signatureDto, existing);
        return toDto(repo.save(existing));
    }

    @Override
    public void deleteDigitalSignature(Long id) {
        DigitalSignature existing = getExisting(id);
        repo.delete(existing);
    }

    private DigitalSignature getExisting(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Digital signature", id));
    }

    private void validate(DigitalSignatureDto dto) {
        if (dto == null || dto.getImageUrl() == null || dto.getImageUrl().isBlank()) {
            throw new BadRequestException("Signature image is required");
        }
        ValidationUtils.requireEnumValue(dto.getInkColor(), InkColor.class, "Ink color");
        ValidationUtils.requireText(dto.getNameBelow(), "Name below signature", 150);
        ValidationUtils.requireText(dto.getDesignationBelow(), "Designation below signature", 150);
    }

    // The entity still uses the lowercase "designationbelow" field; the DTO exposes "designationBelow"
    private void copyToEntity(DigitalSignatureDto dto, DigitalSignature signature) {
        signature.setImageUrl(dto.getImageUrl().trim());
        signature.setInkColor(dto.getInkColor());
        signature.setNameBelow(dto.getNameBelow().trim());
        signature.setDesignationbelow(dto.getDesignationBelow().trim());
    }

    private DigitalSignatureDto toDto(DigitalSignature signature) {
        return new DigitalSignatureDto(signature.getId(), signature.getImageUrl(), signature.getInkColor(),
                signature.getNameBelow(), signature.getDesignationbelow());
    }
}
