package pl.straszewski;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
/**
 * Created by wojtek on 2017-07-10.
 */
@Entity
public class Address {
    @Id
    @GeneratedValue
    private long id;
    private String street;

}
