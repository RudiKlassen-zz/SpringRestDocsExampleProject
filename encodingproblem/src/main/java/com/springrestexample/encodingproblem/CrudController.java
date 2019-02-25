package com.springrestexample.encodingproblem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/crud")
public class CrudController {

    @GetMapping
    public List<ResponeObject> read() {
        return new ArrayList<>();
    }

    class ResponeObject {
        //nothing to do here
    }

}
