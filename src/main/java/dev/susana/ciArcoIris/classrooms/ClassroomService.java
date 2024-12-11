package dev.susana.ciArcoIris.classrooms;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.susana.ciArcoIris.children.ChildDTO;
import dev.susana.ciArcoIris.config.CustomUserDetailsService;
import dev.susana.ciArcoIris.users.User;
import dev.susana.ciArcoIris.users.UserRepository;

@Service
public class ClassroomService {

     @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public ClassroomDTO createClass(ClassroomDTO classroomDTO) {
      
        User user = userRepository.findById(classroomDTO.getUserId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado con id: " + classroomDTO.getUserId());
        }
        
        Classroom classroom = new Classroom();
        classroom.setUser(user);
        classroom.setName(classroomDTO.getName());
        classroom.setMinAge(classroomDTO.getMinAge());
        classroom.setMaxAge(classroomDTO.getMaxAge());

        Classroom savedClassroom = classroomRepository.save(classroom);

        return new ClassroomDTO(
                savedClassroom.getId(),
                savedClassroom.getUser().getId(),
                savedClassroom.getName(),
                savedClassroom.getMinAge(),
                savedClassroom.getMaxAge(),
                null
        );
    }

    public List<ClassroomDTO> getAll() {
        return classroomRepository.findAll()
                .stream()
                .map(classroom -> new ClassroomDTO(
                        classroom.getId(),
                        classroom.getUser().getId(),
                        classroom.getName(),
                        classroom.getMinAge(),
                        classroom.getMaxAge(),
                        classroom.getChildren() != null
                                ? classroom.getChildren().stream()
                                .map(child -> new ChildDTO(child.getId(), child.getClassroom().getId(), child.getName(), child.getDayBirth(), null))
                                .toList()
                                : null
                ))
                .toList();
    }

    public List<ChildDTO> getChildrenByClassroomId(Long id) {
    Classroom classroom = classroomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aula no encontrada con id: " + id));

    return classroom.getChildren() != null
            ? classroom.getChildren().stream()
                .map(child -> new ChildDTO(
                    child.getId(),
                    child.getClassroom().getId(),
                    child.getName(),
                    child.getDayBirth(),
                    null)) 
                .toList()
                : List.of();
            
    }

    public ClassroomDTO getClassroomById(Long id) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada con id: " + id));
    
        List<ChildDTO> children = (classroom.getChildren() != null)
                ? classroom.getChildren().stream()
                    .map(child -> new ChildDTO(
                        child.getId(),
                        child.getClassroom().getId(),
                        child.getName(),
                        child.getDayBirth(),
                        null))
                    .toList()
                : null;
    
        return new ClassroomDTO(
                classroom.getId(),
                classroom.getUser().getId(),
                classroom.getName(),
                classroom.getMinAge(),
                classroom.getMaxAge(),
                children
        );
    }

    public ClassroomDTO updateClassroom(ClassroomDTO classroomDTO, Long id) {
        
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada con id: " + id));

       
        classroom.setName(classroomDTO.getName());
        classroom.setMinAge(classroomDTO.getMinAge());
        classroom.setMaxAge(classroomDTO.getMaxAge());

        
        Classroom updatedClassroom = classroomRepository.save(classroom);

        return new ClassroomDTO(
                updatedClassroom.getId(),
                updatedClassroom.getUser().getId(),
                updatedClassroom.getName(),
                updatedClassroom.getMinAge(),
                updatedClassroom.getMaxAge(),
                null
        );
    }

    public ClassroomDTO updateTeacher(Long id, Long idUser) {
       
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada con id: " + id));
       
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUser));
       
        classroom.setUser(user);
        Classroom updatedClassroom = classroomRepository.save(classroom);

        return new ClassroomDTO(
                updatedClassroom.getId(),
                updatedClassroom.getUser().getId(),
                updatedClassroom.getName(),
                updatedClassroom.getMinAge(),
                updatedClassroom.getMaxAge(),
                null
        );
    }

    public void deleteClassroom(Long id) {
        
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada con id: " + id));
        
        classroomRepository.delete(classroom);
    }

    public boolean isTeacherOfClassroom(Long classroomId) {
        User currentUser = customUserDetailsService.getCurrentUser();
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("El aula no existe"));

        return classroom.getUser().getId().equals(currentUser.getId());
    }

}