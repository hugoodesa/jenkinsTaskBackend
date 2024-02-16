package br.com.stapassoli.taskBackendJenkins.h2.repository;

import br.com.stapassoli.taskBackendJenkins.dto.TodoDTO;
import br.com.stapassoli.taskBackendJenkins.entity.Todo;
import br.com.stapassoli.taskBackendJenkins.testcontainers.TodoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles(value = "test")
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    private Todo todoGlobal;

    @BeforeEach
    public void setup() {
        todoGlobal = this.todoRepository.save(buildTodo());
    }

    @DisplayName("Save todo")
    @Test
    public void shouldSaveATodo() {
        Todo todo = Todo.builder().createdAt(LocalDateTime.now()).title("MyTitle").description("Description").build();

        Todo save = todoRepository.save(todo);
        Assertions.assertNotNull(save);
        Assertions.assertNotNull(save.getId());

    }

    @Test
    public void shouldReturnAPageOfTodo() {
        Todo todoFirst = Todo.builder().createdAt(LocalDateTime.now()).title("MyTitle1").description("Description1").build();
        Todo todoSecond = Todo.builder().createdAt(LocalDateTime.now()).title("MyTitle2").description("Description2").build();

        todoRepository.saveAll(List.of(todoFirst,todoSecond));
        List<Todo> todos = this.todoRepository.findAll();

        Assertions.assertNotNull(todos);
        Assertions.assertEquals(todos.size(),2);
        Assertions.assertEquals(todos.get(0).getDescription(),todoFirst.getDescription());
    }

    @Test
    @DisplayName("goona save a todo then return this one by id")
    public void shouldSaveTodoThenCheckIfWaSaved () {

        Todo savedTodo = this.todoRepository.save(buildTodo());
        Long savedTodoId = savedTodo.getId();

        Todo todo = this.todoRepository.findById(savedTodoId).orElse(null);

        Assertions.assertNotNull(todo);
        Assertions.assertNotNull(todo.getId());
        Assertions.assertEquals("Todo tile", todo.getTitle());
    }

    @Test
    @DisplayName("gonna save a todo , then find by title")
    public void shouldSaveTodoThenFindByTitle () {

        //this.todoRepository.save(buildTodo());

        String todoTitle = "Todo title";
        Todo todo = this.todoRepository.findByTitle(todoTitle);

        Assertions.assertNotNull(todo);
        Assertions.assertNotNull(todo.getId());
        Assertions.assertEquals("Todo title", todo.getTitle());
    }

    private Todo buildTodo() {
        return Todo.builder().title("Todo title").description("description").createdAt(LocalDateTime.now()).build();
    }

}
