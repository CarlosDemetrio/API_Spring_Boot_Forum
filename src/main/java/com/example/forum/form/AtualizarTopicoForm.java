package com.example.forum.form;

import com.example.forum.modelo.Topico;
import com.example.forum.repository.TopicoRepository;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AtualizarTopicoForm {
    @NotEmpty
    @NotNull
    @Max(value = 60) @Min(value = 10)
    private String titulo;
    @NotEmpty @NotNull @Max(value = 255) @Min(value = 1)
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getOne(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}
