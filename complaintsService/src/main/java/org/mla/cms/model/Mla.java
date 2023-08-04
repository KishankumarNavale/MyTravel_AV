package org.mla.cms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mla")
public class Mla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mla_id")
    int mlaId;

    @Column(name="mla_name")
    String mlaName;

    @Column(name="contact_number")
    String contactNumber;

    @Column(name="city")
    String city;

    @Column(name="state")
    String state;

    @Column(name="email_id")
    String emailId;

    @Column(name="party")
    String party;


}
