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

	private static final String BREAD_CRUMB = "breadCrumb";


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	
		Annotation[] declaredAnnotations = getDeclaredAnnotationsForHandler(handler);
		HttpSession session = request.getSession();
		
		for (Annotation annotation : declaredAnnotations) {
			if(annotation.annotationType().equals(Link.class)){
				processAnnotation(request, session, annotation);
			}
		}
		
		return true;
	}


	private void processAnnotation(HttpServletRequest request, HttpSession session, Annotation annotation) {
		Link link = (Link) annotation;
		String family = link.family();
		String label = link.label();
		
		Map<String, LinkedHashMap<String, BreadCrumbLink>> breadCrumb = getBreadCrumbFromSession(session);
		
		if(breadCrumb == null){
			breadCrumb = new HashMap<String, LinkedHashMap<String,BreadCrumbLink>>();
			session.setAttribute(BREAD_CRUMB, breadCrumb);
		}

		LinkedHashMap<String, BreadCrumbLink> familyMap = breadCrumb.get(family);
		
		
		if(familyMap == null){
			familyMap = new LinkedHashMap<String, BreadCrumbLink>();
			breadCrumb.put(family, familyMap);
		}
		
		BreadCrumbLink breadCrumbLink = null;
		breadCrumbLink = getBreadCrumbLink(request, family, label, familyMap);
		LinkedList<BreadCrumbLink> currentBreadCrumb = new LinkedList<BreadCrumbLink>();
		generateBreadCrumbsRecursively(breadCrumbLink,currentBreadCrumb);
		session.setAttribute("currentBreadCrumb", currentBreadCrumb);
	}


	private BreadCrumbLink getBreadCrumbLink(HttpServletRequest request, String family, String label, LinkedHashMap<String, BreadCrumbLink> familyMap) {
		BreadCrumbLink breadCrumbLink;
		BreadCrumbLink breadCrumbObject = familyMap.get(label);
		resetBreadCrumbs(familyMap);
		if(breadCrumbObject != null){
			breadCrumbObject.setCurrentPage(true);
			breadCrumbLink = breadCrumbObject;
		}else{
			breadCrumbLink = new BreadCrumbLink(family, label, true);
			breadCrumbLink.setUrl(request.getRequestURI());
			createRelationships(familyMap, breadCrumbLink);
			familyMap.put(label, breadCrumbLink);
		}
		return breadCrumbLink;
	}


	@SuppressWarnings("unchecked")
	private Map<String, LinkedHashMap<String, BreadCrumbLink>> getBreadCrumbFromSession(HttpSession session) {
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
			breadCrumbLink.setCurrentPage(false);
			BreadCrumbLink next = breadCrumbLink.getNext();
			if(next==null){
				breadCrumbLink.setNext(newLink);
				newLink.setPrevious(breadCrumbLink);
			}
		}
		
	}

}
