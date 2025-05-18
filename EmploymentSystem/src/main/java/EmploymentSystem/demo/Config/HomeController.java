
package EmploymentSystem.demo.Config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/swagger")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }
    @GetMapping("/dashboard")
    public String dashboard() {
        return "DashBoard"; // no .html extension
    }

}
