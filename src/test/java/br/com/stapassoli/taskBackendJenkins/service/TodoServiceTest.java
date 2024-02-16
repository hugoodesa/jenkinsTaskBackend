package br.com.stapassoli.taskBackendJenkins.service;

import static org.junit.jupiter.api.Assertions.*;

import br.com.stapassoli.taskBackendJenkins.testcontainers.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    void getTodoById() {
    }

    @Test
    void updateTodo() {
    }

    @Test
    void createTodo() {
    }

    @Test
    void getAllTodos() {
    }

    @Test
    void deleteTodoById() {
    }
}