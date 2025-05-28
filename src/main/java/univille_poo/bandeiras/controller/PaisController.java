package univille_poo.bandeiras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @GetMapping("/novo")
    public ModelAndView novoPais() {
        ModelAndView mv = new ModelAndView("pais/form");
        mv.addObject("pais", new Pais());
        mv.addObject("acao", "novo");
        return mv;
    }

    @PostMapping("/salvar")
    public String salvarPais(@ModelAttribute Pais pais, RedirectAttributes attributes) {
        paisService.salvar(pais);
        attributes.addFlashAttribute("mensagem", "País salvo com sucesso!");
        return "redirect:/paises";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarPais(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("pais/form");
        Pais pais = paisService.bucarPorid(id)
                .orElseThrow(() -> new IllegalArgumentException("País inválido:" + id));
        mv.addObject("pais", pais);
        mv.addObject("acao", "editar");
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public String excluirPais(@PathVariable Long id, RedirectAttributes attributes) {
        paisService.excluir(id);
        attributes.addFlashAttribute("mensagem", "País excluído com sucesso!");
        return "redirect:/paises";
    }

    private List<String> obterContinentes() {
        return paisService.listar().stream()
                .map(Pais::getContinente)
                .distinct()
                .sorted()
                .toList();
    }

}
