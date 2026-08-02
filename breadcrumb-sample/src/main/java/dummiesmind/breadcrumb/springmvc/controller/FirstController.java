package dummiesmind.breadcrumb.springmvc.controller;

import dummiesmind.breadcrumb.springmvc.annotations.Link;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @Link(id = "first", label = "First level", family = "first", parent = "")
    @GetMapping("/first")
    public String firstLevel() {
        return "first";
    }

    @Link(id = "second", label = "Second level", family = "first", parent = "first")
    @GetMapping("/first/second")
    public String secondLevel() {
        return "second";
    }

    @Link(id = "alternative", label = "Alternative second level", family = "first", parent = "first")
    @GetMapping("/first/alternative")
    public String alternativeSecondLevel() {
        return "alternative";
    }

    @Link(id = "third", label = "Third level", family = "first", parent = "second")
    @GetMapping("/first/second/third")
    public String thirdLevel() {
        return "third";
    }
}
