
package EmploymentSystem.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    
    private Long id;
    
    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 50, message = "Department name must be between 2 and 50 characters")
    private String name;
    
    @NotBlank(message = "Department location is required")
    @Size(min = 2, max = 100, message = "Department location must be between 2 and 100 characters")
    private String location;
    
    private int employeeCount;
}