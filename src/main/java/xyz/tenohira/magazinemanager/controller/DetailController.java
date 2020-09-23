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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import xyz.tenohira.magazinemanager.domain.form.DetailForm;
import xyz.tenohira.magazinemanager.domain.model.Magazine;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;
import xyz.tenohira.magazinemanager.exception.MagazineNotExistException;

@Controller
public class DetailController {

	@Autowired
	MagazineService service;
	
	@GetMapping("/detail/{id}")
	public String load(@ModelAttribute DetailForm form,
			Model model,
			@PathVariable("id") int magazineId) throws Exception{
		
		// レコードが存在するか
		if (!service.isExistById(magazineId)) {
			throw new MagazineNotExistException();
		}
		
		// 雑誌情報取得
		Magazine magazine = service.getData(magazineId);		
		form.setName(magazine.getName());
		form.setNumber(magazine.getNumber());
		form.setPublisher(magazine.getPublisher());
		form.setIssueDate(magazine.getIssueDate());
		form.setMagazineId(magazineId);
		
		return "detail";
	}
	
	@PostMapping("/detail/{id}/update")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String update(@ModelAttribute @Validated DetailForm form,
			BindingResult bindingResult,
			Model model,
			@PathVariable("id") int magazineId) throws Exception {
		
		// レコードが存在するか
		if (!service.isExistById(magazineId)) {
			throw new MagazineNotExistException();
		}
		
		// 入力チェック
		if (bindingResult.hasErrors()) {
			return "detail";
		}
		
		// 更新処理
		Magazine magazine = new Magazine();
		magazine.setMagazineId(form.getMagazineId());
		magazine.setName(form.getName());
		magazine.setNumber(form.getNumber());
		magazine.setPublisher(form.getPublisher());
		magazine.setIssueDate(form.getIssueDate());
		boolean result = service.update(magazine);
		
		// 実行結果判定
		return result ? "redirect:/list" : "error";
	}
	
	@ExceptionHandler(MagazineNotExistException.class)
	public String magazineNotExistExceptionHandler(MagazineNotExistException e, Model model) {
		
		// メッセージ定義
		model.addAttribute("error", "エラー");
		model.addAttribute("message", "指定した雑誌が存在しません。");
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
	}
}
