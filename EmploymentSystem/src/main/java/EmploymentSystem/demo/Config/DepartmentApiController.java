
package EmploymentSystem.demo.Config;

import EmploymentSystem.demo.DTO.DepartmentDTO;
import EmploymentSystem.demo.Service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department API", description = "REST API for department operations")
public class DepartmentApiController {
    
    private final DepartmentService departmentService;
    
    @GetMapping
    @Operation(summary = "Get all departments", description = "Returns a list of all departments")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID", description = "Returns a department by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved department"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }
    
    @PostMapping
    @Operation(summary = "Create department", description = "Creates a new department")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created department"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Department already exists")
    })
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(departmentService.createDepartment(departmentDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update department", description = "Updates an existing department")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated department"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "409", description = "Department name conflict")
    })
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, 
                                                         @Valid @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete department", description = "Deletes a department")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted department"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
