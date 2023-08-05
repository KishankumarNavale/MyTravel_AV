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

    @Column(name="complaint_title")
    String complaintTitle;

    @Column(name="image")
    byte[] image;

    @Column(name="description")
    String description;

    @Column(name="mla_id")
    int mlaId;

    @Column(name="date")
    String date;

    @Column(name="complaint_status")
    String complaintStatus;

    @Column(name="constituency")
    String constituency;

    @Column(name="mla_name")
    String mlaName;

    @Column(name="comments")
    String comments;



}
