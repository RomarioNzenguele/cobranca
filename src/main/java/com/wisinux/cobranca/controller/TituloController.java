package com.wisinux.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wisinux.cobranca.models.StatusTitulo;
import com.wisinux.cobranca.models.Titulo;
import com.wisinux.cobranca.repository.TituloFilter;
import com.wisinux.cobranca.service.TituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    private static final String CADASTRO_VIEW = "CadastroTitulo";

    @Autowired
    private TituloService tituloService;

    @RequestMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject("todosStatus", StatusTitulo.values());
        mv.addObject(new Titulo());
        return mv;
    }

    @PostMapping
    public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            return CADASTRO_VIEW;
        }

        try {
            this.tituloService.salvar(titulo);
            attributes.addFlashAttribute("mensagem", "Titulo Salvo Com Sucesso!"); // preservando os atributos depois do
            return "redirect:/titulos/novo";
        } catch (IllegalArgumentException e) {
            errors.rejectValue("dataVencimento", null, e.getMessage());
            return CADASTRO_VIEW;
        }
    }

    @RequestMapping("{codigo}")
    public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(titulo);
        return mv;
    }

    @DeleteMapping("{codigo}")
    public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
        this.tituloService.excluir(codigo);
        attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
        return "redirect:/titulos";
    }

    @RequestMapping
    public ModelAndView listar(@ModelAttribute("filtro") TituloFilter filtro) {
        ModelAndView mv = new ModelAndView("PesquisaTitulos");
        mv.addObject("titulos", this.tituloService.filtrar(filtro));
        return mv;
    }

    // @RequestMapping
    // public ModelAndView listar(@RequestParam(defaultValue = "%") String
    // descricao) {
    // ModelAndView mv = new ModelAndView("PesquisaTitulos");
    // mv.addObject("titulos", this.tituloService.findByDescricao(descricao));
    // return mv;
    // }

    @ModelAttribute("todosStatus")
    public List<StatusTitulo> listarStatus() {
        return Arrays.asList(StatusTitulo.values());
    }

    @PutMapping("/{codigo}/receber")
    public @ResponseBody String receber(@PathVariable Long codigo) {
        return this.tituloService.receber(codigo);
    }

}
