package org.mla.cms.controller;


import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.mla.cms.model.Complaints;
import org.mla.cms.model.Users;
import org.mla.cms.service.ComplaintsService;
import org.mla.cms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/complaints")
public class ComplaintsController {

    private ComplaintsService complaintsService;

    // build create Users REST API
    @PostMapping
    public ResponseEntity<Complaints> createComplaint(@RequestBody Complaints complaint){
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
                                           @RequestBody Complaints complaint){
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
    @DeleteMapping("{complaintId}")
    public ResponseEntity<String> deleteUser(@PathVariable("complaintId") Integer complaintId){
        complaintsService.deleteComplaint(complaintId);
        return new ResponseEntity<>("Complaint successfully deleted!", HttpStatus.OK);
    }

    private String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}