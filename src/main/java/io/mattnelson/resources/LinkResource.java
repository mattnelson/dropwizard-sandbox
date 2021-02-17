package io.mattnelson.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("link")
public class LinkResource {

    @GET
    public String link(@Context UriInfo uriInfo) {
        return uriInfo.getRequestUri().toString();
    }
}
