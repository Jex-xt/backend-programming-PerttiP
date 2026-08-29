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
public class FriendController {

    private List<Friend> friends = new ArrayList<>();

    public FriendController() {
    friends.add(new Friend("Minna", "Minnanen"));
    friends.add(new Friend("Tanja", "Tanjanen"));
    friends.add(new Friend("Jukka", "Jukkanen"));
    }

    @GetMapping("/friend")
    public String friendList(Model model) {
        model.addAttribute("friends", friends);
        return "friend";
    }
    @GetMapping("/add")
    public String addForm(Model model) {
    model.addAttribute("friend", new Friend());
    return "add";
}
    @PostMapping("/add")
    public String addFriend(@ModelAttribute Friend friend, Model model) {
        friends.add(friend);       
        return "redirect:/friend";
    }
    

}
