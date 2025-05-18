
package EmploymentSystem.demo.Controller;


import EmploymentSystem.demo.DTO.DepartmentDTO;
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
@RequestMapping("/departments")
@RequiredArgsConstructor
@Tag(name = "Department Controller", description = "Operations related to departments")
public class DepartmentController {
    
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    
    @GetMapping
    @Operation(summary = "List all departments", description = "Returns a page with all departments")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "department/list";
    }
    
    @GetMapping("/create")
    @Operation(summary = "Show department creation form", description = "Returns form for creating a new department")
    public String showCreateForm(Model model) {
        model.addAttribute("department", new DepartmentDTO());
        return "department/create";
    }
    
    @PostMapping("/create")
    @Operation(summary = "Create a new department", description = "Creates a new department and redirects to the list")
    public String createDepartment(@Valid @ModelAttribute("department") DepartmentDTO departmentDTO,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "department/create";
        }
        
        departmentService.createDepartment(departmentDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Department created successfully!");
        return "redirect:/departments";
    }
    
    @GetMapping("/{id}/edit")
    @Operation(summary = "Show department edit form", description = "Returns form for editing an existing department")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "department/edit";
    }
    
    @PostMapping("/{id}/edit")
    @Operation(summary = "Update a department", description = "Updates an existing department and redirects to the list")
    public String updateDepartment(@PathVariable Long id,
                                  @Valid @ModelAttribute("department") DepartmentDTO departmentDTO,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "department/edit";
        }
        
        departmentService.updateDepartment(id, departmentDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Department updated successfully!");
        return "redirect:/departments";
    }
    
    @GetMapping("/{id}/details")
    @Operation(summary = "Show department details", description = "Returns a page with department details and its employees")
    public String showDepartmentDetails(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.getDepartmentById(id));
        model.addAttribute("employees", employeeService.getEmployeesByDepartmentId(id));
        return "department/details";
    }
    
    @GetMapping("/{id}/delete")
    @Operation(summary = "Delete a department", description = "Deletes a department and redirects to the list")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        departmentService.deleteDepartment(id);
        redirectAttributes.addFlashAttribute("successMessage", "Department deleted successfully!");
        return "redirect:/departments";
    }
}
