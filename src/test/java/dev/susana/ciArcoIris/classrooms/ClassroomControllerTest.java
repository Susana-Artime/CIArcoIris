package dev.susana.ciArcoIris.classrooms;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.susana.ciArcoIris.children.ChildDTO;


@ExtendWith(MockitoExtension.class)
public class ClassroomControllerTest {

    @Mock
    private ClassroomService classroomService;

    @InjectMocks
    private ClassroomController classroomController;

    @Test
    void testGetAllClassroom() {
        
        List<ClassroomDTO> mockClassrooms = List.of(
                new ClassroomDTO(1L, 1L, "Classroom A", 5, 10, null),
                new ClassroomDTO(2L, 2L, "Classroom B", 6, 12, null)
        );
        when(classroomService.getAll()).thenReturn(mockClassrooms);
       
        ResponseEntity<List<ClassroomDTO>> response = classroomController.getAllClassroom();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockClassrooms);
        verify(classroomService, times(1)).getAll();
    }

    @Test
    void testAddClassroom() {
       
        ClassroomDTO classroomToAdd = new ClassroomDTO(null, 1L, "Classroom A", 5, 10, null);
        ClassroomDTO savedClassroom = new ClassroomDTO(1L, 1L, "Classroom A", 5, 10, null);

        when(classroomService.createClass(classroomToAdd)).thenReturn(savedClassroom);

        
        ResponseEntity<ClassroomDTO> response = classroomController.addClassroom(classroomToAdd);

        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(savedClassroom);
        verify(classroomService, times(1)).createClass(classroomToAdd);
    }

    @Test
    void testGetClassroomById() {
        
        Long classroomId = 1L;
        ClassroomDTO mockClassroom = new ClassroomDTO(classroomId, 1L, "Classroom A", 5, 10, null);

        when(classroomService.getClassroomById(classroomId)).thenReturn(mockClassroom);
        
        ResponseEntity<ClassroomDTO> response = classroomController.getClassroomById(classroomId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockClassroom);
        verify(classroomService, times(1)).getClassroomById(classroomId);
    }

    @Test
    void testUpdateClassroom() {
        
        Long classroomId = 1L;
        ClassroomDTO classroomToUpdate = new ClassroomDTO(classroomId, 1L, "Updated Classroom", 6, 12, null);
        ClassroomDTO updatedClassroom = new ClassroomDTO(classroomId, 1L, "Updated Classroom", 6, 12, null);

        when(classroomService.updateClassroom(classroomToUpdate, classroomId)).thenReturn(updatedClassroom);
        
        ResponseEntity<ClassroomDTO> response = classroomController.updateClassroom(classroomId, classroomToUpdate);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedClassroom);
        verify(classroomService, times(1)).updateClassroom(classroomToUpdate, classroomId);
    }

    @Test
    void testDeleteClassroom() {
        
        Long classroomId = 1L;

        ResponseEntity<Classroom> response = classroomController.deleteClassroom(classroomId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(classroomService, times(1)).deleteClassroom(classroomId);
    }

    @Test
    void testGetChildrenByClassroomId() {
        
        Long classroomId = 1L;
        List<ChildDTO> mockChildren = List.of(
            new ChildDTO(1L, classroomId, "Child A","2015-03-25", "Good student"),
            new ChildDTO(2L, classroomId, "Child B", "2016-07-12", "Needs improvement")
        );

        when(classroomService.getChildrenByClassroomId(classroomId)).thenReturn(mockChildren);

        ResponseEntity<List<ChildDTO>> response = classroomController.getChildrenByClassroomId(classroomId);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockChildren);
        verify(classroomService, times(1)).getChildrenByClassroomId(classroomId);
    }

    @Test
    void testUpdateTeacher() {
        
        Long classroomId = 1L;
        Long newTeacherId = 2L;
        ClassroomDTO updatedClassroom = new ClassroomDTO(classroomId, newTeacherId, "Classroom A", 5, 10, null);

        when(classroomService.updateTeacher(classroomId, newTeacherId)).thenReturn(updatedClassroom);

        ResponseEntity<ClassroomDTO> response = classroomController.updateTeacher(classroomId, newTeacherId);

        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedClassroom);
        verify(classroomService, times(1)).updateTeacher(classroomId, newTeacherId);
    }

    @Test
    void testGetClassroomByIdThrowsException() {
        
        Long classroomId = 99L;
    String errorMessage = "Aula no encontrada con id: " + classroomId;

    when(classroomService.getClassroomById(classroomId)).thenThrow(new RuntimeException(errorMessage));

    
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        classroomController.getClassroomById(classroomId);
    });

    assertThat(exception.getMessage()).isEqualTo(errorMessage);
    verify(classroomService, times(1)).getClassroomById(classroomId);

  }

    @Test
    void testDeleteClassroomThrowsException() {
        
        Long classroomId = 99L;
    String errorMessage = "Aula no encontrada con id: " + classroomId;

    doThrow(new RuntimeException(errorMessage)).when(classroomService).deleteClassroom(classroomId);

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        classroomController.deleteClassroom(classroomId);
    });

    assertThat(exception.getMessage()).isEqualTo(errorMessage);
    verify(classroomService, times(1)).deleteClassroom(classroomId);
  }

}



