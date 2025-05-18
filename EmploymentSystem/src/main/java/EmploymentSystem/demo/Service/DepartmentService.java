/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EmploymentSystem.demo.Service;


import EmploymentSystem.demo.DTO.DepartmentDTO;
import EmploymentSystem.demo.Entity.Department;
import EmploymentSystem.demo.Repository.DepartmentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
        return convertToDTO(department);
    }
    
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        if (departmentRepository.existsByName(departmentDTO.getName())) {
            throw new EntityExistsException("Department with name " + departmentDTO.getName() + " already exists");
        }
        
        Department department = convertToEntity(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }
    
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
        
        // Check if another department with the same name exists (excluding current department)
        if (!existingDepartment.getName().equals(departmentDTO.getName()) && 
                departmentRepository.existsByName(departmentDTO.getName())) {
            throw new EntityExistsException("Department with name " + departmentDTO.getName() + " already exists");
        }
        
        departmentDTO.setId(id);
        Department department = convertToEntity(departmentDTO);
        // Preserve the relationships
        department.setEmployees(existingDepartment.getEmployees());
        
        Department updatedDepartment = departmentRepository.save(department);
        return convertToDTO(updatedDepartment);
    }
    
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
    
    // Helper methods for DTO conversion
    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setLocation(department.getLocation());
        dto.setEmployeeCount(department.getEmployees().size());
        return dto;
    }
    
    private Department convertToEntity(DepartmentDTO dto) {
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setLocation(dto.getLocation());
        return department;
    }
}
