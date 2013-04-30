Spring MVC breadcrmb:

This is a blog entry about a breadcrumb sample I built for Spring MVC applications. I was trying to see some kind of sultion for my project but I was not
able to find some great idea.

I wanted to build something simple and easy to use. I was sure there is something in Spring that I can use. I started thinking about filters in servlet. It was simple just intercept the call and save a history of the call in some hashmap with key as page name or something like that. 
When user clicks on the next page do the same thing for the next page call and print the link of from history hashmap on the result page. So it was simple algorithm :

User go to search page 

Filter will make a hashmap entry in the breadcrumb hashmap with key/value {search: SEARCH_PAGE_URL}

User type something and click search 

Filter will make another hashmap entry in the hashmap with key/value {searchResults: SEARCH_RESULT_URL}

Result page is displayed to user, also entries from breadcrumb hashmap will be displayed like this:

  											Search > Search Results

Where search results is just plain text and search is a link to go back.

It was simple until I realized what if there are some links I do not want to add as a breadcrumb. What about ajax calls. What about context of pages. For example what if I want to move from search users to search accounts

Imagine I am on Search user page and I click on Search Accounts page and I am able to see Search User as BreadCrumb. Its not right!! 


For me breadcrumb is not history!! Its a flow!! 

I was trying to make it as simple as possible but it was not working. Then suddenly my mind said something like intercept and I thought of Interceptors in Spring framework. I remember talking about interceptors in Interviews and people used to ask me applications of interceptor and difference between Interceptor and Filters. Well all I can say is Interceptors are very very powerful!!


Spring Interceptors are exactly like filters but provides you with much more information. 

Let's see some code now to understand how Interceptors helped me to build a simple BreadCrumb framework that I can use in any Spring MVC application.


My idea was to build something like this:

  @Link(label="Sample Link", family="controllerFamily", parent="");                                                                  
  @RequestMapping(value = "sample.do", method=RequestMethod.GET);
  public ModelAndView sampleMethod(HttpSession session){...}

As you can see method needs to have an annotation Link which must have a 

(1) Label: This is what will be displayed in the BreadCrumb, You can also give some key here and fetch value from the properties file using fmt tag in 			   JSP.

(2) Family: This repersents the context. So as I discussed earlier. In case I move from Search Users to Search Accounts I would like to have generated BreadCrumb in correct context.

(3) Parent: This is for relationship between pages. we will see usage of this attribute once we see the example.


Thats all you have to do at method level and you are all set. You have breadcrumb in your session object and you can use it any where you want. 


Main pillars:

(1) Interceptor: As you can see its the heard of breadcrumb sample. First of all it has to extend HandlerInterceptorAdapter. The you can override methods like postHandle or preHandle. We want to intercept request calls so we have implemented preHandle method.

As you can see that we have a reference (handler) to the method that is assigned to handle this request. We get the declared annotations from that 
method. The we are trying to remove current Breadcrumb. You can obviously do better than this. But for now it doesn't matter. 

Iterate through declared annotations and  see if this class handler qualifies for the Breadcrumb. 

























