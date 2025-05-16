package univille_poo.bandeiras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Pais {

    public Pais(String pais, String capital, String continente) {
        this.nome = pais;
        this.capital = capital;
        this.continente = continente;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String capital;
    private String urlBandeira;
    private String continente;

}
