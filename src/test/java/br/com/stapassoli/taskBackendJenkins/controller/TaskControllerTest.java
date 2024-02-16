package br.com.stapassoli.taskBackendJenkins.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.stapassoli.taskBackendJenkins.dto.TodoDTO;
import br.com.stapassoli.taskBackendJenkins.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @Test
    public void shouldCreateTask() throws Exception {

        TodoDTO build = TodoDTO.builder().todoDescription("description").todoTitle("Title").build();

        given(todoService.createTodo(any())).willReturn(build);

        String contentAsString = mockMvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TodoDTO.builder().todoDescription("description").todoTitle("Title").build())))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        TodoDTO todoDTO = objectMapper.readValue(contentAsString, TodoDTO.class);

        Assertions.assertEquals(todoDTO.getTodoTitle(), "Title");

    }

    @Test
    public void shouldFindAllTasks() throws Exception {

        TodoDTO first = TodoDTO.builder().todoDescription("description #1").todoTitle("Title #1").build();
        TodoDTO second = TodoDTO.builder().todoDescription("description #2").todoTitle("Title #2").build();
        TodoDTO third = TodoDTO.builder().todoDescription("description #3").todoTitle("Title #3").build();

        List<TodoDTO> taskList = List.of(first, second, third);
        PageImpl<TodoDTO> page = new PageImpl<>(taskList);
        given(this.todoService.getAllTodos(any())).willReturn(page);

        ResultActions resultActions = mockMvc
            .perform(get("/task")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        resultActions
            .andDo(System.out::println)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.size()", is(taskList.size())));
    }

    @Test
    public void shouldFindTaskById() throws Exception {

        TodoDTO todoDTO = TodoDTO.builder().todoTitle("Title").todoDescription("Description").createdAt(LocalDateTime.now()).build();
        given(this.todoService.getTodoById(any())).willReturn(ResponseEntity.ok(todoDTO));

        mockMvc
            .perform(get("/task/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("Title")));

    }

}