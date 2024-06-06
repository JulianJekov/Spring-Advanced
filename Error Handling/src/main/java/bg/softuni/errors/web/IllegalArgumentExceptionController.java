package bg.softuni.errors.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IllegalArgumentExceptionController {
    @GetMapping("/iae")
    public String testIae() {
        throw new IllegalArgumentException("iae");
    }
}
