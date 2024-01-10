package com.example.test.controller;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.example.test.model.*;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin
public class AdminController {

	private AdminDAO adminDAO = new AdminDAO();

	@GetMapping("/admin/home")
	public String getAdminHomePage(Model model) {
		LinkedHashMap<Book, Integer> books = new LinkedHashMap<>();
		for (Book x : adminDAO.getAllBooks()) {
			books.put(x, 1);
		}
		model.addAttribute("books", books);
		return "admin";
	}

	@GetMapping("/admin/login")
	public String getAdminLoginPage() {
		return "admin-login";
	}

	@PostMapping("/admin/login")
	public String adminLogin(Model model, @RequestParam String name, @RequestParam String password,
			HttpSession session) {
		if (adminDAO.selectAdmin(name, password).getId() > 0) {
			session.setAttribute("admin", adminDAO.selectAdmin(name, password));
			return "redirect:/admin/books";
		} else {
			String error = "Your account or your password was incorrect!";
			model.addAttribute("error", error);
			return "admin-login";
		}
	}

	@GetMapping("/admin/logout")
	public String adminLogout(HttpSession session) {
		session.removeAttribute("admin");
		session.removeAttribute("error");
		return "redirect:/admin/home";
	}

	@GetMapping("/admin/books")
	public String getBooks(Model model, HttpSession session) throws IOException {
		Object o = session.getAttribute("admin");
		if (o == null) {
			return "redirect:/admin/home";
		}
		LinkedHashMap<Book, Integer> books = new LinkedHashMap<>();
		List<Order> orders0 = adminDAO.getOrdersByStatus(0);
		List<Order> orders1 = adminDAO.getOrdersByStatus(1);
		for (Book book : adminDAO.getAllBooks()) {
			int orderId = 0;
			for (Order x : orders0) {
				boolean stop = false;
				Cart cart = adminDAO.getCartByOrderId(x.getId());
				Set<Book> keySet = cart.getBooks().keySet();
				for (Book y : keySet) {
					if (y.getId() == book.getId()) {
						stop = true;
						break;
					}
				}
				if (stop) {
					orderId = x.getId();
					break;
				}
			}
			for (Order x : orders1) {
				boolean stop = false;
				Cart cart = adminDAO.getCartByOrderId(x.getId());
				Set<Book> keySet = cart.getBooks().keySet();
				for (Book y : keySet) {
					if (y.getId() == book.getId()) {
						stop = true;
						break;
					}
				}
				if (stop) {
					orderId = x.getId();
					break;
				}
			}
			books.put(book, orderId);
		}
		model.addAttribute("books", books);
		return "admin";
	}
	
	@GetMapping("/admin/books/filter/{bookFilter}")
	public String getFilteredBooks(Model model, @PathVariable String bookFilter) {
		LinkedHashMap<Book, Integer> books = new LinkedHashMap<>();
		List<Order> orders0 = adminDAO.getOrdersByStatus(0);
		List<Order> orders1 = adminDAO.getOrdersByStatus(1);
		
		List<Book> booksList = new ArrayList<>();
		if(Integer.parseInt(bookFilter)==1) {
			booksList = adminDAO.getBooksFromAToZ();
		}else if(Integer.parseInt(bookFilter)==2) {
			booksList = adminDAO.getBooksFromZToA();
		}else if(Integer.parseInt(bookFilter)==3) {
			booksList = adminDAO.getNewestBooks();
		}else if(Integer.parseInt(bookFilter)==4) {
			booksList = adminDAO.getOldestBooks();
		}else if(Integer.parseInt(bookFilter)==5) {
			booksList = adminDAO.getMostPopularBooks();
		}
		for (Book book : booksList) {
			int orderId = 0;
			for (Order x : orders0) {
				boolean stop = false;
				Cart cart = adminDAO.getCartByOrderId(x.getId());
				Set<Book> keySet = cart.getBooks().keySet();
				for (Book y : keySet) {
					if (y.getId() == book.getId()) {
						stop = true;
						break;
					}
				}
				if (stop) {
					orderId = x.getId();
					break;
				}
			}
			for (Order x : orders1) {
				boolean stop = false;
				Cart cart = adminDAO.getCartByOrderId(x.getId());
				Set<Book> keySet = cart.getBooks().keySet();
				for (Book y : keySet) {
					if (y.getId() == book.getId()) {
						stop = true;
						break;
					}
				}
				if (stop) {
					orderId = x.getId();
					break;
				}
			}
			books.put(book, orderId);
		}
		model.addAttribute("books", books);
		model.addAttribute("bookFilter", Integer.parseInt(bookFilter));
		return "admin";
	}
	
	@GetMapping("/admin/books/search")
	public String searchBooks(Model model, @RequestParam("q") String q) {
		if(q.isBlank()) {
			return "redirect:/admin/home";
		}
		LinkedHashMap<Book, Integer> books = new LinkedHashMap<>();
		List<Order> orders0 = adminDAO.getOrdersByStatus(0);
		List<Order> orders1 = adminDAO.getOrdersByStatus(1);
		for (Book book : adminDAO.getBooksByTitleOrAuthor(q, q)) {
			int orderId = 0;
			for (Order x : orders0) {
				boolean stop = false;
				Cart cart = adminDAO.getCartByOrderId(x.getId());
				Set<Book> keySet = cart.getBooks().keySet();
				for (Book y : keySet) {
					if (y.getId() == book.getId()) {
						stop = true;
						break;
					}
				}
				if (stop) {
					orderId = x.getId();
					break;
				}
			}
			for (Order x : orders1) {
				boolean stop = false;
				Cart cart = adminDAO.getCartByOrderId(x.getId());
				Set<Book> keySet = cart.getBooks().keySet();
				for (Book y : keySet) {
					if (y.getId() == book.getId()) {
						stop = true;
						break;
					}
				}
				if (stop) {
					orderId = x.getId();
					break;
				}
			}
			books.put(book, orderId);
		}
		model.addAttribute("books", books);
		return "admin";
	}

	@GetMapping("/admin/book/{bookId}")
	public String getBook(Model model, @PathVariable String bookId, HttpSession session) {
		Object o = session.getAttribute("admin");
		if (o == null) {
			return "redirect:/admin/home";
		}

		model.addAttribute("bookId", bookId);
		Book book = new Book();
		if (Integer.valueOf(bookId) > 0) {
			book = adminDAO.getBookById(Integer.parseInt(bookId));
		}
		model.addAttribute("book", book);
		model.addAttribute("categories", adminDAO.getCategories());
		return "admin-book-detail";
	}

	@PostMapping("/admin/book/save/{bookId}")
	public String addBook(Model model, Book book, @PathVariable String bookId,
			@RequestParam("imageInput") MultipartFile multipartFile, HttpSession session) throws Exception {
		Object o = session.getAttribute("admin");
		if (o == null) {
			return "redirect:/admin/home";
		}
		if (!adminDAO.checkExistedBookWhenInsert(book)) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			book.setBookCover(fileName);
			adminDAO.insertBook(book);

			String dirName = "imgs/" + adminDAO.getLastBook().getId();

			Path uploadPath = Paths.get(dirName);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputStream = multipartFile.getInputStream()) {
				Path filePath = uploadPath.resolve(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
				System.out.println(filePath.toString());
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "redirect:/admin/books";
		} else {
			String error = "The book was existed. Please add a new book!";
			model.addAttribute("error", error);
			return "admin-book-detail";
		}
	}

	@PutMapping("/admin/book/save/{bookId}")
	public String updateBook(Model model, Book book, @PathVariable String bookId,
			@RequestParam("imageInput") MultipartFile multipartFile, HttpSession session) throws Exception {
		Object o = session.getAttribute("admin");
		if (o == null) {
			return "redirect:/admin/home";
		}
		if (!adminDAO.checkExistedBookWhenUpdate(book)) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			if (fileName.equals("")) {
				book.setBookCover(adminDAO.getBookById(Integer.parseInt(bookId)).getBookCover());
			} else {
				book.setBookCover(fileName);
			}
			adminDAO.updateBook(book);

			String dirName = "imgs/" +Integer.parseInt(bookId);

			Path uploadPath = Paths.get(dirName);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputStream = multipartFile.getInputStream()) {
				Path filePath = uploadPath.resolve(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
				System.out.println(filePath.toString());
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "redirect:/admin/books";
		} else {
			String error = "The book was existed. Please add a new book!";
			model.addAttribute("error", error);
			return "admin-book-detail";
		}
	}

	@DeleteMapping("/admin/book/delete/{bookId}")
	public String deleteBook(Model model, @PathVariable String bookId, HttpSession session) {
		Object o = session.getAttribute("admin");
		if (o == null) {
			return "redirect:/admin/home";
		}
		List<Order> orders0 = adminDAO.getOrdersByStatus(0);
		List<Order> orders1 = adminDAO.getOrdersByStatus(1);
		boolean check = true;
		for (Order x : orders0) {
			boolean stop = false;
			Cart cart = adminDAO.getCartByOrderId(x.getId());
			Set<Book> keySet = cart.getBooks().keySet();
			for (Book y : keySet) {
				if (y.getId() == Integer.parseInt(bookId)) {
					check = false;
					stop = true;
					break;
				}
			}
			if (stop) {
				break;
			}
		}
		for (Order x : orders1) {
			boolean stop = false;
			Cart cart = adminDAO.getCartByOrderId(x.getId());
			Set<Book> keySet = cart.getBooks().keySet();
			for (Book y : keySet) {
				if (y.getId() == Integer.parseInt(bookId)) {
					check = false;
					stop = true;
					break;
				}
			}
			if (stop) {
				break;
			}
		}
		if (check) {
			adminDAO.deleteBook(Integer.parseInt(bookId));
		}
		return "redirect:/admin/books";
	}

	@GetMapping("/admin/order")
	public String getOrderPage(HttpSession session, Model model) {
		Object o = session.getAttribute("admin");
		if (o == null) {
			return "redirect:/admin/home";
		}
		model.addAttribute("orders", adminDAO.getAllOrders());
		model.addAttribute("orderFilter", "All");
		return "admin-order";
	}

	@GetMapping("/admin/order/filter")
	public String getFilteredOrders(HttpSession session, Model model, @RequestParam("filter") String filter) {
		Object o = session.getAttribute("admin");
		if (o == null) {
			return "redirect:/admin/home";
		}
		if (filter.equals("All")) {
			model.addAttribute("orders", adminDAO.getAllOrders());
		} else if (filter.equals("Waiting")) {
			model.addAttribute("orders", adminDAO.getOrdersByStatus(0));
		} else if (filter.equals("Delivering")) {
			model.addAttribute("orders", adminDAO.getOrdersByStatus(1));
		} else {
			model.addAttribute("orders", adminDAO.getOrdersByStatus(2));
		}
		model.addAttribute("orderFilter", filter);
		return "admin-order";
	}

	@GetMapping("/admin/order/search")
	public String getOrdersFromTo(HttpSession session, Model model, @RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate) {
		Object o = session.getAttribute("admin");
		if (o == null) {
			return "redirect:/admin/home";
		}
		model.addAttribute("orders", adminDAO.getOrdersFromTo(fromDate, toDate));

		return "admin-order";
	}

	@GetMapping("/admin/order/detail/{orderId}")
	public String getOrderDetail(HttpSession session, Model model, @PathVariable String orderId) {
		Object o = session.getAttribute("admin");
		if (o == null) {
			return "redirect:/user/home";
		}
		model.addAttribute("cart", adminDAO.getCartByOrderId(Integer.parseInt(orderId)));
		return "admin-order-detail";
	}

	@PutMapping("/admin/order/delivering")
	public String updateDeliveringOrder(HttpSession session, @RequestParam("orderId") String orderId) {
		adminDAO.updateOrderStatus(Integer.parseInt(orderId), 1);
		return "redirect:/admin/order";
	}

	@PutMapping("/admin/order/delivered")
	public String updateDeliveredOrder(HttpSession session, @RequestParam("orderId") String orderId) {
		adminDAO.updateOrderStatus(Integer.parseInt(orderId), 2);
		Cart cart = adminDAO.getCartByOrderId(Integer.parseInt(orderId));
		LinkedHashMap<Book, Integer> books = cart.getBooks();
		Set<Book> keySet = books.keySet();
		for (Book x : keySet) {
			adminDAO.updateBookSold(x.getId(), x.getSold() + books.get(x));
		}
		return "redirect:/admin/order";
	}
	
	@GetMapping("/admin/profile/{adminId}")
	public String getProfilePage(HttpSession session, Model model, @PathVariable String adminId) {
		if (session.getAttribute("admin") == null) {
			return "redirect:/admin/home";
		}
		return "admin-profile";
	}

	@PutMapping("/admin/profile/{adminId}")
	public String updateProfile(HttpSession session, Model model, @PathVariable String adminId,
			@RequestParam String fullName, @RequestParam Date dob, @RequestParam String email,
			@RequestParam String phone) {
		if (session.getAttribute("admin") == null) {
			return "redirect:/admin/home";
		}
		Admin admin = adminDAO.selectAdminByIdOrName(Integer.parseInt(adminId), "");
		if (!admin.getRealName().contentEquals(fullName) || !admin.getDob().equals(dob)
				|| !admin.getEmail().contentEquals(email) || !admin.getPhone().contentEquals(phone)) {
			adminDAO.updateAdminInfo(Integer.parseInt(adminId), fullName, dob, email, phone);
			session.setAttribute("admin", adminDAO.selectAdminByIdOrName(Integer.parseInt(adminId), ""));
			model.addAttribute("message", "Update successfully!");
			return "admin-profile";
		} else {
			model.addAttribute("message", "No change");
			return "admin-profile";
		}
	}

	@GetMapping("/admin/change-password/{adminId}")
	public String getChangePasswordPage(HttpSession session, Model model, @PathVariable String adminId) {
		if (session.getAttribute("admin") == null) {
			return "redirect:/admin/home";
		}
		return "admin-change-password";
	}

	@PutMapping("/admin/change-password/{adminId}")
	public String changePassword(HttpSession session, Model model, @PathVariable String adminId,
			@RequestParam String currentPassword, @RequestParam String newPassword,
			@RequestParam String confirmNewPassword) {
		if (session.getAttribute("admin") == null) {
			return "redirect:/admin/home";
		}
		if (!currentPassword.contentEquals(adminDAO.selectAdminByIdOrName(Integer.parseInt(adminId), "").getPassword())) {
			model.addAttribute("error", "Wrong password!!!");
		} else if (currentPassword.contentEquals(newPassword)) {
			model.addAttribute("error", "Your new password is as same as your current password!");
		} else if (!newPassword.contentEquals(confirmNewPassword)) {
			model.addAttribute("error", "Your confirm password does not match!");
		} else {
			model.addAttribute("message", "Change password successfully!");
			adminDAO.updateAdminPassword(Integer.parseInt(adminId), newPassword);
		}
		return "admin-change-password";
	}
}
