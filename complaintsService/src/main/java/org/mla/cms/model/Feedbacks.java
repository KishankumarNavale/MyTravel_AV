package org.mla.cms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedbacks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="feedback_id")
    int feedbackId;

    @Column(name="mla_name")
    String mlaName;

    @Column(name="feedback")
    String feedback;

    @Column(name="date")
    String date;

    @Column(name="constituency")
    String constituency;

    @Column(name="user_id")
    String userId;


}
