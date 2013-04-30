package dummiesmind.breadcrumb.springmvc.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import dummiesmind.breadcrumb.springmvc.annotations.Link;
import dummiesmind.breadcrumb.springmvc.breadcrumb.BreadCrumbLink;



public class BreadCrumbInterceptor extends HandlerInterceptorAdapter {

	private static final String BREAD_CRUMB_LINKS = "breadCrumb";


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// Get declared annotations
		Annotation[] declaredAnnotations = getDeclaredAnnotationsForHandler(handler);
		HttpSession session = request.getSession();
		/*
		 * Remove reference to current BreadCrumb object from session. You can obviously do better than this. But for now it doesn't matter.
		 */
		emptyCurrentBreadCrumb(session);

		//Loop through annotations to check if this handler qualifies for BreadCrumb or not.
		for (Annotation annotation : declaredAnnotations) {
			if(annotation.annotationType().equals(Link.class)){
				//Process annotation in case it qualifies
				processAnnotation(request, session, annotation);
			}
		}

		return true;
	}


	private void emptyCurrentBreadCrumb(HttpSession session) {
		//Set empty object
		session.setAttribute("currentBreadCrumb", new LinkedList<BreadCrumbLink>());
	}


	/*
	 * Process Link annotation
	 */
	private void processAnnotation(HttpServletRequest request, HttpSession session, Annotation annotation) {

		Link link = (Link) annotation; //get annotation object
		String family = link.family();  //get family (basically context of the request. Make sure this has nothing to do with PageContext)

		Map<String, LinkedHashMap<String, BreadCrumbLink>> breadCrumbLinksByFamily = getBreadCrumbLinksFromSession(session); // Get BreadCrumbLink Objects by family


		// If this is the first request and no breadCrumbLinks Exists make a new one and add to session.
		if(breadCrumbLinksByFamily == null){
			breadCrumbLinksByFamily = new HashMap<String, LinkedHashMap<String,BreadCrumbLink>>();
			session.setAttribute(BREAD_CRUMB_LINKS, breadCrumbLinksByFamily);
		}


		// Get links by family
		LinkedHashMap<String, BreadCrumbLink> familyMap = breadCrumbLinksByFamily.get(family);

		//If family does not have any links in the current history. make a new one and add to the map.
		if(familyMap == null){
			familyMap = new LinkedHashMap<String, BreadCrumbLink>();
			breadCrumbLinksByFamily.put(family, familyMap);
		}


		//Create a breadCrumbLink. This contains all the information to display one link in the breadcrumb (contains url, label, next, previous links etc)
		BreadCrumbLink breadCrumbLink = getBreadCrumbLink(request, link, familyMap);
		//Create a new breadCrumb for display 
		LinkedList<BreadCrumbLink> currentBreadCrumb = new LinkedList<BreadCrumbLink>();
		//Generate breadcrumb for display recursively
		generateBreadCrumbsRecursively(breadCrumbLink,currentBreadCrumb);

		//Set breadcrumb in session
		session.setAttribute("currentBreadCrumb", currentBreadCrumb);
	}


	//Generate BreadCrumbLink based on attributes in Link annotationa nd request Object.
	private BreadCrumbLink getBreadCrumbLink(HttpServletRequest request, Link link,
			LinkedHashMap<String, BreadCrumbLink> familyMap) {
		BreadCrumbLink breadCrumbLink;
		BreadCrumbLink breadCrumbObject = familyMap.get(link.label());
		resetBreadCrumbs(familyMap);
		if(breadCrumbObject != null){
			breadCrumbObject.setCurrentPage(true);
			breadCrumbLink = breadCrumbObject;
		}else{
			breadCrumbLink = new BreadCrumbLink(link.family(), link.label(), true, link.parent());
			String fullURL = request.getRequestURL().append("?").append(request.getQueryString()).toString();
			breadCrumbLink.setUrl(fullURL);
			createRelationships(familyMap, breadCrumbLink);
			familyMap.put(link.label(), breadCrumbLink);
		}
		return breadCrumbLink;
	}


	@SuppressWarnings("unchecked")
	private Map<String, LinkedHashMap<String, BreadCrumbLink>> getBreadCrumbLinksFromSession(HttpSession session) {
		Map<String, LinkedHashMap<String, BreadCrumbLink>> breadCrumb = (Map<String, LinkedHashMap<String, BreadCrumbLink>>)session.getAttribute(BREAD_CRUMB);
		return breadCrumb;
	}

	private Annotation[] getDeclaredAnnotationsForHandler(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();
		Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
		return declaredAnnotations;
	}

	private void resetBreadCrumbs(LinkedHashMap<String, BreadCrumbLink> familyMap) {
		for(BreadCrumbLink breadCrumbLink : familyMap.values()){
			breadCrumbLink.setCurrentPage(false);
		}
	}

	private void generateBreadCrumbsRecursively(BreadCrumbLink link , LinkedList<BreadCrumbLink> breadCrumbLinks){
		if(link.getPrevious() != null){
			generateBreadCrumbsRecursively(link.getPrevious(), breadCrumbLinks);
		}
		 breadCrumbLinks.add(link);
	}


	private void createRelationships(LinkedHashMap<String, BreadCrumbLink> familyMap , BreadCrumbLink newLink){
		Collection<BreadCrumbLink> values = familyMap.values();
		for (BreadCrumbLink breadCrumbLink : values) {
			if(breadCrumbLink.getLabel().equalsIgnoreCase(newLink.getParentKey())){
					breadCrumbLink.addNext(newLink);
					newLink.setPrevious(breadCrumbLink);
					newLink.setParent(breadCrumbLink);
			}
		}

	}

}
