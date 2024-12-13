package dev.susana.ciArcoIris.guardians;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardianDTO {

    private Long id;
    private Long childId;
    private String name;
    private String email;
    private String phone;
    private String relationship;

    
}
