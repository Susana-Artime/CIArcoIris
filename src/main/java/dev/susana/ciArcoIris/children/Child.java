package dev.susana.ciArcoIris.children;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import dev.susana.ciArcoIris.classrooms.Classroom;
import dev.susana.ciArcoIris.guardians.Guardian;
import lombok.*;
    
    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "children")
    public class Child {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @ManyToOne
        @JoinColumn(name = "id_classroom", nullable = false)
        private Classroom classroom;

        @OneToMany
        @JoinColumn(name = "id_guardian", nullable = false)
        private Guardian guardian;
    
        @Column(nullable = false)
        private String name;
    
        @Column(nullable = false)
        private Date dayBirth;

        private String comments;
        
}
