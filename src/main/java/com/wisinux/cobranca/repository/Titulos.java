package com.wisinux.cobranca.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wisinux.cobranca.models.Titulo;

public interface Titulos extends JpaRepository<Titulo, Long> {

    public List<Titulo> findByDescricaoContaining(String descricao);
}
