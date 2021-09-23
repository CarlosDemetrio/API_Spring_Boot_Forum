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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping //os parametros vem do GET topicos?page=0&size=10&sort=id,asc
    public Page<Topicodto> lista(@RequestParam(required = false) String nomeCurso,
                                 @PageableDefault(sort = "id", size = 20) Pageable paginacao) {



        if (nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return Topicodto.converter(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
            return Topicodto.converter(topicos);
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<Topicodto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new Topicodto(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    @Transactional//requestBody avisa ao spring que o parametro vem do metodo post; Valid ativa validacoes
    public ResponseEntity<Topicodto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {

        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        //necessário para devolver codigo 201 de cadastrado com sucesso
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new Topicodto(topico));//uri e o caminho do endereco do item cadastrado, o body retorna como ele foi cadastrado
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Topicodto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarTopicoForm form, UriComponentsBuilder uriComponentsBuilder) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new Topicodto(topico));
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
