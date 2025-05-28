package univille_poo.bandeiras.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univille_poo.bandeiras.model.Pais;
import univille_poo.bandeiras.repository.PaisRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaisService {

    private final PaisRepository paisRepository;

    public Pais salvar(Pais pais) {
        return paisRepository.save(pais);
    }

    public Optional<Pais> bucarPorid(Long id) {
        return paisRepository.findById(id);
    }

    public void excluir(Long id) {
        paisRepository.deleteById(id);
    }

    private static final List<Pais> listaDePaises = new ArrayList<>();

    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
        listaDePaises.add(new Pais("Brasil", "Brasília", "América do Sul"));
        listaDePaises.add(new Pais("Argentina", "Buenos Aires", "América do Sul"));
        listaDePaises.add(new Pais("Japão", "Tóquio", "Ásia"));
        listaDePaises.add(new Pais("Alemanha", "Berlim", "Europa"));
        listaDePaises.add(new Pais("Canadá", "Ottawa", "América do Norte"));
        listaDePaises.add(new Pais("Austrália", "Camberra", "Oceania"));
        listaDePaises.add(new Pais("Egito", "Cairo", "África"));
    }

    public List<Pais> listar() {
        return paisRepository.findAll().stream()
                .peek(pais -> pais.setUrlBandeira("https://flagcdn.com/w320/" + getCodigoBandeira(pais.getNome()) + ".png"))
                .collect(Collectors.toList());
    }

    public List<Pais> listar(String continente, String ordenacao) {
        List<Pais> paisesFiltrados = paisRepository.findAll().stream()
                .filter(pais -> continente == null || continente.isEmpty() || pais.getContinente().equalsIgnoreCase(continente))
                .collect(Collectors.toList());


        paisesFiltrados.forEach(pais -> pais.setUrlBandeira(getCodigoBandeira(pais.getNome())));

        if (ordenacao != null && !ordenacao.isEmpty()) {
            switch (ordenacao.toLowerCase()) {
                case "nome":
                    paisesFiltrados.sort(Comparator.comparing(Pais::getNome));
                    break;
                case "capital":
                    paisesFiltrados.sort(Comparator.comparing(Pais::getCapital));
                    break;
                case "continente":
                    paisesFiltrados.sort(Comparator.comparing(Pais::getContinente));
                    break;
            }
        }

        return paisesFiltrados;
    }

    private String getCodigoBandeira(String nome) {
        return switch (nome.toLowerCase()) {
            case "brasil" -> "br";
            case "argentina" -> "ar";
            case "japão" -> "jp";
            case "alemanha" -> "de";
            case "canadá" -> "ca";
            case "austrália" -> "au";
            case "egito" -> "eg";
            default -> "paisx";
        };
    }


}
