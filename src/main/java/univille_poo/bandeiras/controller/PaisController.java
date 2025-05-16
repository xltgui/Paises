package univille_poo.bandeiras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import univille_poo.bandeiras.model.Pais;
import univille_poo.bandeiras.service.PaisService;

import java.util.List;

@Controller
@RequestMapping("/paises")
public class PaisController {

    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping()
    public ModelAndView listarPaises(
            @RequestParam(value = "continente", required = false) String continente,
            @RequestParam(value = "ordenacao", required = false) String ordenacao
    ) {
        List<Pais> listaPaises = paisService.listar(continente, ordenacao);
        ModelAndView mv = new ModelAndView("pais/index");
        mv.addObject("listaPaises", listaPaises);
        mv.addObject("continentes", obterContinentes());
        mv.addObject("continenteSelecionado", continente);
        mv.addObject("ordenacaoSelecionada", ordenacao);
        return mv;
    }

    private List<String> obterContinentes() {
        return paisService.listar().stream()
                .map(Pais::getContinente)
                .distinct()
                .sorted()
                .toList();
    }

}
