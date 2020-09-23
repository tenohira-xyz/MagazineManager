package xyz.tenohira.magazinemanager.controller;

import java.util.ArrayList;
import java.util.Iterator;
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

import xyz.tenohira.magazinemanager.domain.model.Article;
import xyz.tenohira.magazinemanager.domain.form.ContentForm;
import xyz.tenohira.magazinemanager.domain.service.ArticleService;
import xyz.tenohira.magazinemanager.domain.service.MagazineService;
import xyz.tenohira.magazinemanager.exception.MagazineNotExistException;

@Controller
public class ContentListController {

	@Autowired
	MagazineService magazineService;
	
	@Autowired
	ArticleService articleService;
	
	@GetMapping("/contents/{id}")
	public String load(@ModelAttribute ContentForm form,
			Model model,
			@PathVariable("id") int magazineId) throws Exception {
		
		// 雑誌レコードが存在するか
		if (!magazineService.isExistById(magazineId)) {
			throw new MagazineNotExistException();
		}
		
		// 記事リスト取得
		List<Article> list = articleService.getList(magazineId);
		if (list.size() == 0) {
			Article article = new Article();
			list.add(article);
		}
		
		// フォームに詰め替え
		form.setMagazineId(magazineId);
		for (Article article : list) {
			form.addList(article);
		}
		
		return "contentsList";
	}
	
	@PostMapping("/contents/{id}/update")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String update(@ModelAttribute @Validated ContentForm form,
			BindingResult bindingResult,
			Model model) throws Exception {
		
		System.out.println("magazineId: " + form.getMagazineId());
		
		// レコードが存在するか
		if (!magazineService.isExistById(form.getMagazineId())) {
			throw new MagazineNotExistException();
		}
		
		// 入力チェック
		if (bindingResult.hasErrors()) {
			return "contentsList";
		}
		
		// 記事リストを登録
		List<Article> articleList = new ArrayList<>();
		for (xyz.tenohira.magazinemanager.domain.form.Article item : form.getArticleList()) {
			Article article = new Article();
			article.setMagazineId(form.getMagazineId());
			article.setSection(item.getSection());
			article.setTitle(item.getTitle());
			article.setStartPage(item.getStartPage());
			articleList.add(article);
		}
		boolean result = articleService.update(form.getMagazineId(), articleList);
		
		// 実行結果判定
		return result ? "redirect:/list" : "error";
	}
	
	@PostMapping(value = "/contents/{id}/edit", params = "add")
	public String addListItem(@ModelAttribute @Validated ContentForm form,
			Model model) throws Exception {
		
		// リストに行を追加
		form.addList();
		
		return "contentsList";
	}
	
	@PostMapping(value = "/contents/{id}/edit", params = "remove")
	public String removeListItem(@ModelAttribute @Validated ContentForm form,
			Model model,
			HttpServletRequest request) throws Exception {
		
		// 指定した行をリストから削除
		int index = Integer.valueOf(request.getParameter("remove"));
		form.removeList(index); 
		
		return "contentsList";
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
