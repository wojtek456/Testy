package pl.straszewski;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by wojtek on 2017-06-04.
 */
@ToString
@Setter
@Getter
@Entity
@Table(name = "Uzytkownik")
public class Users {
    @Id
    @GeneratedValue
    private long id;

    @Column(name="imie")
    private String firstName;

    @Column(name="nazwisko")
    private String lastName;

    @Column(name="pensja")
    private float salary;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Address> address;

}
