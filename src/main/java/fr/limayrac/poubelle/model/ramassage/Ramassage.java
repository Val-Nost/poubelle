package fr.limayrac.poubelle.model.ramassage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Ramassage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "ramassage", fetch = FetchType.EAGER)
    private List<RamassageCyclisteVelo> ramassageCyclisteVelos;
    private Boolean enCours;
    @OneToMany(mappedBy = "ramassage")
    private List<Passage> passages;

    public boolean termine() {
        return passages.stream().allMatch(passage -> passage.getRamasse().equals(true));
    }
}
