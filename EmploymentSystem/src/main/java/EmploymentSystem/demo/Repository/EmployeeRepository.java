
package EmploymentSystem.demo.Repository;


import EmploymentSystem.demo.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findByDepartmentId(Long departmentId);
    
    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.position) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.office) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> search(@Param("keyword") String keyword);
}