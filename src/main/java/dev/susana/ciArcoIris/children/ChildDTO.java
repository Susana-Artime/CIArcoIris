package dev.susana.ciArcoIris.children;

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
    private String dayBirth;
    private String comments;
    private List<GuardianDTO> guardians;
}

