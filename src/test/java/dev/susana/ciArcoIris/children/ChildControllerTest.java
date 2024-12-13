package dev.susana.ciArcoIris.children;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChildControllerTest {

    @Mock
    private ChildService childService;

    @InjectMocks
    private ChildController childController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddChild() {
        
       ChildDTO childDTO = new ChildDTO(null, null, "Child 1", LocalDate.parse("2015-06-01"), "No comments");
       ChildDTO savedChild = new ChildDTO(1L,2L, "Child 1", LocalDate.parse("2015-06-01"), "No comments");

        when(childService.createChild(childDTO)).thenReturn(savedChild);

        
        ResponseEntity<ChildDTO> response = childController.addChild(childDTO);

        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedChild, response.getBody());
        verify(childService, times(1)).createChild(childDTO);
    }

    @Test
    void testGetAllChildren() {
       
        List<ChildDTO> children = List.of(
            new ChildDTO(null, null, "Child 1", LocalDate.parse("2015-06-01"), "No comments"));
        

        when(childService.getAllChildren()).thenReturn(children);

        
        ResponseEntity<List<ChildDTO>> response = childController.getAllChildren();

        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(children, response.getBody());
        verify(childService, times(1)).getAllChildren();
    }

    @Test
    void testGetChildById() {
       
        Long childId = 1L;
        ChildDTO child = new ChildDTO(childId, null, "Child 1", LocalDate.parse("2015-06-01"), "No comments");

        when(childService.getChildById(childId)).thenReturn(child);

        
        ResponseEntity<ChildDTO> response = childController.getChildById(childId);

        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());;
        assertEquals(child, response.getBody());
        verify(childService, times(1)).getChildById(childId);
    }

    @Test
    void testUpdateChild() {
        
        Long childId = 1L;
        ChildDTO updatedChild = new ChildDTO(childId, 3L, "Updated Child", LocalDate.parse("2010-06-01"), "Updated comments");
       

        when(childService.updateChild(updatedChild, childId)).thenReturn(updatedChild);

        
        ResponseEntity<ChildDTO> response = childController.updateChild(childId, updatedChild);

        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedChild, response.getBody());
        verify(childService, times(1)).updateChild(updatedChild, childId);
    }

    @Test
    void testAssignGuardianToChild() {
        
        Long childId = 1L;
        Long guardianId = 2L;
        ChildDTO childWithGuardian = new ChildDTO(childId, 2L,"Child 1", LocalDate.parse("2015-06-01"), "No comments");
       
        when(childService.assignGuardian(childId, guardianId)).thenReturn(childWithGuardian);

        
        ResponseEntity<ChildDTO> response = childController.assingGuardianToChild(childId, guardianId);

        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(childWithGuardian, response.getBody());
        verify(childService, times(1)).assignGuardian(childId, guardianId);
    }

    @Test
    void testDeleteChild() {
        
        Long childId = 1L;

        doNothing().when(childService).deleteChild(childId);

        ResponseEntity<Void> response = childController.deleteChild(childId);

       
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(childService, times(1)).deleteChild(childId);
    }
}