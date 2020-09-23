package xyz.tenohira.magazinemanager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import xyz.tenohira.magazinemanager.domain.form.IndexForm;
import xyz.tenohira.magazinemanager.domain.model.Article;
import xyz.tenohira.magazinemanager.domain.model.Keyword;
import xyz.tenohira.magazinemanager.domain.service.KeywordService;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;
import xyz.tenohira.magazinemanager.exception.MagazineNotExistException;

@Controller
public class IndexListController {

	@Autowired
	MagazineService magazineService;
	
	@Autowired
	KeywordService keywordService;
	
	@GetMapping("/index/{id}")
	public String load(@ModelAttribute IndexForm form,
			Model model,
			@PathVariable("id") int magazineId) throws Exception {
		
		// 雑誌レコードが存在するか
		if (!magazineService.isExistById(magazineId)) {
			throw new MagazineNotExistException();
		}
		
		// キーワードリスト取得
		List<Keyword> list = keywordService.getList(magazineId);
		if (list.size() == 0) {
			Keyword keyword = new Keyword();
			list.add(keyword);
		}
		
		// フォームに詰め替え
		form.setMagazineId(magazineId);
		for (Keyword keyword : list) {
			form.addList(keyword);
		}
		
		return "indexList";
	}
	
	@PostMapping("/index/{id}/update")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String update(@ModelAttribute @Validated IndexForm form,
			BindingResult bindingResult,
			Model model) throws Exception {
		
		System.out.println("magazineId: " + form.getMagazineId());
		
		// レコードが存在するか
		if (!magazineService.isExistById(form.getMagazineId())) {
			throw new MagazineNotExistException();
		}
		
		// 入力チェック
		if (bindingResult.hasErrors()) {
			return "indexList";
		}
		
		// 記事リストを登録
		List<Keyword> keywordList = new ArrayList<>();
		for (xyz.tenohira.magazinemanager.domain.form.Keyword item : form.getKeywordList()) {
			Keyword keyword = new Keyword();
			keyword.setMagazineId(form.getMagazineId());
			keyword.setWord(item.getWord());
			keyword.setStartPage(item.getStartPage());
			keywordList.add(keyword);
		}
		boolean result = keywordService.update(form.getMagazineId(), keywordList);
		
		// 実行結果判定
		return result ? "redirect:/list" : "error";
	}
	
	@PostMapping(value = "/index/{id}/edit", params = "add")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String addListItem(@ModelAttribute IndexForm form, Model model) {
		
		// リストに行を追加
		form.addList();
		
		return "indexList";
	}
	
	@PostMapping(value = "/index/{id}/edit", params = "remove")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String removeListItem(@ModelAttribute IndexForm form,
			Model model,
			HttpServletRequest request) {
		
		// 指定した行をリストから削除
		int index = Integer.valueOf(request.getParameter("remove"));
		form.removeList(index);
		
		return "indexList";
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
