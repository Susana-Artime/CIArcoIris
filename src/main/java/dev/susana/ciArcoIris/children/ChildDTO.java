package dev.susana.ciArcoIris.children;

import java.time.LocalDate;
import java.util.List;
import dev.susana.ciArcoIris.guardians.GuardianDTO;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildDTO {

    private Long id;
    private Long id_classroom;
    private String name;
    private LocalDate dayBirth;
    private String comments;
    private List<GuardianDTO> guardians;


    public ChildDTO(Long id, Long id_classroom, String name, LocalDate dayBirth, String comments) {
        this.id = id;
        this.id_classroom= id_classroom;
        this.name = name;
        this.dayBirth = dayBirth;
        this.comments = comments;
    }

}

