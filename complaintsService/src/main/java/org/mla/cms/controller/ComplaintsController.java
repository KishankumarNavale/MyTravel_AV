package org.mla.cms.controller;


import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.mla.cms.model.Complaints;
import org.mla.cms.model.ComplaintsImages;
import org.mla.cms.model.Mla;
import org.mla.cms.model.Users;
import org.mla.cms.service.ComplaintsImagesService;
import org.mla.cms.service.ComplaintsService;
import org.mla.cms.service.MlaService;
import org.mla.cms.service.UserService;
import org.mla.cms.utils.ImageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/complaints")
public class ComplaintsController {

    private ComplaintsService complaintsService;

    private MlaService mlaService;

    private ComplaintsImagesService complaintsImagesService;

    // build create Users REST API
    @PostMapping("{complaintId}")
    public ResponseEntity<?> createComplaintImage(@PathVariable("complaintId") Integer complaintId,@RequestParam("image") MultipartFile image) throws IOException {
        ComplaintsImages complaintImage = ComplaintsImages.builder().complaintId(complaintId).image(ImageUtil.compressImage(image.getBytes())).build();
        complaintsImagesService.createComplaintImage(complaintImage);
        return new ResponseEntity<>("Complaint submitted successfully", HttpStatus.CREATED);

    }

    // build create Users REST API
    @PutMapping("updateImage/{complaintId}")
    public ResponseEntity<?> updateComplaintImage(@PathVariable("complaintId") Integer complaintId,@RequestParam("image") MultipartFile image) throws IOException {
        ComplaintsImages complaintImage = ComplaintsImages.builder().complaintId(complaintId).image(ImageUtil.compressImage(image.getBytes())).build();
        complaintsImagesService.createComplaintImage(complaintImage);
        return new ResponseEntity<>("Complaint submitted successfully", HttpStatus.CREATED);

    }

    // build create Users REST API
    @GetMapping("{complaintId}")
    public ResponseEntity<?> getComplaintImageByComplaintId(@PathVariable("complaintId") Integer complaintId) throws IOException {
        byte[] complaintsImages = complaintsImagesService.getComplaintImageByComplaintId(complaintId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(complaintsImages);

    }


    // build create Users REST API
    @PostMapping
    public ResponseEntity<?> createComplaint(@RequestBody Complaints complaint) throws IOException {
        Optional<Mla> mla = mlaService.getMLAByMlaNameAndConstituency(complaint.getMlaName(),complaint.getConstituency());
        if(!mla.isPresent())
            return new ResponseEntity<>("MLA details are missing", HttpStatus.BAD_REQUEST);
            complaint.setMlaId(mla.get().getMlaId());
            complaint.setDate(getDate());
            complaint.setComplaintStatus("New");
            Complaints savedComplaint = complaintsService.createComplaint(complaint);
            return new ResponseEntity<>(savedComplaint, HttpStatus.CREATED);

    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("/user")
    public ResponseEntity<List<Complaints>> getComplaintsByUserId(@RequestParam("userId") Integer userId){
        List<Complaints> complaints = complaintsService.getComplaintByUserId(userId);
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("/mlaNstatus")
    public ResponseEntity<List<Complaints>> getComplaintsByMlaIdAndStatus(@RequestParam("mlaId") Integer mlaId, @RequestParam("status") String status){
        List<Complaints> complaints = complaintsService.getAllComplaintsByStatusAndMlaId(status,mlaId);
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("/userNStatus")
    public ResponseEntity<List<Complaints>> getComplaintsByUserIdAndStatus(@RequestParam("userId") Integer userId,@RequestParam("status") String status){
        List<Complaints> complaints = complaintsService.getAllComplaintsByStatusAndUserId(status,userId);
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/14
    @GetMapping("/mla")
    public ResponseEntity<List<Complaints>> getComplaintsByMlaId(@RequestParam("mlaId") Integer mlaId){
        List<Complaints> complaints = complaintsService.getComplaintByMlaId(mlaId);
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    // Build Update Users REST API
    // http://localhost:8080/api/users/1
    @PutMapping("{complaintId}")
    public ResponseEntity<Complaints> updateComplaint(@PathVariable("complaintId") Integer complaintId,
                                                      @RequestParam Complaints complaint){
        complaint.setComplaintId(complaintId);
        complaint.setComplaintStatus("InProgress");
        complaint.setDate(getDate());
        Complaints updatedComplaint = complaintsService.updateComplaint(complaint);
        return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
    }

    @PutMapping("/closeComplaint/{complaintId}")
    public ResponseEntity<Complaints> closeComplaint(@PathVariable("complaintId") Integer complaintId){
        Complaints complaint = complaintsService.getComplaintByComplaintId(complaintId);
        complaint.setComplaintStatus("Closed");
        complaint.setDate(getDate());
        Complaints updatedComplaint = complaintsService.updateComplaint(complaint);
        return new ResponseEntity<>(updatedComplaint, HttpStatus.OK);
    }


    // Build Delete Users REST API
    @GetMapping("complaints/{complaintId}")
    public ResponseEntity<Complaints> getComplaintByComplaintId(@PathVariable("complaintId") Integer complaintId){
        Complaints complaint = complaintsService.getComplaintByComplaintId(complaintId);
        return new ResponseEntity<>(complaint, HttpStatus.OK);
    }

    // Build Delete Users REST API
    @DeleteMapping("{complaintId}")
    public ResponseEntity<String> deleteComplaint(@PathVariable("complaintId") Integer complaintId){
        complaintsService.deleteComplaint(complaintId);
        return new ResponseEntity<>("Complaint successfully deleted!", HttpStatus.OK);
    }

    private String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}