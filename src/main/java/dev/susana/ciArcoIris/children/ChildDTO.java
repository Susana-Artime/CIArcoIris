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
    private String name;
    private LocalDate dayBirth;
    private String comments;
    private List<GuardianDTO> guardians;


    public ChildDTO(Long id, String name, LocalDate dayBirth, String comments) {
        this.id = id;
        this.name = name;
        this.dayBirth = dayBirth;
        this.comments = comments;
    }

}

