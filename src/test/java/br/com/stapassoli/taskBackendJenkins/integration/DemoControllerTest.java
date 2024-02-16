package br.com.stapassoli.taskBackendJenkins.integration;

import static org.hamcrest.Matchers.is;

import br.com.stapassoli.taskBackendJenkins.entity.Todo;
import br.com.stapassoli.taskBackendJenkins.testcontainers.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

//@ActiveProfiles(value = "application-container-test")
public class DemoControllerTest extends AbstractIntegrationTest {

    @Autowired
    TodoRepository todoRepository;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        todoRepository.deleteAll();
    }

    @Test
    public void test() {
        Todo todo = Todo.builder().title("Title Do Dragon").createdAt(LocalDateTime.now()).description("Description Ball").build();
        this.todoRepository.save(todo);

        Todo savedTodo = this.todoRepository.save(Todo.builder().createdAt(LocalDateTime.now()).title("title").description("teste Hugo").build());

        RestAssured
            .get("/task/" + savedTodo.getId())
            .then()
            .statusCode(200)
            .and().body("description", is("teste Hugo"));
    }

}
