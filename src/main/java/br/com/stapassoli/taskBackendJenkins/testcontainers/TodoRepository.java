package br.com.stapassoli.taskBackendJenkins.testcontainers;

import br.com.stapassoli.taskBackendJenkins.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Todo findByTitle(String title);

}
