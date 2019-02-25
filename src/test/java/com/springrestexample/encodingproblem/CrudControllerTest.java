package com.springrestexample.encodingproblem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.snippet.FullDocumentationSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CrudControllerTest {

    protected static final String REST_ENDPOINT = "http://localhost:8080";

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    @InjectMocks
    private CrudController testSubject;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = initMockMvc(testSubject);
    }

    private MockMvcRestDocumentationConfigurer getDocumentationConfiguration() {
        MockMvcRestDocumentationConfigurer mockMvcRestDocumentationConfigurer = MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation);
        mockMvcRestDocumentationConfigurer.snippets().withDefaults().withEncoding("UTF-8");
        return mockMvcRestDocumentationConfigurer;
    }

    protected MockMvc initMockMvc(Object... controllers) {
        return MockMvcBuilders.standaloneSetup(controllers).apply(getDocumentationConfiguration()).build();
    }

    @Test
    public void readTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/crud").contentType(MediaType.APPLICATION_JSON));
        perform.andExpect(status().isOk())
                .andDo(document("read", new FullDocumentationSnippet(REST_ENDPOINT)))//
                .andReturn();
    }

}