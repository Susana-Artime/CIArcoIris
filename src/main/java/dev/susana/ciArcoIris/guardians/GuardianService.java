package dev.susana.ciArcoIris.guardians; 
import dev.susana.ciArcoIris.children.Child;
import dev.susana.ciArcoIris.children.ChildRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import java.util.List; 
import java.util.stream.Collectors; 


@Service 
public class GuardianService { 
    @Autowired 
    private GuardianRepository guardianRepository; 
    @Autowired private ChildRepository childRepository; 
    
    public GuardianDTO createGuardian(GuardianDTO guardianDTO) { 

         Child child = childRepository.findById(guardianDTO.getChildId())
         .orElseThrow(() -> new RuntimeException("Niño no encontrado con id: " + guardianDTO.getChildId())); 
         
         Guardian guardian = Guardian.builder() 

         .child(child) 
         .name(guardianDTO.getName()) 
         .email(guardianDTO.getEmail()) 
         .phone(guardianDTO.getPhone()) 
         .relationship(guardianDTO.getRelationship()) 
         .build(); 
         Guardian savedGuardian = guardianRepository.save(guardian); 
         return mapToDTO(savedGuardian);

    
    }
    
    public GuardianDTO getGuardianById(Long id) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guardián no encontrado con id: " + id));

        return mapToDTO(guardian);
    }

    public List<GuardianDTO> getGuardiansByChildId(Long childId) {

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Niño no encontrado con id: " + childId));

        List<Guardian> guardians = guardianRepository.findByChildId(childId);
        return guardians.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<GuardianDTO> getAllGuardians() {
        List<Guardian> guardians = guardianRepository.findAll();
        return guardians.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public GuardianDTO updateGuardian(Long id, GuardianDTO guardianDTO) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guardián no encontrado con id: " + id));

        guardian.setName(guardianDTO.getName());
        guardian.setEmail(guardianDTO.getEmail());
        guardian.setPhone(guardianDTO.getPhone());
        guardian.setRelationship(guardianDTO.getRelationship());

        Guardian updatedGuardian = guardianRepository.save(guardian);
        return mapToDTO(updatedGuardian);
    }

    public void deleteGuardian(Long id) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guardián no encontrado con id: " + id));
        guardianRepository.delete(guardian);
    }

    private GuardianDTO mapToDTO(Guardian guardian) {
        return GuardianDTO.builder()
                .id(guardian.getId())
                .childId(guardian.getChild().getId())
                .name(guardian.getName())
                .email(guardian.getEmail())
                .phone(guardian.getPhone())
                .relationship(guardian.getRelationship())
                .build();
    }
}

