/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EmploymentSystem.demo.Config;

import EmploymentSystem.demo.DTO.EmployeeDTO;
import EmploymentSystem.demo.Service.EmployeeService;
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
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "REST API for employee operations")
public class EmployeeApiController {
    
    private final EmployeeService employeeService;
    
    @GetMapping
    @Operation(summary = "Get all employees", description = "Returns a list of all employees")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Returns an employee by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved employee"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search employees", description = "Returns employees matching the search keyword")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved employees")
    public ResponseEntity<List<EmployeeDTO>> searchEmployees(@RequestParam String keyword) {
        return ResponseEntity.ok(employeeService.searchEmployees(keyword));
    }
    
    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get employees by department", description = "Returns employees belonging to a department")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartmentId(departmentId));
    }
    
    @PostMapping
    @Operation(summary = "Create employee", description = "Creates a new employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created employee"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update employee", description = "Updates an existing employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated employee"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, 
                                                      @Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Deletes an employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted employee"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
