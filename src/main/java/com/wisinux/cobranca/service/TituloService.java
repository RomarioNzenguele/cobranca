package com.wisinux.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.wisinux.cobranca.models.StatusTitulo;
import com.wisinux.cobranca.models.Titulo;
import com.wisinux.cobranca.repository.TituloFilter;
import com.wisinux.cobranca.repository.Titulos;

@Service
public class    TituloService {

    @Autowired
    Titulos titulos;

    public void salvar(Titulo titulo) {
        try {
            this.titulos.save(titulo);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Formato de data Invalido!");
        }
    }

    public void excluir(Long codigo) {
        this.titulos.deleteById(codigo);
    }

    public List<Titulo> getAll() {
        return this.titulos.findAll();
    }

    public List<Titulo> filtrar(TituloFilter filtro) {
        String descricao = filtro.getDescricao() == null ? "" : filtro.getDescricao();
        return this.titulos.findByDescricaoContaining(descricao);
    }

    public String receber(Long codigo) {
        Titulo titulo = this.titulos.getReferenceById(codigo);
        titulo.setStatus(StatusTitulo.RECEBIDO);
        titulos.save(titulo);

        return StatusTitulo.RECEBIDO.getDescricao();
    }

}
