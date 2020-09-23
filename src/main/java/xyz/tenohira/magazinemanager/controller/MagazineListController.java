package xyz.tenohira.magazinemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import xyz.tenohira.magazinemanager.domain.model.Magazine;
import xyz.tenohira.magazinemanager.domain.form.MagazineListForm;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;
import xyz.tenohira.magazinemanager.exception.MagazineNotExistException;

@Controller
public class MagazineListController {

	@Autowired
	MagazineService service;
	
	@GetMapping({"/", "/list"})
	public String load(Model model) {
		
		// レコードリスト取得
		List<Magazine> list = service.getList();
		
		// フォームに詰め替え
		MagazineListForm form = new MagazineListForm();
		for (Magazine magazine : list) {
			form.addList(magazine);
		}
		
		model.addAttribute("magazineListForm", form);
		
		return "magazineList";
	}
	
	
	@PostMapping("/list/delete/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String delete(Model model, @PathVariable("id") int magazineId) throws Exception {
		
		System.out.println("magazineId:" + magazineId);
		
		// レコードが存在するか
		if (!service.isExistById(magazineId)) {
			throw new MagazineNotExistException();
		}
		
		// 削除処理実行
		boolean result = service.delete(magazineId);
		
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
