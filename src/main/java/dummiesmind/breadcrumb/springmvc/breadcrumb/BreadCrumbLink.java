package dummiesmind.breadcrumb.springmvc.breadcrumb;

public class BreadCrumbLink {

	private BreadCrumbLink previous;
	private BreadCrumbLink next;
	private String url;
	private String family;
	private String label;
	boolean currentPage;

	public BreadCrumbLink(String family, String label, boolean currentPage) {
		this.family = family;
		this.label = label;
		this.currentPage = currentPage;
	}

	public BreadCrumbLink getPrevious() {
		return previous;
	}

	public void setPrevious(BreadCrumbLink previous) {
		this.previous = previous;
	}

	public BreadCrumbLink getNext() {
		return next;
	}

	public void setNext(BreadCrumbLink next) {
		this.next = next;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(boolean currentPage) {
		this.currentPage = currentPage;
	}
	
	

}
