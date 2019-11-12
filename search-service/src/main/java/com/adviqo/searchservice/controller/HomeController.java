package com.adviqo.searchservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Home controller which redirects to swagger ui.
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@Controller
@ApiIgnore
public class HomeController {

    @RequestMapping("/")
    public String home() {
	return "redirect:swagger-ui.html";
    }

}
