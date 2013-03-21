package dummiesmind.breadcrumb.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dummiesmind.breadcrumb.springmvc.annotations.Link;

@Controller
public class FirstController {
	
	@Link(label="First Level(First Controller)", family="FirstController" )
	@RequestMapping(value="firstLevelOfFirstController.do", method=RequestMethod.GET)
	public ModelAndView firstLevelOfFirstController(){
		return new ModelAndView("firstLevelOfFirstController");
	}
	
	@Link(label="Second Level(First Controller)", family="FirstController" )
	@RequestMapping(value="secondLevelOfFirstController.do", method=RequestMethod.GET)
	public ModelAndView secondLevelOfFirstController(){
		return new ModelAndView("secondLevelOfFirstController");
	}
	
	@Link(label="Third Level(First Controller)", family="FirstController" )
	@RequestMapping(value="thirdLevelOfFirstController.do", method=RequestMethod.GET)
	public ModelAndView thirdLevelOfFirstController(){
		return new ModelAndView("thirdLevelOfFirstController");
	}
	

}
