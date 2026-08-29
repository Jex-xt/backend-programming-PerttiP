package backend.chapter2.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String helloAge(@RequestParam(name = "name") String name, @RequestParam(name = "age") int age, Model model) {
        if (age > 18) {
            model.addAttribute("message", "Welcome " + name + "!");
        }
        else {
            model.addAttribute("message", "You are too young!");
        }
        return "hello";
    }
}
