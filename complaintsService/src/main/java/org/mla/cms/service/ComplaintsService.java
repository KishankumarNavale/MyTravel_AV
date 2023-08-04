package org.mla.cms.service;

import org.mla.cms.model.Complaints;
import org.mla.cms.model.Users;
import org.mla.cms.repo.ComplaintsRepository;
import org.mla.cms.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ComplaintsService {

    @Autowired
    private ComplaintsRepository complaintsRepository;

    public Complaints createComplaint(Complaints complaint) {
        return complaintsRepository.save(complaint);
    }

    public List<Complaints> getComplaintByUserId(Integer usersId) {
        return complaintsRepository.findAll().stream().filter(x -> usersId == x.getUserId()).collect(Collectors.toList());
    }

    public List<Complaints> getComplaintByMlaId(Integer mlaId) {
        return complaintsRepository.findAll().stream().filter(x -> mlaId == x.getMlaId()).collect(Collectors.toList());
    }

    public Complaints getComplaintByComplaintId(Integer complaintId) {
        return complaintsRepository.findAll().stream().filter(x -> complaintId == x.getComplaintId()).findAny().get();
    }


    public List<Complaints> getAllComplaintsByStatusAndMlaId(String status , Integer mlaId) {

        return complaintsRepository.findAll().stream().filter(x -> status.equals(x.getComplaintStatus()) && mlaId == x.getMlaId()).collect(Collectors.toList());
    }

    public List<Complaints> getAllComplaintsByStatusAndUserId(String status , Integer userId) {

        return complaintsRepository.findAll().stream().filter(x -> status.equals(x.getComplaintStatus()) && userId == x.getUserId()).collect(Collectors.toList());
    }


    public Complaints updateComplaint(Complaints complaint) {
        Complaints existingComplaint = complaintsRepository.findById(complaint.getComplaintId()).get();
        existingComplaint.setUserId(complaint.getUserId());
        existingComplaint.setMlaId(complaint.getMlaId ());
        existingComplaint.setImage(complaint.getImage());
        Complaints updatedComplaints = complaintsRepository.save(existingComplaint);
        return updatedComplaints;
    }

    public void deleteComplaint(Integer complaintId) {
        complaintsRepository.deleteById(complaintId);
    }
}
