package br.com.stapassoli.taskBackendJenkins.controller;

import br.com.stapassoli.taskBackendJenkins.dto.TodoDTO;
import br.com.stapassoli.taskBackendJenkins.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@RequestBody @Valid TodoDTO todoDTO) {
        TodoDTO createdTodo = todoService.createTodo(todoDTO);
        return ResponseEntity.ok(createdTodo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @GetMapping
    public Page<TodoDTO> getAllTodos(@PageableDefault Pageable pageable) {
        return todoService.getAllTodos(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable Long id, @RequestBody @Valid TodoDTO todoDTO) {
        return todoService.updateTodo(id, todoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }


}
