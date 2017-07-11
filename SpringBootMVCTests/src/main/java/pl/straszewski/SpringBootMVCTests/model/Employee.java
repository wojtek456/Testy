package pl.straszewski.SpringBootMVCTests.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.awt.*;
import java.util.Date;

/**
 * Created by wojtek on 2017-06-15.
 */
@Setter
@Getter
@ToString
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String password;
    @Email
    private String email;
    @Column(name = "age")
    private int age;

    private String imagePath;
    private String imageName;
    private boolean uploadedImage = false;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date lastModified;


}
