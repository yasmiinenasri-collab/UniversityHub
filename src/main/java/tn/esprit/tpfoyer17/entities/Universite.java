package tn.esprit.tpfoyer17.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Universite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long idUniversite;
    @NotNull(message = "Le nom de l'université ne peut pas être nul.")
    @Size(min = 3, max = 100, message = "Le nom de l'université doit avoir entre 3 et 100 caractères.")
    String nomUniversite;

    String adresse;

    int capaciteMaximale;


    @ToString.Exclude
    @OneToOne
    @JsonIgnore
    Foyer foyer;
}
