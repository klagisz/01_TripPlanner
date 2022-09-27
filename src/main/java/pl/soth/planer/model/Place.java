package pl.soth.planer.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data

public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String adress;

    private TypeOfPlace typeOfPlace;

    private String note;

    private boolean isVisited;

    @ManyToOne
    private Trip trip;

}
