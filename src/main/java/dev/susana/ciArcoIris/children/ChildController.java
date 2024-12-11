package dev.susana.ciArcoIris.children;

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

@RestController
@RequestMapping("/api")
public class ChildController {

    @Autowired
    private ChildService childService;

    @PostMapping(path = "/admin/children")
    public ResponseEntity<ChildDTO> addChild(@RequestBody ChildDTO childDTO) {
        return new ResponseEntity<>(childService.createChild(childDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/admin/children")
    public ResponseEntity<List<ChildDTO>> getAllChildren() {
        return new ResponseEntity<>(childService.getAllChildren(), HttpStatus.OK);
    }

    @GetMapping(path = "/admin/children/{id}")
    public ResponseEntity<ChildDTO> getChildById(@PathVariable Long id) {
        return new ResponseEntity<>(childService.getChildById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/admin/children/{id}")
    public ResponseEntity<ChildDTO> updateChild(@PathVariable Long id, @RequestBody ChildDTO childDTO) {
        return new ResponseEntity<>(childService.updateChild(childDTO, id), HttpStatus.OK);
    }

    @PatchMapping(path = "/admin/children/{id}")
    public ResponseEntity<ChildDTO> assingGuardianToChild(@PathVariable Long id, @RequestParam Long idGuardian) {
        return new ResponseEntity<>(childService.assignGuardian(id, idGuardian), HttpStatus.OK);
    }

    @PatchMapping(path = "/admin/children/{id}/assign-classroom")
    public ResponseEntity<ChildDTO> assignClassroomToChild(@PathVariable Long id, @RequestParam Long idClassroom) {
    ChildDTO childDTO = childService.assignClassroom(id, idClassroom);
        return new ResponseEntity<>(childDTO, HttpStatus.OK);
}

    @DeleteMapping(path = "/admin/children/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        childService.deleteChild(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}