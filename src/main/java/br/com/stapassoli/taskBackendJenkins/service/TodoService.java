package br.com.stapassoli.taskBackendJenkins.service;

import br.com.stapassoli.taskBackendJenkins.dto.TodoDTO;
import br.com.stapassoli.taskBackendJenkins.entity.Todo;
import br.com.stapassoli.taskBackendJenkins.testcontainers.TodoRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;


    // Read (Get) a Todo by ID with exception handling
    public ResponseEntity<TodoDTO> getTodoById(Long id) {
        return todoRepository.findById(id)
            .map(this::convertToTodoDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Update (Save) an existing Todo with exception handling
    @Transactional
    public ResponseEntity<TodoDTO> updateTodo(Long id, TodoDTO todoDTO) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setTitle(todoDTO.getTodoTitle());
            todo.setDescription(todoDTO.getTodoDescription());
            todo.setFinishedAt(todoDTO.getFinishedAt());

            Todo updatedTodo = todoRepository.save(todo);

            return ResponseEntity.ok(convertToTodoDTO(updatedTodo));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public TodoDTO createTodo(TodoDTO todoDTO) {
        Todo newTodo = new Todo();
        newTodo.setTitle(todoDTO.getTodoTitle());
        newTodo.setDescription(todoDTO.getTodoDescription());
        newTodo.setCreatedAt(LocalDateTime.now());

        return convertToTodoDTO(todoRepository.save(newTodo));
    }

    // List all Todos
    public Page<TodoDTO> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable).map(this::convertToTodoDTO);
    }

    // Delete a Todo by ID with exception handling
    public void deleteTodoById(Long id) {
        try {
            todoRepository.deleteById(id);
        } catch (Exception e) {
            // Handle the exception (e.g., log it) or rethrow a custom exception
        }
    }

    private TodoDTO convertToTodoDTO(Todo todo) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTodoTitle(todo.getTitle());
        todoDTO.setTodoDescription(todo.getDescription());
        todoDTO.setCreatedAt(todo.getCreatedAt());
        todoDTO.setFinishedAt(todo.getFinishedAt());
        return todoDTO;
    }


}
