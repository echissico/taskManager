package com.example.demo.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.Todo;
import com.example.demo.repositories.TodoRepository;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;


@Controller
// @RequestMapping("/")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @GetMapping("/listar")
    public ModelAndView list(){
        return new ModelAndView("todo/list", Map.of("todos", todoRepository.findAll()));
    }


    @GetMapping("/criar")
    public ModelAndView create(){
        return new ModelAndView("todo/form", Map.of("todo", new Todo()));
    }

    @PostMapping("/criar")
    public String create(Todo todo){
        todoRepository.save(todo);
        return "redirect:/listar";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edit(@PathVariable Long id){
        var todo  = todoRepository.findById(id);
        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ModelAndView("todo/form", Map.of("todo", todo.get()));
    }

    @PostMapping("/editar")
    public String  edit(Todo todo){
        todoRepository.save(todo);
        return "redirect:/listar";
    }

    @DeleteMapping("/apagar/{id}")
    public String  apagar(@PathVariable Long id){
        todoRepository.deleteById(id);
        return "redirect:/listar";
    }







    // @GetMapping("/")
    // public ModelAndView home(){
    //     var modelAndView = new ModelAndView("home");
    //     modelAndView.addObject("nome", "Lista");
    //     var pessoas = List.of("Eddy Chissico", "Ana Joana", "Humana Hunma");
    //     modelAndView.addObject("pessoas", pessoas);

    //     var todos = todoRepository.findAll();
    //     System.out.println(todos);

    //     return modelAndView;
    // }

    
}
