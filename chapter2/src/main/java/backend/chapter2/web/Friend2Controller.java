package backend.chapter2.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import backend.chapter2.domain.Friend;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class Friend2Controller {

    private List<Friend> friends = new ArrayList<>();

    public Friend2Controller() {
    friends.add(new Friend("Minna", "Minnanen"));
    friends.add(new Friend("Tanja", "Tanjanen"));
    friends.add(new Friend("Jukka", "Jukkanen"));
    }

    @GetMapping("/friend2")
    public String friendList(Model model) {
        model.addAttribute("friends", friends);
        model.addAttribute("friend", new Friend()); // Tyhjä Friend-luokan olio Modeliin
        return "friend2";
    }
    
    @PostMapping("/friend2")
    public String addFriend(@ModelAttribute Friend friend) { // Ei Model model, koska uusi lisätään suoraan friends listaan
        friends.add(friend);       
        return "redirect:/friend2"; //html näkymän päivitys GET-metodin kautta. Lisätty Friend ilmestyy listaa heti.
    }
    
}
