package com.app.policebandobastapi.service;

import java.util.List;

import com.app.policebandobastapi.dto.InstructionTemplateDto;

public interface InstructionTemplateService {

    public InstructionTemplateDto addInstructionTemplate(InstructionTemplateDto instructionTemplate);

    public List<InstructionTemplateDto> getAllInstructionTemplate();

    public InstructionTemplateDto findById(int id);

    public InstructionTemplateDto updateInstructionTemplate(int id, InstructionTemplateDto instructionTemplate);

    public void deleteInstructionTemplate(int id);

}
