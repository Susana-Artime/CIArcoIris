package dev.susana.ciArcoIris.classrooms;

import dev.susana.ciArcoIris.config.CustomUserDetailsService;
import dev.susana.ciArcoIris.users.User;
import dev.susana.ciArcoIris.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ClassroomServiceTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private ClassroomService classroomService;

    private User user;
    private ClassroomDTO classroomDTO;
    private Classroom classroom;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks

        
        user = new User(1L,"prueba","password","prueba@ciarcoiris.com","675483920", "Directora");
        classroomDTO = new ClassroomDTO(null, user.getId(), "Aula Pollitos", 1, 2,null);
        classroom = new Classroom(1L, user, "Aula de Matemáticas", 6, 12, null);
       
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);
    }


    @Test
    public void testCreateClassroomSuccess() {
        ClassroomDTO result = classroomService.createClass(classroomDTO);

        assertNotNull(result);
        assertEquals("Aula de Matemáticas", result.getName());
        assertEquals(6, result.getMinAge());
        assertEquals(12, result.getMaxAge());
    }

    @Test
    public void testCreateClassroomUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            classroomService.createClass(new ClassroomDTO(null, 99L, "Aula de Matemáticas", 6, 12, null));
        });

        assertEquals("Usuario no encontrado con id: 99", thrown.getMessage());
    }

    @Test
public void testGetChildrenByClassroomIdAccessDenied() {
    
    Classroom mockClassroom = new Classroom();
    mockClassroom.setId(3L);
    mockClassroom.setUser(new User(1L, "directora", "password", "directora@ciarcoiris.com", "123456789", "Directora"));
    when(classroomRepository.findById(3L)).thenReturn(Optional.of(mockClassroom));

    
    when(customUserDetailsService.getCurrentUser()).thenReturn(new User(5L, "prueba", "password", "prueba@ciarcoiris.com", "675483920", "Profesora"));

    
    AccessDeniedException thrown = assertThrows(AccessDeniedException.class, () -> {
        classroomService.getChildrenByClassroomId(3L);
    });

    assertEquals("No tienes permisos para ver la aula con id: 3", thrown.getMessage());
    }

    @Test
    public void testGetClassroomByIdNotFound() {
        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            classroomService.getClassroomById(1L);
        });

        assertEquals("Aula no encontrada con id: 1", thrown.getMessage());
    }

    @Test
    public void testUpdateClassroom() {
        ClassroomDTO updatedClassroomDTO = new ClassroomDTO(1L, user.getId(), "Aula de Lengua", 6, 12, null);
        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        ClassroomDTO result = classroomService.updateClassroom(updatedClassroomDTO, 1L);

        assertNotNull(result);
        assertEquals("Aula de Lengua", result.getName());
        assertEquals(6, result.getMinAge());
        assertEquals(12, result.getMaxAge());
    }

    @Test
    public void testDeleteClassroom() {
        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));
        doNothing().when(classroomRepository).delete(any(Classroom.class));

        assertDoesNotThrow(() -> classroomService.deleteClassroom(1L));

        verify(classroomRepository, times(1)).delete(classroom);  
    } 
}
