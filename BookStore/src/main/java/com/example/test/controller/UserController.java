package com.example.test.controller;

import java.util.*;
//import java.io.*;
import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.example.test.model.*;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

@Controller
@CrossOrigin
public class UserController {

	private UserDAO userDAO = new UserDAO();
	private AdminDAO adminDAO = new AdminDAO();

	@GetMapping("/user/home")
	public String getUserHomePage(Model model) {
		model.addAttribute("books", userDAO.getAllBooks());
		model.addAttribute("categories", userDAO.getCategories());
		return "user";
	}

	@GetMapping("/user/login")
	public String getUserLoginPage(Model model) {
		model.addAttribute("categories", userDAO.getCategories());
		return "user-login";
	}

	@PostMapping("/user/login")
	public String userLogin(Model model, @RequestParam String userName, @RequestParam String password,
			HttpSession session) {
		User user = userDAO.selectExistedUser(userName, password);
		if (user.getId() > 0) {
			session.setAttribute("user", user);
			Order order = userDAO.getLastOrder(user.getId());
			if (order.getId() == 0 || order.getConfirm()) {
				userDAO.addOrder(user.getId());
				order = userDAO.getLastOrder(user.getId());
			}
			Cart cart = userDAO.getCart(order.getId());
			session.setAttribute("cart", cart);
			session.setMaxInactiveInterval(0);
			return "redirect:/user/books";
		} else {
			String error = "Your account or your password was incorrect!";
			model.addAttribute("error", error);
			return "user-login";
		}
	}

	@GetMapping("/user/logout")
	public String userLogout(HttpSession session, Model model) {
		session.removeAttribute("user");
		session.removeAttribute("cart");
		model.addAttribute("categories", userDAO.getCategories());
		return "redirect:/user/home";
	}

	@GetMapping("/user/register")
	public String getRegisterPage(Model model) {
		return "register";
	}

	@PostMapping("/user/register")
	public String register(Model model, @RequestParam String userName, @RequestParam String password,
			@RequestParam String confirmPassword, @RequestParam String fullName, @RequestParam Date dob,
			@RequestParam String email, @RequestParam String phone, HttpSession session) {
		model.addAttribute("fullName", fullName);
		model.addAttribute("dob", dob);
		model.addAttribute("email", email);
		model.addAttribute("phone", phone);
		if (userDAO.selectUserByIdOrName(0, userName).getAccName() == null
				&& adminDAO.selectAdminByIdOrName(0, userName).getAccName() == null) {
			if (password.contentEquals(confirmPassword)) {
				userDAO.insertUser(userName, password, fullName, dob, email, phone);
				session.setAttribute("user", userDAO.selectExistedUser(userName, password));
				return "redirect:/user/login";
			} else {
				String error = "Your confirm password does not match!";
				model.addAttribute("error", error);
				model.addAttribute("userName", userName);
				return "register";
			}
		} else {
			String error = "Your username existed!";
			model.addAttribute("error", error);
			return "register";
		}
	}

	@GetMapping("/user/category/{categoryName}")
	public String getBooksCategory(Model model, @PathVariable String categoryName) {
		model.addAttribute("books", userDAO.getBookByCategory(categoryName));
		model.addAttribute("categories", userDAO.getCategories());
		return "user";
	}

	@GetMapping("/user/books")
	public String getAllBooks(Model model, HttpSession session) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		model.addAttribute("books", userDAO.getAllBooks());
		model.addAttribute("categories", userDAO.getCategories());
		return "user";
	}

	@GetMapping("/user/books/filter/{bookFilter}")
	public String getTop10BestSellers(Model model, @PathVariable String bookFilter) {
		if (Integer.parseInt(bookFilter) == 1) {
			model.addAttribute("books", userDAO.getBooksFromAToZ());
		} else if (Integer.parseInt(bookFilter) == 2) {
			model.addAttribute("books", userDAO.getBooksFromZToA());
		} else if (Integer.parseInt(bookFilter) == 3) {
			model.addAttribute("books", userDAO.getNewestBooks());
		} else if (Integer.parseInt(bookFilter) == 4) {
			model.addAttribute("books", userDAO.getOldestBooks());
		} else if (Integer.parseInt(bookFilter) == 5) {
			model.addAttribute("books", userDAO.getTop10BestSellers());
		}
		model.addAttribute("bookFilter", Integer.parseInt(bookFilter));
		model.addAttribute("categories", userDAO.getCategories());
		return "user";
	}

	@GetMapping("/user/books/search")
	public String searchBooks(Model model, @PathParam("q") String q) {
		if (q.isBlank()) {
			return "redirect:/user/home";
		}
		model.addAttribute("books", userDAO.getBooksByTitleOrAuthor(q, q));
		model.addAttribute("categories", userDAO.getCategories());
		return "user";
	}

	@GetMapping("/user/book/{bookId}")
	public String getBookById(Model model, @PathVariable String bookId) {
		model.addAttribute("book", userDAO.getBookById(Integer.parseInt(bookId)));
		model.addAttribute("comments", userDAO.getComments(Integer.parseInt(bookId)));
		model.addAttribute("categories", userDAO.getCategories());
		return "user-book-detail";
	}

	@PostMapping("/user/book/{bookId}/comment")
	public String insertComment(Model model, HttpSession session, @PathVariable String bookId,
			@RequestParam String userId, @RequestParam String ratingPost, @RequestParam String comment) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		userDAO.insertComment(Integer.parseInt(userId), Integer.parseInt(bookId), Integer.parseInt(ratingPost),
				comment);
		return "redirect:/user/book/" + bookId;
	}

	@PutMapping("/user/book/{bookId}/comment")
	public String updateComment(Model model, HttpSession session, @PathVariable String bookId,
			@RequestParam String commentId, @RequestParam String ratingPut, @RequestParam String comment) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		userDAO.updateComment(Integer.parseInt(commentId), Integer.parseInt(ratingPut), comment);
		return "redirect:/user/book/" + bookId;
	}

	@DeleteMapping("/user/book/{bookId}/comment/delete")
	public String deleteComment(Model model, HttpSession session, @PathVariable String bookId,
			@RequestParam String commentId) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		userDAO.deleteComment(Integer.parseInt(commentId));
		return "redirect:/user/book/" + bookId;
	}

	@GetMapping("/user/cart")
	public String getCart(HttpSession session, Model model) {
		model.addAttribute("categories", userDAO.getCategories());
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		return "user-cart";
	}

	@PostMapping("/user/cart")
	public String addCart(HttpSession session, Model model, @SessionAttribute("user") User user,
			@SessionAttribute("cart") Cart cart, @RequestParam String bookId, @RequestParam String quantity) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}

		int userId = user.getId();
		if (cart.getOrder().getConfirm()) {
			userDAO.addOrder(userId);
			cart = userDAO.getCart(userDAO.getLastOrder(userId).getId());
		}

		LinkedHashMap<Book, Integer> books = cart.getBooks();
		boolean existed = false;
		int oldQuantity = 0;
		Set<Book> keySet = books.keySet();
		for (Book x : keySet) {
			if (x.getId() == Integer.parseInt(bookId)) {
				existed = true;
				oldQuantity = books.get(x);
				break;
			}
		}
		if (!existed) {
			userDAO.insertCart(cart.getOrder().getId(), Integer.parseInt(bookId), Integer.parseInt(quantity));
		} else {
			userDAO.updateCart(cart.getOrder().getId(), Integer.parseInt(bookId),
					Integer.parseInt(quantity) + oldQuantity);
		}

		Cart newCart = userDAO.getCart(cart.getOrder().getId());
		session.setAttribute("cart", newCart);

		return "redirect:/user/cart";
	}

	@GetMapping("/user/cart/handle")
	public String handleQuantity(HttpSession session, Model model, @SessionAttribute("cart") Cart cart,
			@PathParam("number") String number, @PathParam("bookId") String bookId) {
		model.addAttribute("categories", userDAO.getCategories());
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		LinkedHashMap<Book, Integer> books = cart.getBooks();
		int oldQuantity = 0;
		Set<Book> keySet = books.keySet();
		for (Book x : keySet) {
			if (x.getId() == Integer.parseInt(bookId)) {
				oldQuantity = books.get(x);
				break;
			}
		}
		if (oldQuantity + Integer.parseInt(number) > 0) {
			userDAO.updateCart(cart.getOrder().getId(), Integer.parseInt(bookId),
					oldQuantity + Integer.parseInt(number));
		}
		Cart newCart = userDAO.getCart(cart.getOrder().getId());
		session.setAttribute("cart", newCart);

		return "redirect:/user/cart";
	}

	@DeleteMapping("/user/cart/delete")
	public String deleteItem(HttpSession session, @RequestParam String orderId, @RequestParam String bookId) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		userDAO.deleteItem(Integer.parseInt(orderId), Integer.parseInt(bookId));
		session.setAttribute("cart", userDAO.getCart(Integer.parseInt(orderId)));

		return "redirect:/user/cart";
	}

	@PostMapping("/user/order")
	public String addOrder(HttpSession session, @SessionAttribute("user") User user,
			@SessionAttribute("cart") Cart cart) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		userDAO.updateOrderConfirm(cart.getOrder().getId(), 1);
		userDAO.updateOrderTotalPrice(cart.getOrder().getId(), cart.getTotalPrice());
		userDAO.updateOrderDate(cart.getOrder().getId(), new Date(System.currentTimeMillis()));
		session.setAttribute("cart", new Cart());
		return "redirect:/user/order";
	}

	@GetMapping("/user/order")
	public String getOrders(HttpSession session, Model model, @SessionAttribute("user") User user) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		model.addAttribute("categories", userDAO.getCategories());
		List<Order> orders = userDAO.getConfirmedOrders(user.getId());
		model.addAttribute("orders", orders);

		return "user-order";
	}

	@DeleteMapping("/user/order/delete")
	public String deleteOrder(HttpSession session, Model model, @RequestParam String orderId) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		userDAO.deleteOrder(Integer.parseInt(orderId));
		userDAO.deleteCart(Integer.parseInt(orderId));

		return "redirect:/user/order";
	}

	@GetMapping("/user/order/detail/{orderId}")
	public String getOrderDetail(HttpSession session, Model model, @PathVariable String orderId) {
		Object o = session.getAttribute("user");
		if (o == null) {
			return "redirect:/user/home";
		}
		Cart cart = userDAO.getCart(Integer.parseInt(orderId));
		model.addAttribute("cart", cart);
		model.addAttribute("categories", userDAO.getCategories());
		return "user-order-detail";
	}

	@GetMapping("/user/profile/{userId}")
	public String getProfilePage(HttpSession session, Model model, @PathVariable String userId) {
		if (session.getAttribute("user") == null) {
			return "redirect:/user/home";
		}
		return "user-profile";
	}

	@PutMapping("/user/profile/{userId}")
	public String updateProfile(HttpSession session, Model model, @PathVariable String userId,
			@RequestParam String fullName, @RequestParam Date dob, @RequestParam String email,
			@RequestParam String phone) {
		if (session.getAttribute("user") == null) {
			return "redirect:/user/home";
		}
		User user = userDAO.selectUserByIdOrName(Integer.parseInt(userId), "");
		if (!user.getRealName().contentEquals(fullName) || !user.getDob().equals(dob)
				|| !user.getEmail().contentEquals(email) || !user.getPhone().contentEquals(phone)) {
			userDAO.updateUserInfo(Integer.parseInt(userId), fullName, dob, email, phone);
			session.setAttribute("user", userDAO.selectUserByIdOrName(Integer.parseInt(userId), ""));
			model.addAttribute("message", "Update successfully!");
			return "user-profile";
		} else {
			model.addAttribute("message", "No change");
			return "user-profile";
		}
	}

	@GetMapping("/user/change-password/{userId}")
	public String getChangePasswordPage(HttpSession session, Model model, @PathVariable String userId) {
		if (session.getAttribute("user") == null) {
			return "redirect:/user/home";
		}
		return "user-change-password";
	}

	@PutMapping("/user/change-password/{userId}")
	public String changePassword(HttpSession session, Model model, @PathVariable String userId,
			@RequestParam String currentPassword, @RequestParam String newPassword,
			@RequestParam String confirmNewPassword) {
		if (session.getAttribute("user") == null) {
			return "redirect:/user/home";
		}
		if (!currentPassword.contentEquals(userDAO.selectUserByIdOrName(Integer.parseInt(userId), "").getPassword())) {
			model.addAttribute("error", "Wrong password!!!");
		} else if (currentPassword.contentEquals(newPassword)) {
			model.addAttribute("error", "Your new password is as same as your current password!");
		} else if (!newPassword.contentEquals(confirmNewPassword)) {
			model.addAttribute("error", "Your confirm password does not match!");
		} else {
			model.addAttribute("message", "Change password successfully!");
			userDAO.updateUserPassword(Integer.parseInt(userId), newPassword);
		}
		return "user-change-password";
	}
}
