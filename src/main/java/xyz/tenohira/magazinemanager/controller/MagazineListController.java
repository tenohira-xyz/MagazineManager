package xyz.tenohira.magazinemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MagazineListController {

	@GetMapping({"/", "/list"})
	public String getList(Model model) {
		return "magazineList";
	}
	
	
}
