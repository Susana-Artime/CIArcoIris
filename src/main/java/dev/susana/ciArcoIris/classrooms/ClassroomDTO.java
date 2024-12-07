package dev.susana.ciArcoIris.classrooms;

import java.util.List;
import dev.susana.ciArcoIris.children.Child;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDTO {

    private Long id;
    private Long userId;
    private String name;
    private int minAge;
    private int maxAge;
    private List<ChildDTO> children;
    


}
