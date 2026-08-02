package dummiesmind.breadcrumb.springmvc.breadcrumb;

import java.io.Serializable;

public record BreadCrumbLink(String id, String label, String url, boolean currentPage) implements Serializable {

    private static final long serialVersionUID = 1L;
}
