package org.mla.cms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "complaints")
public class Complaints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="complaint_id")
    int complaintId;

    @Column(name="user_id")
    int userId;

    @Column(name="image")
    String image;

    @Column(name="description")
    String description;

    @Column(name="mla_id")
    int mlaId;

    @Column(name="date")
    String date;

    @Column(name="complaint_status")
    String complaintStatus;


}
