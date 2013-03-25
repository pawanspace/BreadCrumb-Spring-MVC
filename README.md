This is a very simple implementation of using breadcrumb in Spring MVC. You should be using Spring 3.1.X or above. 
For usage please look into the sample war file. Which contains everything you need to do.

Still I will give some description:<br/>
```java
@Link(label="Sample Link", family="controllerFamily", parent="");                                                                  
@RequestMapping(value = "sample.do", method=RequestMethod.GET);
public ModelAndView sampleMethod(HttpSession session){...}
```


Link is the annotation which will generate breadcrumbs for you at interceptor level. 

<b>label: </b> attribute is the label you want to display for the Link<br/>
<b>family:</b> attribute is to define the group in which this link will fall.<br/>
<b>parent:</b> attribute defines the parent of current page 

