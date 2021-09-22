package com.example.forum.controller;


import com.example.forum.dto.DetalheTopicoDto;
import com.example.forum.dto.Topicodto;
import com.example.forum.form.AtualizarTopicoForm;
import com.example.forum.form.TopicoForm;
import com.example.forum.modelo.Topico;
import com.example.forum.modelo.Curso;
import com.example.forum.repository.CursoRepository;
import com.example.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<Topicodto> lista(String nomeCurso){

        if(nomeCurso==null){
            List<Topico> topicos = topicoRepository.findAll();
            return Topicodto.converter(topicos);
        }
        else{
            List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
            return Topicodto.converter(topicos);
        }


    }

    @PostMapping
    @Transactional//requestBody avisa ao spring que o parametro vem do metodo post; Valid ativa validacoes
    public ResponseEntity<Topicodto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){

        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);


        //necessário para devolver codigo 201 de cadastrado com sucesso
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new Topicodto(topico));//uri e o caminho do endereco do item cadastrado, o body retorna como ele foi cadastrado
    }

    @GetMapping("/{id}")
    public DetalheTopicoDto detalhar(@PathVariable Long id){
        Topico topico = topicoRepository.getOne(id);
        return new DetalheTopicoDto(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Topicodto> atualizar(@PathVariable Long id,@RequestBody @Valid AtualizarTopicoForm form, UriComponentsBuilder uriComponentsBuilder){

        Topico topico = form.atualizar(id, topicoRepository);
        return ResponseEntity.ok(new Topicodto(topico));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id){
        topicoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
