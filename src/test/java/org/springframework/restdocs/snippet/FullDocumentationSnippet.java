package org.springframework.restdocs.snippet;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.restdocs.generate.RestDocumentationGenerator;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.request.AbstractParametersSnippet;
import org.springframework.util.Assert;

/**
 * Snippet, welches die erzeugten Snippets zusammenfasst und eine vollst?ndige Dokumentation zur?ckliefert.
 */
public class FullDocumentationSnippet extends AbstractParametersSnippet {

    private final String restEndpoint;
    private String showRequestFields = "!showRequestFields";
    private String showRequestParameters = "!showRequestParameters";
    private String showResponseFields = "!showResponseFields";
    private String showHttpResponse = "!showHttpResponse";
    private String showRequestParts = "!showRequestParts";
    private String requestPartName = "";
    private String showResponseHeader = "!showResponseHeader";
    private String showPathParameters = "!showPathParameters";
    private String showSpecificErrorMessages = "!showSpecificErrorMessages";
    private String customErrorDescription = "";
    private String queryParameters = "";

    /**
     * @param restEndpoint
     *            REST-Enpoint als String
     */
    public FullDocumentationSnippet(String restEndpoint) {
        super("fullDocumentation", new ArrayList<>(), null, true);
        this.restEndpoint = restEndpoint;
    }

    public FullDocumentationSnippet withRequestFields() {
        this.showRequestFields = "showRequestFields";
        return this;
    }

    public FullDocumentationSnippet withRequestParameters() {
        this.showRequestParameters = "showRequestParameters";
        return this;
    }

    public FullDocumentationSnippet withResponseFields() {
        this.showResponseFields = "showResponseFields";
        return this;
    }

    public FullDocumentationSnippet withoutHttpResponse() {
        this.showHttpResponse = "!showHttpResponse";
        return this;
    }

    public FullDocumentationSnippet withMultipartRequestParts(String requestPartName) {
        this.showRequestParts = "showRequestParts";
        this.requestPartName = requestPartName;
        return this;
    }

    public FullDocumentationSnippet withResponseHeader() {
        this.showResponseHeader = "showResponseHeader";
        return this;
    }

    public FullDocumentationSnippet withPathParameters() {
        this.showPathParameters = "showPathParameters";
        return this;
    }

    public FullDocumentationSnippet withExampleRequestQueryParameters(String queryParameters) {
        this.queryParameters = queryParameters;
        return this;
    }

    public FullDocumentationSnippet withoutSpecificErrors() {
        this.showSpecificErrorMessages = "!showSpecificErrorMessages";
        return this;
    }

    public FullDocumentationSnippet withoutSpecificErrors(String customErrorDescription) {
        this.showSpecificErrorMessages = "!showSpecificErrorMessages";
        this.customErrorDescription = customErrorDescription;
        return this;
    }

    @Override
    protected Map<String, Object> createModel(Operation operation) {
        Map<String, Object> model = super.createModel(operation);
        model.put("method", operation.getRequest().getMethod());
        model.put("queryParameters", queryParameters);
        model.put("folder", operation.getName());
        model.put("showResponseFields", showResponseFields);
        model.put("showHttpResponse", showHttpResponse);
        model.put("showRequestFields", showRequestFields);
        model.put("showRequestParameters", showRequestParameters);
        model.put("showRequestParts", showRequestParts);
        model.put("requestPartName", requestPartName);
        model.put("showResponseHeader", showResponseHeader);
        model.put("showPathParameters", showPathParameters);
        model.put("showSpecificErrorMessages", showSpecificErrorMessages);
        model.put("customErrorDescription", customErrorDescription);
        return model;
    }


    @Override
    protected Set<String> extractActualParameters(Operation operation) {
        return new HashSet<>();
    }

    @Override
    protected void verificationFailed(Set<String> undocumentedParameters, Set<String> missingParameters) {
        // nothing to document - do nothing
    }

    private String removeQueryStringIfPresent(String urlTemplate) {
        int index = urlTemplate.indexOf('?');
        if (index == -1) {
            return urlTemplate;
        }
        return urlTemplate.substring(0, index);
    }

    private String extractUrlTemplate(Operation operation) {
        String urlTemplate = (String) operation.getAttributes().get(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE);
        Assert.notNull(urlTemplate, "urlTemplate not found. If you are using MockMvc did " + "you use RestDocumentationRequestBuilders to build the request?");
        return urlTemplate;
    }

    private String extractProtocol(Operation operation) {
        return operation.getRequest().getUri().getScheme();
    }

    private int extractPort(Operation operation) {
        URI urlTemplate = operation.getRequest().getUri();
        return urlTemplate.getPort();
    }

}
