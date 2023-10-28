package ru.sharov.tasklist.backendspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sharov.tasklist.backendspringboot.entity.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
