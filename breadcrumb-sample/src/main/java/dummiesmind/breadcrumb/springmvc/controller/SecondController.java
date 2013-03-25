package dummiesmind.breadcrumb.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dummiesmind.breadcrumb.springmvc.annotations.Link;

@Controller
public class SecondController {
	
	
	@Link(label="First Level(Second Controller)", family="SecondController", parent = "" )
	@RequestMapping(value="firstLevelOfSecondController.do", method=RequestMethod.GET)
	public ModelAndView firstLevelOfSecondController(){
		return new ModelAndView("firstLevelOfSecondController");
	}
	
	@Link(label="Second Level(Second Controller)", family="SecondController", parent = "First Level(Second Controller)" )
	@RequestMapping(value="secondLevelOfSecondController.do", method=RequestMethod.GET)
	public ModelAndView secondLevelOfSecondController(){
		return new ModelAndView("secondLevelOfSecondController");
	}
	
	@Link(label="Third Level(Second Controller)", family="SecondController", parent = "Second Level(Second Controller)" )
	@RequestMapping(value="thirdLevelOfSecondController.do", method=RequestMethod.GET)
	public ModelAndView thirdLevelOfSecondController(){
		return new ModelAndView("thirdLevelOfSecondController");
	}
	


}
