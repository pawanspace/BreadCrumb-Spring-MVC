package dummiesmind.breadcrumb.springmvc.controller;

import dummiesmind.breadcrumb.springmvc.annotations.Link;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecondController {

    @Link(id = "first", label = "First level", family = "second", parent = "")
    @GetMapping("/second")
    public String firstLevel() {
        return "second-first";
    }

    @Link(id = "child", label = "Second level", family = "second", parent = "first")
    @GetMapping("/second/child")
    public String secondLevel() {
        return "second-child";
    }

    @Link(id = "third", label = "Third level", family = "second", parent = "child")
    @GetMapping("/second/child/third")
    public String thirdLevel() {
        return "second-third";
    }
}
