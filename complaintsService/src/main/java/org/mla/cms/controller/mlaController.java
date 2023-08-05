package org.mla.cms.controller;


import lombok.AllArgsConstructor;
import org.mla.cms.model.Complaints;
import org.mla.cms.model.Mla;
import org.mla.cms.model.Users;
import org.mla.cms.service.MlaService;
import org.mla.cms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/mla")
public class mlaController {

    private MlaService mlaService;

    // build create Users REST API
    @PostMapping
    public ResponseEntity<Mla> createUser(@RequestBody Mla mla){
        Mla savedMla = mlaService.createMla(mla);
        return new ResponseEntity<>(savedMla, HttpStatus.CREATED);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity<Mla> getUserById(@PathVariable("id") Integer userId){
        Mla mla = mlaService.getMlaById(userId);
        return new ResponseEntity<>(mla, HttpStatus.OK);
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<Mla>> getAllMlas(){
        List<Mla> mlas = mlaService.getAllMlas();
        return new ResponseEntity<>(mlas, HttpStatus.OK);
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping("{constituency}")
    public ResponseEntity<List<String>> getAllMlaByConstituency(@PathVariable("constituency") String constituency){
        List<String> mlas = mlaService.getAllMlasByConstituency(constituency);
        return new ResponseEntity<>(mlas, HttpStatus.OK);
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping("/constituencies")
    public ResponseEntity<List<String>> getAllConstituency(){
        List<String> mlas = mlaService.getAllConstituency();
        return new ResponseEntity<>(mlas, HttpStatus.OK);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("/mlaNConstitueny")
    public ResponseEntity<Optional<Mla>> getMLAByMlaNameAndConstituency(@RequestParam("mlaName") String mlaName,
                                                                          @RequestParam("constituency") String constituency){
        Optional<Mla> mla = mlaService.getMLAByMlaNameAndConstituency(mlaName,constituency);
        return new ResponseEntity<>(mla, HttpStatus.OK);
    }





    // Build Update Users REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/users/1
    public ResponseEntity<Mla> updateUser(@PathVariable("id") Integer mlaId,
                                           @RequestBody Mla mla){
        mla.setMlaId(mlaId);
        Mla updatedMla = mlaService.updateMla(mla);
        return new ResponseEntity<>(updatedMla, HttpStatus.OK);
    }


    // Build Delete Users REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMla(@PathVariable("id") Integer userId){
        mlaService.deleteMla(userId);
        return new ResponseEntity<>("Mla data successfully deleted!", HttpStatus.OK);
    }
}