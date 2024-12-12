package dev.susana.ciArcoIris.classrooms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClassroomControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;  

        
    @Test
    void testCreateClassroomAsDirectora() throws Exception {
    mockMvc.perform(post("/api/admin/classrooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userId\": 4, \"name\": \"Aula Perritos\", \"minAge\": 0, \"maxAge\": 1}")
            .with(SecurityMockMvcRequestPostProcessors.user("directora").password("password").roles("Directora")))
            .andExpect(status().isCreated()) 
            .andExpect(jsonPath("$.userId").value(4))
            .andExpect(jsonPath("$.name").value("Aula Perritos"))
            .andExpect(jsonPath("$.minAge").value(0))
            .andExpect(jsonPath("$.maxAge").value(1))
            .andExpect(jsonPath("$.children").isEmpty());
}
    
    @Test
    void testCreateClassroomAsProfesora() throws Exception {
        mockMvc.perform(post("/api/admin/classrooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": 7, \"name\": \"Math 101\", \"minAge\": 5, \"maxAge\": 10,\"children\": null}")
                .with(SecurityMockMvcRequestPostProcessors.user("profesora").password("password").roles("Profesora")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetAllClassroomsAsDirectora() throws Exception {
        mockMvc.perform(get("/api/admin/classrooms")
                .with(SecurityMockMvcRequestPostProcessors.user("directora").password("password").roles("Directora")))
                .andExpect(status().isOk()) 
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testGetAllClassroomsAsProfesora() throws Exception {
        mockMvc.perform(get("/api/admin/classrooms")
                .with(SecurityMockMvcRequestPostProcessors.user("profesora").password("password").roles("Profesora")))
                .andExpect(status().isForbidden());  // Expect 403 forbidden
    }

    @Test
    void testGetClassroomByIdAsDirectora() throws Exception {
        
        mockMvc.perform(get("/api/admin/classrooms/2")
                .with(SecurityMockMvcRequestPostProcessors.user("directora").password("password").roles("Directora")))
                .andExpect(status().isOk()) 
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Aula Patitos"));
    }

    @Test
    void testGetChildrenByClassroomIdAsProfesora() throws Exception {
        
        mockMvc.perform(get("/api/classrooms/4/children")
                .with(SecurityMockMvcRequestPostProcessors.user("mariagarcia@ciarcoiris.com").password("ciarcoiris#profe2").roles("Profesora")))
                .andExpect(status().isOk()) 
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Carlos Pérez"));
                
    }

    @Test
    void testUpdateClassroomAsDirectora() throws Exception {
        mockMvc.perform(put("/api/admin/classrooms/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Classroom\", \"minAge\": 6, \"maxAge\": 12}")
                .with(SecurityMockMvcRequestPostProcessors.user("directora").password("password").roles("Directora")))
                .andExpect(status().isOk())  // Expect 200 OK
                .andExpect(jsonPath("$.name").value("Updated Classroom"));
    }

    @Test
    void testDeleteClassroomAsDirectora() throws Exception {
        mockMvc.perform(delete("/api/admin/classrooms/3")
                .with(SecurityMockMvcRequestPostProcessors.user("directora").password("password").roles("Directora")))
                .andExpect(status().isNoContent());  // Expect 204 No Content
    }

    @Test
    void testDeleteClassroomAsProfesora() throws Exception {
        mockMvc.perform(delete("/api/admin/classrooms/1")
                .with(SecurityMockMvcRequestPostProcessors.user("profesora").password("password").roles("Profesora")))
                .andExpect(status().isForbidden());  // Expect 403 Forbidden
    }

}
