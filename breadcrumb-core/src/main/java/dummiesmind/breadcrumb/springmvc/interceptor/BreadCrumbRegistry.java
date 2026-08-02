package dummiesmind.breadcrumb.springmvc.interceptor;

import dummiesmind.breadcrumb.springmvc.annotations.Link;
import dummiesmind.breadcrumb.springmvc.breadcrumb.BreadCrumbLink;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class BreadCrumbRegistry {

    private final Map<String, Definition> definitions = new HashMap<>();
    private final ObjectProvider<RequestMappingHandlerMapping> handlerMappingProvider;
    private boolean initialized;

    public BreadCrumbRegistry(ObjectProvider<RequestMappingHandlerMapping> handlerMappingProvider) {
        this.handlerMappingProvider = handlerMappingProvider;
    }

    public List<BreadCrumbLink> resolve(Link currentLink, String contextPath) {
        initialize();
        List<Definition> trail = new ArrayList<>();
        Definition current = definitions.get(key(currentLink.family(), id(currentLink)));
        if (current == null) {
            return List.of();
        }

        while (current != null) {
            trail.addFirst(current);
            String parent = current.link.parent();
            current = parent.isBlank() ? null : definitions.get(key(current.link.family(), parent));
        }

        List<BreadCrumbLink> result = new ArrayList<>(trail.size());
        for (int index = 0; index < trail.size(); index++) {
            Definition definition = trail.get(index);
            result.add(new BreadCrumbLink(id(definition.link), definition.link.label(), contextPath + definition.path,
                    index == trail.size() - 1));
        }
        return List.copyOf(result);
    }

    private synchronized void initialize() {
        if (initialized) {
            return;
        }
        RequestMappingHandlerMapping handlerMapping = handlerMappingProvider.getObject();
        handlerMapping.getHandlerMethods().forEach((mapping, handlerMethod) -> {
            Link link = handlerMethod.getMethodAnnotation(Link.class);
            if (link != null && !mapping.getPatternValues().isEmpty()) {
                definitions.put(key(link.family(), id(link)), new Definition(link, mapping.getPatternValues().iterator().next()));
            }
        });
        initialized = true;
    }

    private static String id(Link link) {
        return link.id().isBlank() ? link.label() : link.id();
    }

    private static String key(String family, String id) {
        return family + ':' + id;
    }

    private record Definition(Link link, String path) {
    }
}
