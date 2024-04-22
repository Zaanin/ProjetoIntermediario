package com.ProjetoIntermediario.ProjetoIntermediario.controler;

import com.ProjetoIntermediario.ProjetoIntermediario.model.Ajuda;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AjudaController {

    @GetMapping("/ajuda")
    public Ajuda getAjudaInfo() {
        return new Ajuda("Mateus Zanin Fernandes", "Encurtador de link");
    }
}
