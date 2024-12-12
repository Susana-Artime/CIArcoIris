package dev.susana.ciArcoIris.guardians;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api")
public class GuardianController {

    @Autowired
    private GuardianService guardianService;

    @PostMapping (path = "/admin/guardians")
    public ResponseEntity<GuardianDTO> createGuardian(@RequestBody GuardianDTO guardianDTO) {
        GuardianDTO savedGuardian = guardianService.createGuardian(guardianDTO);
        return new ResponseEntity<>(savedGuardian, HttpStatus.CREATED);
    }

    @GetMapping (path = "admin/guardians/{id}")
    public ResponseEntity<GuardianDTO> getGuardianById(@PathVariable Long id) {
        GuardianDTO guardianDTO = guardianService.getGuardianById(id);
        return new ResponseEntity<>(guardianDTO, HttpStatus.OK);
    }

    @GetMapping (path = "admin/guardians")
    public ResponseEntity<List<GuardianDTO>> getAllGuardians() {
        List<GuardianDTO> guardians = guardianService.getAllGuardians();
        return new ResponseEntity<>(guardians, HttpStatus.OK);
    }


    @GetMapping (path = "admin/guardians/child/{childId}")
    public ResponseEntity<List<GuardianDTO>> getGuardiansByChildId(@PathVariable Long childId) {
        List<GuardianDTO> guardians = guardianService.getGuardiansByChildId(childId);
        return new ResponseEntity<>(guardians, HttpStatus.OK);
    }

    
    @PutMapping (path = "admin/guardians/{id}")
    public ResponseEntity<GuardianDTO> updateGuardian(@PathVariable Long id, @RequestBody GuardianDTO guardianDTO) {
        GuardianDTO updatedGuardian = guardianService.updateGuardian(id, guardianDTO);
        return new ResponseEntity<>(updatedGuardian, HttpStatus.OK);
    }

    @DeleteMapping (path = "admin/guardians/{id}")
    public ResponseEntity<Void> deleteGuardian(@PathVariable Long id) {
        guardianService.deleteGuardian(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

