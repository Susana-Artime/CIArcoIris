package dev.susana.ciArcoIris.children;

import dev.susana.ciArcoIris.classrooms.Classroom;
import dev.susana.ciArcoIris.classrooms.ClassroomRepository;
import dev.susana.ciArcoIris.guardians.Guardian;
import dev.susana.ciArcoIris.guardians.GuardianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ChildServiceTest {

    @InjectMocks
    private ChildService childService;

    @Mock
    private ChildRepository childRepository;

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private GuardianRepository guardianRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateChild() {
        
        Classroom classroom = new Classroom();
        classroom.setId(1L);
        classroom.setName("Aula Pollitos");

        Child child = new Child();
        child.setId(1L);
        child.setName("Child 1");
        child.setDayBirth(LocalDate.of(2022, 6, 1));
        child.setComments("Test child");
        child.setClassroom(classroom);

        ChildDTO childDTO = ChildDTO.builder()
                .name("Child 1")
                .dayBirth(LocalDate.of(2021, 12, 28))
                .comments("Test child")
                .build();

        when(classroomRepository.findByName("Aula Ardillas")).thenReturn(Optional.of(classroom));
        when(childRepository.save(any(Child.class))).thenReturn(child);

       
        ChildDTO result = childService.createChild(childDTO);

        
        assertNotNull(result);
        assertEquals("Child 1", result.getName());
        assertEquals("Test child", result.getComments());
        assertEquals(1L, result.getId_classroom());

        verify(classroomRepository, times(1)).findByName("Aula Ardillas");
        verify(childRepository, times(1)).save(any(Child.class));
    }

    @Test
    void testGetAllChildren() {
        
        Classroom classroom = new Classroom();
        classroom.setId(1L);

        Child child1 = new Child();
        child1.setId(1L);
        child1.setName("Child 1");
        child1.setClassroom(classroom);

        Child child2 = new Child();
        child2.setId(2L);
        child2.setName("Child 2");
        child2.setClassroom(classroom);

        when(childRepository.findAll()).thenReturn(List.of(child1, child2));

        
        List<ChildDTO> result = childService.getAllChildren();

        
        assertEquals(2, result.size());
        assertEquals("Child 1", result.get(0).getName());
        assertEquals("Child 2", result.get(1).getName());

        verify(childRepository, times(1)).findAll();
    }

    @Test
    void testUpdateChild() {
        
        Child child = new Child();
        child.setId(1L);
        child.setName("Old Name");
        child.setDayBirth(LocalDate.of(2024, 3, 3));
        child.setComments("Old comment");

        ChildDTO childDTO = ChildDTO.builder()
                .name("New Name")
                .dayBirth(LocalDate.of(2024, 3, 3))
                .comments("New comment")
                .build();

        when(childRepository.findById(1L)).thenReturn(Optional.of(child));
        when(childRepository.save(any(Child.class))).thenReturn(child);

        
        ChildDTO result = childService.updateChild(childDTO, 1L);

        
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("New comment", result.getComments());

        verify(childRepository, times(1)).findById(1L);
        verify(childRepository, times(1)).save(child);
    }

    @Test
    void testGetChildById() {
        
        Child child = new Child();
        child.setId(1L);
        child.setName("Child 1");
        child.setDayBirth(LocalDate.of(2020, 6, 1));
        child.setComments("Test child");

        when(childRepository.findById(1L)).thenReturn(Optional.of(child));

       
        ChildDTO result = childService.getChildById(1L);

       
        assertNotNull(result);
        assertEquals("Child 1", result.getName());
        assertEquals("Test child", result.getComments());
        verify(childRepository, times(1)).findById(1L);
    }

    @Test
    void testAssignGuardian() {
       
        Child child = new Child();
        child.setId(1L);
        child.setName("Child 1");

        Guardian guardian = new Guardian();
        guardian.setId(1L);
        guardian.setName("Guardian 1");
        guardian.setChild(child);

        when(childRepository.findById(1L)).thenReturn(Optional.of(child));
        when(guardianRepository.findById(1L)).thenReturn(Optional.of(guardian));
        when(childRepository.save(any(Child.class))).thenReturn(child);

        
        ChildDTO result = childService.assignGuardian(1L, 1L);

        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1, result.getGuardians().size());
        assertEquals("Guardian 1", result.getGuardians().get(0).getName());

        verify(childRepository, times(1)).findById(1L);
        verify(guardianRepository, times(1)).findById(1L);
        verify(childRepository, times(1)).save(any(Child.class));
    }

    @Test
    void testDeleteChild() {
        
        Child child = new Child();
        child.setId(1L);

        when(childRepository.findById(1L)).thenReturn(Optional.of(child));

        
        childService.deleteChild(1L);

        
        verify(childRepository, times(1)).findById(1L);
        verify(childRepository, times(1)).delete(child);
    }
}