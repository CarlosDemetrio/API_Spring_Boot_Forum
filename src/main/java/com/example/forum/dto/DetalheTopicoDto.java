package com.example.forum.dto;

import com.example.forum.modelo.StatusTopico;
import com.example.forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DetalheTopicoDto {
    private String titulo;
    private String mensagem;
    private LocalDateTime data;
    private String nomeAutor;
    private StatusTopico status;
    private List<RespostaDto> respostas;
    public DetalheTopicoDto(Topico topico){
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.data = topico.getDataCriacao();
        this.nomeAutor = topico.getAutor().getNome();
        this.status = topico.getStatus();
        this.respostas = new ArrayList<>();
        this.respostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
    }

}
