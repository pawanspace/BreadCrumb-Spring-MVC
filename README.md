# Spring MVC Breadcrumb

A small annotation-driven breadcrumb library for Spring MVC 7, with a runnable Spring Boot 4 sample application.

## Requirements

- Java 21+
- Maven 3.9+

## Run the sample

```bash
mvn spring-boot:run -pl breadcrumb-sample -am
```

Open `http://localhost:8080/`, then follow either trail. Direct visits to a nested page produce its full breadcrumb path without relying on session state.

## Use the library

Import `BreadCrumbConfiguration` and annotate mapped controller methods:

```java
@Link(id = "details", label = "Details", family = "account", parent = "overview")
@GetMapping("/accounts/{id}/details")
String details() {
    return "details";
}
```

The interceptor adds an immutable `breadcrumbs` list to the MVC model. Each `BreadCrumbLink` contains `id`, `label`, `url`, and `currentPage` fields.

`id` is optional for backwards compatibility; when omitted, `label` is used as the identifier. New applications should always use stable IDs.
