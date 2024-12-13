package dev.susana.ciArcoIris.classrooms;

import java.util.ArrayList;
import java.util.List;
import dev.susana.ciArcoIris.users.User;
import dev.susana.ciArcoIris.children.Child;
import jakarta.persistence.*;
import lombok.*;
    
    @Entity
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "classrooms")
    public class Classroom {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "id_user")
        private User user;
    
        @Column(nullable = false)
        private String name;
        
        @Column(nullable = false)
        private int minAge;
        
        @Column(nullable = false)
        private int maxAge;
    
        @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
        @Builder.Default
        private List<Child> children = new ArrayList<>(); 
        
        
}
    
