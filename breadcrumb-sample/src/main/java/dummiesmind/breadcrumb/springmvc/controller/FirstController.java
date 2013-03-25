package dummiesmind.breadcrumb.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dummiesmind.breadcrumb.springmvc.annotations.Link;

@Controller
public class FirstController {
	
	@Link(label="First Level(First Controller)", family="FirstController", parent = "" )
	@RequestMapping(value="firstLevelOfFirstController.do", method=RequestMethod.GET)
	public ModelAndView firstLevelOfFirstController(){
		return new ModelAndView("firstLevelOfFirstController");
	}
	
	@Link(label="Second Level(First Controller)", family="FirstController", parent = "First Level(First Controller)" )
	@RequestMapping(value="secondLevelOfFirstController.do", method=RequestMethod.GET)
	public ModelAndView secondLevelOfFirstController(){
		return new ModelAndView("secondLevelOfFirstController");
	}
	
	@Link(label="Second Level-2(First Controller)", family="FirstController", parent = "First Level(First Controller)" )
	@RequestMapping(value="secondLevel2OfFirstController.do", method=RequestMethod.GET)
	public ModelAndView secondLevel2OfFirstController(){
		return new ModelAndView("secondLevel2OfFirstController");
	}
	
	
	@Link(label="Third Level(First Controller)", family="FirstController", parent = "Second Level(First Controller)" )
	@RequestMapping(value="thirdLevelOfFirstController.do", method=RequestMethod.GET)
	public ModelAndView thirdLevelOfFirstController(){
		return new ModelAndView("thirdLevelOfFirstController");
	}
	

}
