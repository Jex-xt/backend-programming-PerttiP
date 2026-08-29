package backend.demo1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @RequestMapping("/main")
    @ResponseBody
    public String returnGreeting() {
        return "Eka SB Sovellukseni";
    }
    
    @RequestMapping("sayHello")
    @ResponseBody
    public String returnGreetingName(@RequestParam (name = "nimesi") String etunimi) {
        return "Hei " + etunimi;
    }

    @RequestMapping("sayHelloDefault")
    @ResponseBody
    public String returnGreetingDefault(@RequestParam (name = "nimesi", required=false, defaultValue="Muumi" ) String etunimi) {
        return "Hei " + etunimi;
    }
    @RequestMapping("sayHelloAndAge")
    @ResponseBody
    public String returnGreetingAge(@RequestParam (name = "nimesi", required=false, defaultValue="Muumi" ) String etunimi, @RequestParam int age) {
        return "Hei " + etunimi + ", " + age + " vuotta";
    }    
}
