package xyz.tenohira.magazinemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import xyz.tenohira.magazinemanager.domain.form.RegisterForm;
import xyz.tenohira.magazinemanager.domain.model.Magazine;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;
import xyz.tenohira.magazinemanager.exception.MagazineRegisteredException;

@Controller
public class RegisterController {

	@Autowired
	MagazineService service;
	
	@GetMapping("/register")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String load(@ModelAttribute RegisterForm form, Model model) {
		
		return "register";
	}
	
	@PostMapping("/register/create")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String register(@ModelAttribute @Validated RegisterForm form,
			BindingResult bindingResult,
			Model model) throws Exception {
		
		System.out.println("name:" + form.getName() + ", " + "number:" + form.getNumber());
		
		// 入力チェック
		if (bindingResult.hasErrors()) {
			return load(form, model);
		}
		
		// レコードが登録済かチェック
		boolean exist = service.isExistByData(form.getName(), form.getNumber());
		if (exist) {
			throw new MagazineRegisteredException();
		}
		
		// 登録処理
		Magazine magazine = new Magazine();
		magazine.setName(form.getName());
		magazine.setNumber(form.getNumber());
		boolean result = service.add(magazine);
		
		// 実行結果判定
		return result ? "redirect:/list" : "error";
	}
	
	@ExceptionHandler(MagazineRegisteredException.class)
	public String magazineRegisteredExceptionHandler(MagazineRegisteredException e, Model model) {
		
		// メッセージ定義
		model.addAttribute("error", "エラー");
		model.addAttribute("message", "指定した内容の雑誌は既に登録されています。");
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
}
