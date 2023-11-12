package ru.sharov.tasklist.backendspringboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sharov.tasklist.backendspringboot.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT p FROM Task p WHERE " +
           "(:title IS NULL OR :title='' OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%') ) ) AND " +
           "(:completed IS NULL OR p.completed=:completed) AND " +
           "(:priorityId IS NULL OR p.priority.id=:priorityId) AND " +
           "(:categoryId IS NULL OR p.category.id=:categoryId)"
    )
    Page<Task> findByParam(@Param("title") String title,
                           @Param("completed") Integer completed,
                           @Param("priorityId") Long priorityId,
                           @Param("categoryId") Long categoryId,
                           Pageable pageable);
}
