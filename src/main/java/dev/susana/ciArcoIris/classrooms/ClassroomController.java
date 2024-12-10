package dev.susana.ciArcoIris.classrooms;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.susana.ciArcoIris.children.ChildDTO;


@RestController
@RequestMapping("/api")
public class ClassroomController {

     @Autowired
    private ClassroomService classroomService;

    @PostMapping(path = "/admin/create")
    public ResponseEntity<ClassroomDTO> addClassroom(@RequestBody ClassroomDTO classroom) {
        return new ResponseEntity<>(classroomService.createClass(classroom), HttpStatus.CREATED);
    }

    @GetMapping(path = "admin/classrooms")
    public ResponseEntity<List<ClassroomDTO>> getAllClassroom() {
        return new ResponseEntity<>(classroomService.getAll(), HttpStatus.OK);
    }

    @GetMapping (path = "admin/classrooms/{id}")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable Long id) {

        return new ResponseEntity<>(classroomService.getClassroomById(id), HttpStatus.OK);
        
    }

    @GetMapping("classrooms/{id}/children")
    public ResponseEntity<List<ChildDTO>> getChildrenByClassroomId(@PathVariable Long id) {
        return new ResponseEntity<>(classroomService.getChildrenByClassroomId(id), HttpStatus.OK);
    }

    @PutMapping(path = "/admin/classroooms/{id}")
    public ResponseEntity<ClassroomDTO> updateClassroom(@RequestBody ClassroomDTO classroom, @PathVariable Long id) {
        return new ResponseEntity<>(classroomService.updateClassroom(classroom, id), HttpStatus.OK);
    }

    @PatchMapping(path = "admin/classrooms/{id}")
    public ResponseEntity<ClassroomDTO> updateTeacher(@PathVariable Long id, @RequestParam Long id_user) {
        return new ResponseEntity<>(classroomService.updateTeacher(id, id_user), HttpStatus.OK);
    }

    @DeleteMapping(path = "/admin/classrooms/{id}")
    public ResponseEntity<Classroom> deleteClassrooom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
    

