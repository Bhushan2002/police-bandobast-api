package com.app.policebandobastapi.service;


import com.app.policebandobastapi.enums.Enums;
import com.app.policebandobastapi.model.Shift;
import com.app.policebandobastapi.repository.ShiftRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShiftService {
    @Autowired
    private ShiftRepo repo;

    public List<Shift> findAll() {
        return repo.findAll();
    }

    public Optional<Shift> findById(int id) {
        return repo.findById(id);
    }

    public Shift save(Shift shift) {
        if (shift.getStatus() == null) {
            shift.setStatus(Enums.Status.ACTIVE);
        }
        return repo.save(shift);
    }

    public Optional<Shift> update(int id, Shift shift) {
        return repo.findById(id).map(existing -> {
            existing.setName(shift.getName());
            existing.setStartTime(shift.getStartTime());
            existing.setEndTime(shift.getEndTime());
            existing.setShiftType(shift.getShiftType());
            existing.setDayOffAfter24hr(shift.isDayOffAfter24hr());
            if (shift.getStatus() != null) {
                existing.setStatus(shift.getStatus());
            }
            return repo.save(existing);
        });
    }

    // Soft delete: keeps the row so records that reference it stay valid
    public boolean delete(int id) {
        return repo.findById(id).map(existing -> {
            existing.setStatus(Enums.Status.INACTIVE);
            repo.save(existing);
            return true;
        }).orElse(false);
    }
}
