package dev.susana.ciArcoIris.classrooms;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    
    Optional<Classroom> findByName(String name);
}

