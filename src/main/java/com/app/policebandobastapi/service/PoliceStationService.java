package com.app.policebandobastapi.service;


import com.app.policebandobastapi.model.PoliceStation;
import com.app.policebandobastapi.repository.PoliceStationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PoliceStationService {

    @Autowired
    private PoliceStationRepo repo;


    // post
    public void save(PoliceStation policeStation) {
        repo.save(policeStation);
    }


    //get all
    public List<PoliceStation> findAll() {
        return repo.findAll();
    }

    // getById
    public PoliceStation findById(int id) {
        return repo.findById(id).get();
    }

    // update
    public Optional<PoliceStation> update(PoliceStation policeStation, int id) {
        return repo.findById(id).map(existing -> {
            existing.setName(policeStation.getName());
            existing.setHeadquarterId(policeStation.getHeadquarterId());
            existing.setDistrict(policeStation.getDistrict());
            existing.setCity(policeStation.getCity());
            existing.setVillage(policeStation.getVillage());
            existing.setPincode(policeStation.getPincode());
            existing.setLandline(policeStation.getLandline());
            existing.setMobile(policeStation.getMobile());
            existing.setTaluka(policeStation.getTaluka());
            if (policeStation.getStatus() != null) {
                existing.setStatus(policeStation.getStatus());
            }
            return repo.save(existing);
        });
    }

    // delete
    public void delete(int id) {
        repo.deleteById(id);
    }

}
