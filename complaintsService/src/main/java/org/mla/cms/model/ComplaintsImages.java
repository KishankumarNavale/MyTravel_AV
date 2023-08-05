package org.mla.cms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "complaint_images")
@Builder
public class ComplaintsImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="complaint_imageid")
    int complaintImageId;

    @Column(name="complaint_id")
    int complaintId;

    @Column(name="image")
    byte[] image;


}
