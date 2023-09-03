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
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wisinux.cobranca.models.StatusTitulo;
import com.wisinux.cobranca.models.Titulo;
import com.wisinux.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    private static final String CADASTRO_VIEW = "CadastroTitulo";
    @Autowired
    private Titulos titulos;

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

        this.titulos.save(titulo);

        attributes.addFlashAttribute("mensagem", "Titulo Salvo Com Sucesso!"); // preservando os atributos depois do

        return "redirect:/titulos/novo";
    }

    @RequestMapping("{codigo}")
    public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(titulo);
        return mv;
    }

    @DeleteMapping("{codigo}")
    public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
        this.titulos.deleteById(codigo);
        attributes.addFlashAttribute("mensagem", "Titulo Excluido Com Sucesso!");
        return "redirect:/titulos";
    }

    @RequestMapping
    public ModelAndView pesquisar() {
        List<Titulo> todosTitulos = this.titulos.findAll();
        ModelAndView mv = new ModelAndView("PesquisaTitulos");
        mv.addObject("titulos", todosTitulos);
        return mv;
    }

    @ModelAttribute("todosStatus")
    public List<StatusTitulo> listarStatus() {
        return Arrays.asList(StatusTitulo.values());
    }
}
