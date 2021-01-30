package com.example.forum.controller;


import com.example.forum.dto.Topicodto;
import com.example.forum.modelo.Topico;
import com.example.forum.modelo.Curso;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicosController {


    @RequestMapping("/topicos")
    public List<Topicodto> lista(){
        Topico topico = new Topico("duvida", "duvida",new Curso("Spring","programação"));

        return Topicodto.converter(Arrays.asList(topico, topico, topico));
    }
}
