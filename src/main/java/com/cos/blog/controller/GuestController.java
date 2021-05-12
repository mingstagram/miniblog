package com.cos.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.GuestBook;
import com.cos.blog.service.GuestService;

@Controller
public class GuestController {
	
	@Autowired
	private GuestService guestService;

	@GetMapping("/guest/guestBook")
	public String guestBook(Model model) {
		List<GuestBook> guestBook =  guestService.방명록목록();
		model.addAttribute("guest", guestBook);
		return "guest/guestBook";
	} 
}
