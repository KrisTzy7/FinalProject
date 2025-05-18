
package EmploymentSystem.demo.Controller;

import EmploymentSystem.demo.DTO.EmployeeDTO;
import EmploymentSystem.demo.Service.DepartmentService;
import EmploymentSystem.demo.Service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "Operations related to employees")
public class EmployeeController {
    
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    
    @GetMapping
    @Operation(summary = "List all employees", description = "Returns a page with all employees")
    public String listEmployees(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("employees", employeeService.searchEmployees(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("employees", employeeService.getAllEmployees());
        }
        return "employee/list";
    }
    
    @GetMapping("/create")
    @Operation(summary = "Show employee creation form", description = "Returns form for creating a new employee")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee/create";
    }
    
    @PostMapping("/create")
    @Operation(summary = "Create a new employee", description = "Creates a new employee and redirects to the list")
    public String createEmployee(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO, 
                                 BindingResult result, 
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employee/create";
        }
        
        employeeService.createEmployee(employeeDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Employee created successfully!");
        return "redirect:/employees";
    }
    
    @GetMapping("/{id}/edit")
    @Operation(summary = "Show employee edit form", description = "Returns form for editing an existing employee")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee/edit";
    }
    
    @PostMapping("/{id}/edit")
    @Operation(summary = "Update an employee", description = "Updates an existing employee and redirects to the list")
    public String updateEmployee(@PathVariable Long id,
                                @Valid @ModelAttribute("employee") EmployeeDTO employeeDTO,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employee/edit";
        }
        
        employeeService.updateEmployee(id, employeeDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully!");
        return "redirect:/employees";
    }
    
    @GetMapping("/{id}/details")
    @Operation(summary = "Show employee details", description = "Returns a page with employee details")
    public String showEmployeeDetails(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "employee/details";
    }
    
    @GetMapping("/{id}/delete")
    @Operation(summary = "Delete an employee", description = "Deletes an employee and redirects to the list")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        return "redirect:/employees";
    }
}
