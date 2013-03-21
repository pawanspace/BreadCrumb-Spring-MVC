package dummiesmind.breadcrumb.springmvc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomePageController {

	@RequestMapping(value="/home.do", method = RequestMethod.GET)
	public ModelAndView home(){
		return new ModelAndView("home");
	}
	
	
}
