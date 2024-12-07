package dev.susana.ciArcoIris.guardians;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import dev.susana.ciArcoIris.children.Child;
import lombok.*;

    @Entity
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "guardians")
    public class Guardian {
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @ManyToOne
        @JoinColumn(name = "id_child", nullable = false)
        private Child child;

        @Column(nullable = false)
        private String name;
        
        @Column(nullable = false)
        private String email;
        
        @Column(nullable = false)
        private String phone;
                
        @Column(nullable = false)
        private String relationship;
    

    }
