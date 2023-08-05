package org.mla.cms.service;

import org.mla.cms.model.Complaints;
import org.mla.cms.model.ComplaintsImages;
import org.mla.cms.repo.ComplaintsImageRepository;
import org.mla.cms.repo.ComplaintsRepository;
import org.mla.cms.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ComplaintsImagesService {

    @Autowired
    private ComplaintsImageRepository complaintsImageRepository;

    public void createComplaintImage(ComplaintsImages complaintsImages) {
        complaintsImageRepository.save(complaintsImages);
    }

    public byte[] getComplaintImageByComplaintId(Integer complaintId) {
        ComplaintsImages image =  complaintsImageRepository.findAll().stream().filter(x -> complaintId == x.getComplaintId()).findAny().get();
        return  ImageUtil.decompressImage(image.getImage());
    }


    public void deleteComplaintImageByComplaintId(Integer complaintId) {
       ComplaintsImages image =  complaintsImageRepository.findAll().stream().filter(x -> x.getComplaintImageId() == complaintId).findAny().get();
        complaintsImageRepository.deleteById(image.getComplaintImageId());
    }
}
