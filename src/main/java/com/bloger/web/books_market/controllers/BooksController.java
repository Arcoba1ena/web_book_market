package com.bloger.web.books_market.controllers;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.bloger.web.books_market.models.BooksEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.bloger.web.books_market.services.RegistrationService;
import com.bloger.web.books_market.repository.BooksEntityRepository;

@Controller
public class BooksController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private BooksEntityRepository booksEntityRepository;

    //просмотр библиотеки
    @GetMapping("/allBooks")
    public String booksPage(Model model) {
        Iterable<BooksEntity> bookEntities = booksEntityRepository.findAll();
        model.addAttribute("books", bookEntities);
        if (!registrationService.getAllCredentials().isEmpty()) {
            for (var cred : registrationService.getAllCredentials()) {
                if (cred.get("login").getAsString().equals("admin") && cred.get("login").getAsString().equals("admin") && cred.get("isAuthentication").getAsBoolean()) {
                    return "books-all-admin";
                }
            }
        }
        return "books-all";
    }

    //добавление книги
    @GetMapping("/book/add")
    public String blogAddGetPage(Model model) {
        model.addAttribute("page", "Добавить книгу");
        System.out.println("/book/add" + registrationService.getAllCredentials());
        if (!registrationService.getAllCredentials().isEmpty()) {
            for (var cred : registrationService.getAllCredentials()) {
                if (cred.get("login").getAsString().equals("admin") && cred.get("login").getAsString().equals("admin")) {
                    return "book-add";
                }
            }
        }
        return "auth";
    }

    @PostMapping("/book/add")
    public String bookAddPage(
            @RequestParam String title,
            @RequestParam String descriptions,
            @RequestParam String years,
            @RequestParam String author,
            @RequestParam String category,
            @RequestParam Integer price,
            Model model) {
        BooksEntity booksEntity = new BooksEntity(title, descriptions, years, author, category, price);
        booksEntityRepository.save(booksEntity);
        return "redirect:/allBooks";
    }


    @GetMapping("/book/{id}")
    public String bookDetailsPage(@PathVariable(value = "id") long userId, Model model) {
        if (!registrationService.getAllCredentials().isEmpty()) {
            for (var cred : registrationService.getAllCredentials()) {
                if (cred.get("isAuthentication").getAsBoolean()) {
                    if (!booksEntityRepository.existsById(userId)) {
                        return "redirect:/allBooks";
                    }
                    Optional<BooksEntity> postEntity = booksEntityRepository.findById(userId);
                    List<BooksEntity> resultList = new ArrayList<>();
                    postEntity.ifPresent(resultList::add);
                    model.addAttribute("books", resultList);
                    return "book-details";
                }
            }
        }
        return "auth";
    }

    //редактирование книги
    @GetMapping("/book/{id}/edit")
    public String bookEditPage(@PathVariable(value = "id") long bookId, Model model) {
        if (!booksEntityRepository.existsById(bookId)) {
            return "redirect:/allBooks";
        }
        Optional<BooksEntity> postEntity = booksEntityRepository.findById(bookId);
        List<BooksEntity> resultList = new ArrayList<>();
        postEntity.ifPresent(resultList::add);
        model.addAttribute("books", resultList);
        return "book-edit";
    }

    @PostMapping("/book/{id}/edit")
    public String bookUpdatePage(
            @PathVariable(value = "id") long id,
            @RequestParam String title,
            @RequestParam String descriptions,
            @RequestParam String years,
            @RequestParam String author,
            @RequestParam String category,
            @RequestParam Integer price,
            Model model) {
        BooksEntity booksEntity = booksEntityRepository.findById(id).orElseThrow();
        booksEntity.setTitle(title);
        booksEntity.setDescriptions(descriptions);
        booksEntity.setYears(years);
        booksEntity.setAuthor(author);
        booksEntity.setCategory(category);
        booksEntity.setPrice(price);

        booksEntityRepository.save(booksEntity);
        return "redirect:/allBooks";
    }

    //удаление статьи
    @PostMapping("/book/{id}/remove")
    public String bookDeletePage(@PathVariable(value = "id") long id, Model model) {
        BooksEntity booksEntity = booksEntityRepository.findById(id).orElseThrow();
        booksEntityRepository.delete(booksEntity);
        return "redirect:/allBooks";
    }
}