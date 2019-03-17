package iws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class hellocontroller {
	@RequestMapping(value="/hello")
	public String send() {
		return "hello";
	}
}
