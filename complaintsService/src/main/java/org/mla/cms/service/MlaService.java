package org.mla.cms.service;

import org.mla.cms.model.Mla;
import org.mla.cms.model.Users;
import org.mla.cms.repo.MlaRepository;
import org.mla.cms.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public List<String> getAllMlasByConstituency(String constituency) {
       List<Mla> mlas =  mlaRepository.findAll().stream().filter(x -> null != x.getConstituency()).collect(Collectors.toList());
       return mlas.stream().filter(x -> constituency.equals(x.getConstituency())).map(y -> y.getMlaName()).collect(Collectors.toList());
    }

    public List<String> getAllConstituency() {
       return mlaRepository.findAll().stream().map(x -> x.getConstituency()).collect(Collectors.toList());
    }

    public Optional<Mla> getMLAByMlaNameAndConstituency(String mlaName, String constituency) {
       return mlaRepository.findAll().stream().filter(x -> x.getMlaName().equals(mlaName) && x.getConstituency().equals(constituency)).findAny();
    }
}
