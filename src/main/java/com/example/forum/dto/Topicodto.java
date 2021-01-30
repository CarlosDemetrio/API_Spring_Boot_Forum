package com.example.forum.dto;

import com.example.forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Topicodto {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime data;

    public Topicodto(Topico topico){
      this.id = topico.getId();
      this.titulo = topico.getTitulo();
      this.mensagem = topico.getMensagem();
      this.data = topico.getDataCriacao();
    }


    //converte topico para topicodto usando Stream do java 8
    public static List<Topicodto> converter(List<Topico> topicos){
        return topicos.stream().map(Topicodto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }
}
