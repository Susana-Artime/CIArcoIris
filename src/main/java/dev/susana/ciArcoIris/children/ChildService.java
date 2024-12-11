package dev.susana.ciArcoIris.children;

import dev.susana.ciArcoIris.classrooms.Classroom;
import dev.susana.ciArcoIris.classrooms.ClassroomRepository;
import dev.susana.ciArcoIris.guardians.GuardianRepository;
import dev.susana.ciArcoIris.guardians.Guardian;
import dev.susana.ciArcoIris.guardians.GuardianDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private GuardianRepository guardianRepository;

    public ChildDTO createChild(ChildDTO childDTO) {
        Child child = new Child();
        child.setName(childDTO.getName());
        child.setDayBirth(childDTO.getDayBirth());
        child.setComments(childDTO.getComments());

        if (childDTO.getGuardians() != null && !childDTO.getGuardians().isEmpty()) {
            List<Guardian> guardians = childDTO.getGuardians()
                    .stream()
                    .map(guardianDTO -> new Guardian(guardianDTO.getId(), child, guardianDTO.getName(),guardianDTO.getEmail(),guardianDTO.getPhone(),guardianDTO.getRelationship()))
                    .toList();
            child.setGuardians(guardians);
        }

        Child savedChild = childRepository.save(child);

        return mapToDTO(savedChild);
    }


    public List<ChildDTO> getAllChildren() {
        return childRepository.findAll()
                .stream()
                .map(child -> new ChildDTO(
                        child.getId(),
                        child.getClassroom() != null ? child.getClassroom().getId() : null,
                        child.getName(),
                        child.getDayBirth(),
                        child.getComments()
                       
                ))
                .toList();
    }

    public ChildDTO getChildById(Long id) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Niño no encontrado con id: " + id));

        return new ChildDTO(
                child.getId(),
                child.getClassroom() != null ? child.getClassroom().getId() : null,
                child.getName(),
                child.getDayBirth(),
                child.getComments()
                
        );
    }

    public ChildDTO updateChild(ChildDTO childDTO, Long id) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Niño no encontrado con id: " + id));

        child.setName(childDTO.getName());
        child.setDayBirth(childDTO.getDayBirth());
        child.setComments(childDTO.getComments());

        Child updatedChild = childRepository.save(child);

        return new ChildDTO(
                updatedChild.getId(),
                updatedChild.getClassroom() != null ? updatedChild.getClassroom().getId() : null,
                updatedChild.getName(),
                updatedChild.getDayBirth(),
                updatedChild.getComments()
                
        );
    }

    public ChildDTO assignGuardian(Long id, Long idGuardian) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Niño no encontrado con id: " + id));

        Guardian guardian = guardianRepository.findById(idGuardian)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado con id: " + idGuardian));

        guardian.setChild(child);
        guardianRepository.save(guardian);

        if (child.getGuardians() == null) {
        child.setGuardians(new ArrayList<>());
        }
        if (!child.getGuardians().contains(guardian)) {
        child.getGuardians().add(guardian);
        }

        Child updatedChild = childRepository.save(child);

        return ChildDTO.builder()
        .id(updatedChild.getId())
        .id_classroom(updatedChild.getClassroom() != null ? updatedChild.getClassroom().getId() : null)
        .name(updatedChild.getName())
        .dayBirth(updatedChild.getDayBirth())
        .comments(updatedChild.getComments())
        .guardians(updatedChild.getGuardians() != null
                ? updatedChild.getGuardians().stream()
                    .map(g -> GuardianDTO.builder()
                            .id(g.getId())
                            .childId(updatedChild.getId())
                            .name(g.getName())
                            .email(g.getEmail())
                            .phone(g.getPhone())
                            .relationship(g.getRelationship())
                            .build())
                    .toList()
                : null)
        .build();
    }

 

    public ChildDTO assignClassroom(Long id, Long idClassroom) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Niño no encontrado con id: " + id));

        Classroom classroom = classroomRepository.findById(idClassroom)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada con id: " + idClassroom));

        child.setClassroom(classroom);
        Child updatedChild = childRepository.save(child);

        return ChildDTO.builder()
        .id(updatedChild.getId())
        .id_classroom(updatedChild.getClassroom() != null ? updatedChild.getClassroom().getId() : null)
        .name(updatedChild.getName())
        .dayBirth(updatedChild.getDayBirth())
        .comments(updatedChild.getComments())
        .guardians(updatedChild.getGuardians() != null
                ? updatedChild.getGuardians().stream()
                    .map(g -> GuardianDTO.builder()
                            .id(g.getId())
                            .childId(updatedChild.getId())
                            .name(g.getName())
                            .email(g.getEmail())
                            .phone(g.getPhone())
                            .relationship(g.getRelationship())
                            .build())
                    .toList()
                : null)
        .build();
    }
 

    public void deleteChild(Long id) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Niño no encontrado con id: " + id));

        childRepository.delete(child);
    }

    private ChildDTO mapToDTO(Child child) {
        return ChildDTO.builder()
                .id(child.getId())
                .name(child.getName())
                .dayBirth(child.getDayBirth())
                .comments(child.getComments())
                .guardians(child.getGuardians() != null ? child.getGuardians().stream()
                        .map(guardian -> GuardianDTO.builder()
                        .id(guardian.getId())
                        .childId(child.getId())
                        .name(guardian.getName())
                        .email(guardian.getEmail())
                        .phone(guardian.getPhone())
                        .relationship(guardian.getRelationship())
                .build())
                        .toList(): null)
                .build();
        }
}