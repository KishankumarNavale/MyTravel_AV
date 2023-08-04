package org.mla.cms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    int userId;

    @Column(name="username")
    String userName;

    @Column(name="contact_number")
    String contactNumber;

    @Column(name="city")
    String city;

    @Column(name="state")
    String state;

    @Column(name="email_id")
    String emailId;


}
