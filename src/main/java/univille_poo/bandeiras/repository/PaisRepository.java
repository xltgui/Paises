package univille_poo.bandeiras.repository;

import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import univille_poo.bandeiras.model.Pais;
@RequestMapping
public interface PaisRepository extends JpaRepository<Pais, Long> {
    Pais findByContinenteIgnoreCaseContaining(@PathParam("continente") String continente);
}
