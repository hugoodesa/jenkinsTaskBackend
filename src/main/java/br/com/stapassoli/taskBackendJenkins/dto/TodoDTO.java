package br.com.stapassoli.taskBackendJenkins.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import br.com.stapassoli.taskBackendJenkins.entity.Todo;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    @NotNull
    @JsonProperty("title")
    private String todoTitle;

    @JsonProperty("description")
    private String todoDescription;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("finished_at")
    private LocalDateTime finishedAt;

    public Todo toEntity() {
        Todo todo = new Todo();
        todo.setTitle(this.todoTitle);
        todo.setDescription(this.todoDescription);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setFinishedAt(this.finishedAt);
        return todo;
    }

    public static TodoDTO fromEntity(Todo todo) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTodoTitle(todo.getTitle());
        todoDTO.setTodoDescription(todo.getDescription());
        todoDTO.setCreatedAt(todo.getCreatedAt());
        todoDTO.setFinishedAt(todo.getFinishedAt());
        return todoDTO;
    }
}