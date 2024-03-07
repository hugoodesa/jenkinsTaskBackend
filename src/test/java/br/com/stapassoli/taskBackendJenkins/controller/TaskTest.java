package br.com.stapassoli.taskBackendJenkins.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.stapassoli.taskBackendJenkins.dto.TodoDTO;
import br.com.stapassoli.taskBackendJenkins.entity.Todo;
import br.com.stapassoli.taskBackendJenkins.testcontainers.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class TaskTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void shouldSaveTodo() throws Exception {

        Todo todo = Todo.builder().createdAt(LocalDateTime.now()).title("MyTodo").description("Testing todo").build();

        var respJson = mockMvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todo)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        TodoDTO todoDTO = objectMapper.readValue(respJson, TodoDTO.class);

        Assertions.assertEquals(todo.getTitle(),todoDTO.getTodoTitle());

    }

}
