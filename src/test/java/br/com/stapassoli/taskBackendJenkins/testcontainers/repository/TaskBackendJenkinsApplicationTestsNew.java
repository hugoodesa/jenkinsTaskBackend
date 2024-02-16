package br.com.stapassoli.taskBackendJenkins.testcontainers.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.stapassoli.taskBackendJenkins.entity.Todo;
import br.com.stapassoli.taskBackendJenkins.testcontainers.TodoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskBackendJenkinsApplicationTestsNew {

	@LocalServerPort
	private Integer port;

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
		"postgres:15-alpine"
	);

	@BeforeAll
	static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	TodoRepository todoRepository;

	@BeforeEach
	void setUp () {
		Todo todo = Todo.builder().title("Study").id(1L).description("description").createdAt(LocalDateTime.now()).build();
		List<Todo> todos = List.of(todo);
		todoRepository.saveAll(todos);
	}

	@Test
	void connectionEstablished() {
		assertThat(postgres.isCreated()).isTrue();
		assertThat(postgres.isRunning()).isTrue();
	}

	@Test
	public void shouldReturnTodoByTitle() {
		Todo todo = todoRepository.findByTitle("Study");
		assertThat(todo).isNotNull();
	}

}
