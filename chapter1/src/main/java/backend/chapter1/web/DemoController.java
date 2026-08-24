package backend.chapter1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @RequestMapping("/index")
    @ResponseBody
    public String returnGreetingIndex() {
        return "This is the main page";
    }

    @RequestMapping("/contact")
    @ResponseBody
    public String returnGreetingContact() {
        return "This is the contact page";
    }
}
