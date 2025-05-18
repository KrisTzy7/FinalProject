/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EmploymentSystem.demo.Service;


import EmploymentSystem.demo.DTO.EmployeeDTO;
import EmploymentSystem.demo.Entity.Department;
import EmploymentSystem.demo.Entity.Employee;
import EmploymentSystem.demo.Repository.DepartmentRepository;
import EmploymentSystem.demo.Repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        return convertToDTO(employee);
    }
    
    public List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<EmployeeDTO> searchEmployees(String keyword) {
        return employeeRepository.search(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }
    
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        
        employeeDTO.setId(id);
        Employee employee = convertToEntity(employeeDTO);
        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToDTO(updatedEmployee);
    }
    
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
    
    // Helper methods for DTO conversion
    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setPosition(employee.getPosition());
        dto.setOffice(employee.getOffice());
        dto.setStartDate(employee.getStartDate());
        dto.setSalary(employee.getSalary());
        dto.setAge(employee.getAge());
        
        Department department = employee.getDepartment();
        if (department != null) {
            dto.setDepartmentId(department.getId());
            dto.setDepartmentName(department.getName());
        }
        
        return dto;
    }
    
    private Employee convertToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setPosition(dto.getPosition());
        employee.setOffice(dto.getOffice());
        employee.setStartDate(dto.getStartDate());
        employee.setSalary(dto.getSalary());
        employee.setAge(dto.getAge());
        
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + dto.getDepartmentId()));
            employee.setDepartment(department);
        }
        
        return employee;
    }
}