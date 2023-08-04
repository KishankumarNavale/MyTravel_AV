package org.mla.cms.service;

import org.mla.cms.model.Mla;
import org.mla.cms.model.Users;
import org.mla.cms.repo.MlaRepository;
import org.mla.cms.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MlaService {

    @Autowired
    private MlaRepository mlaRepository;

    public Mla createMla(Mla mla) {
        return mlaRepository.save(mla);
    }

    public Mla getMlaById(Integer mlaId) {
        Optional<Mla> optionalUsers = mlaRepository.findById(mlaId);
        return optionalUsers.get();
    }

    public List<Mla> getAllMlas() {
        return mlaRepository.findAll();
    }

    public Mla updateMla(Mla mla) {
        Mla existingMlas = mlaRepository.findById(mla.getMlaId()).get();
        existingMlas.setMlaName(mla.getMlaName());
        existingMlas.setCity(mla.getCity());
        existingMlas.setContactNumber(mla.getContactNumber());
        existingMlas.setState(mla.getState());
        existingMlas.setParty(mla.getParty());
        existingMlas.setEmailId(mla.getEmailId());
        Mla updatedMla = mlaRepository.save(mla);
        return updatedMla;
    }

    public void deleteMla(Integer mlaId) {
        mlaRepository.deleteById(mlaId);
    }
}
