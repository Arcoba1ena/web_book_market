package com.bloger.web.books_market.controllers;

import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.ui.Model;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Controller;
import com.bloger.web.books_market.models.BooksEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.bloger.web.books_market.services.RegistrationService;
import com.bloger.web.books_market.services.BooksSubscribeService;
import com.bloger.web.books_market.repository.BooksEntityRepository;

@Controller
public class MyBooksController {

    @Autowired
    private BooksSubscribeService subscribeService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private BooksEntityRepository booksEntityRepository;

    @GetMapping("/myBooks")
    public String myBooks(Model model) {
        List<String> myBooksList = subscribeService.getAllSubscribe();

        List<Map<String, Object>> articles = List.of(
                Map.of(
                        "title", "«Ревизор»",
                        "years", "1836",
                        "author", "Николай Гоголь",
                        "category", "Комедия",
                        "descriptions",
                        "Прокутивший всё состояние дворянин, у которого от блеска прошлой жизни остались только дорогие шмотки,\n" +
                            "приезжает в сонный провинциальной городок. Там местные чиновники и простые жители принимают парня за\n" +
                            "большую государственную шишку, ответственную за проверку состояния дел в городе («ревизора»), и начинают\n" +
                            "всячески его задабривать…",
                        "price", "1590 р."
                ),
                Map.of(
                        "title", "«Горе от ума»",
                        "years", "1825",
                        "author", "Александр Грибоедов",
                        "category", "Комедия",
                        "descriptions",
                        "Остроумная, лёгкая, виртуозно сочетающая смешное с грустным, эта стихотворная комедия повествует об\n" +
                            "инициативном и честном молодом человеке, который наведывается в имение к старым знакомым после\n" +
                            "продолжительной учёбы в Европе. Героя раздражает лицемерие и праздность московской тусовки, но в\n" +
                            "доме живёт любовь его юности, с которой он долгое время был разлучён и теперь снова желает сойтись.\n" +
                            "Но девушку не привлекает интеллект, искренность и дерзость парня по отношению к устоям, она «вросла»\n" +
                            "в светское общество и не желает расставаться с его блеском и пороками…",
                        "price", "2300 р."
                ),
                Map.of(
                        "title", "«Капитанская дочка»",
                        "years", "1836",
                        "author", "Александр Пушкин",
                        "category", "Роман",
                        "descriptions",
                        "Первый бабник Петербурга, светский тусовщик, любитель шампанского, крепкого словца и дуэлей -\n" +
                            "вспоминая про «Наше всё», в голове у обывателя наверняка возникает образ этакой рокзвезды,\n" +
                            "гениального распиздяя, которому всё давалось легко и непринуждённо, этакого Марадоны от мира русской\n" +
                            "классики. Но гораздо меньше людей знают о другой стороне личности великого писателя: Пушкин был\n" +
                            "историческим гиком, скрупулёзно изучавшим тысячи архивных документов, чтобы составить своё\n" +
                            "представление о минувшей эпохе и выполнить поставленные перед собой творческие задачи. А во время\n" +
                            "работы над «Капитанской дочкой» он отправился на южный Урал (нехуёвая поездочка для начала XIX века\n" +
                            "!), дабы лично пообщаться со свидетелями исторических события, увидеть и почувствовать среду, в\n" +
                            "которой жили люди и подготовить материал для, пожалуй, главного шедевра в своей литературной карьере.",
                        "price", "1560 р."
                ),
                Map.of(
                        "title", "«Герой нашего времени»",
                        "years", "1840",
                        "author", "Михаил Лермонтов",
                        "category", "Роман",
                        "descriptions",
                        "Сюжет этой книги пересказать довольно непросто. Она представляет из себя деконструкцию романтической\n" +
                            "приключенческой повести и обладает передовой для своего времени структурой: нелинейностью,\n" +
                            "бессвязностью глав, сочетанием авторского повествования и «POV»-дневников от первого лица. Главный\n" +
                            "герой на страницах произведения даже «встречается» с самим Лермонтовым ! Интеллигентный, но циничный\n" +
                            "прапорщик Печорин по долгу службы и в поиске острых ощущений оказывается в разных живописных местах\n" +
                            "на юге России, но везде чувствует лишь тоску и бессмысленность своего существования…",
                        "price", "1560 р."
                ),
                Map.of(
                        "title", "«Обломов»",
                        "years", "1859",
                        "author", "Иван Гончаров",
                        "category", "Роман",
                        "descriptions",
                        "Лермонтовский образ Печорина был отнюдь не единственным вариантом «лишнего человека» в русской классике.\n" +
                            "Если его циничный офицер ещё пытался вести со своей судьбой торг, то персонаж, созданный писателем и\n" +
                            "литературным критиком Иваном Гончаровым в своём главном произведении, встречает читателя на стадии\n" +
                            "«принятия»",
                        "price", "1560 р."
                )
        );
        Iterable<BooksEntity> bookEntities = booksEntityRepository.findAll();
        List<BooksEntity> filteredBooks = StreamSupport.stream(bookEntities.spliterator(), false)
        .filter(book -> myBooksList.contains(book.getAuthor())).toList();

        model.addAttribute("myBooksList", myBooksList);
        model.addAttribute("articles", articles);
        model.addAttribute("books", filteredBooks);

        if (!registrationService.getAllCredentials().isEmpty()) {
            for (var cred : registrationService.getAllCredentials()) {
                if (cred.get("isAuthentication").getAsBoolean()) {
                    return "my-books";
                }
            }
        }
        return "auth";
    }

    @GetMapping("/author/sort")
    public String sortByAuthor(Model model) {

        List<String> myBooksList = subscribeService.getAllSubscribe();

        List<Map<String, Object>> articles = List.of(
                Map.of(
                        "title", "«Ревизор»",
                        "years", "1836",
                        "author", "Николай Гоголь",
                        "category", "Комедия",
                        "descriptions",
                        "Прокутивший всё состояние дворянин, у которого от блеска прошлой жизни остались только дорогие шмотки,\n" +
                            "приезжает в сонный провинциальной городок. Там местные чиновники и простые жители принимают парня за\n" +
                            "большую государственную шишку, ответственную за проверку состояния дел в городе («ревизора»), и начинают\n" +
                            "всячески его задабривать…",
                        "price", "1590 р."
                ),
                Map.of(
                        "title", "«Горе от ума»",
                        "years", "1825",
                        "author", "Александр Грибоедов",
                        "category", "Комедия",
                        "descriptions",
                        "Остроумная, лёгкая, виртуозно сочетающая смешное с грустным, эта стихотворная комедия повествует об\n" +
                            "инициативном и честном молодом человеке, который наведывается в имение к старым знакомым после\n" +
                            "продолжительной учёбы в Европе. Героя раздражает лицемерие и праздность московской тусовки, но в\n" +
                            "доме живёт любовь его юности, с которой он долгое время был разлучён и теперь снова желает сойтись.\n" +
                            "Но девушку не привлекает интеллект, искренность и дерзость парня по отношению к устоям, она «вросла»\n" +
                            "в светское общество и не желает расставаться с его блеском и пороками…",
                        "price", "2300 р."
                ),
                Map.of(
                        "title", "«Капитанская дочка»",
                        "years", "1836",
                        "author", "Александр Пушкин",
                        "category", "Роман",
                        "descriptions",
                        "Первый бабник Петербурга, светский тусовщик, любитель шампанского, крепкого словца и дуэлей -\n" +
                            "вспоминая про «Наше всё», в голове у обывателя наверняка возникает образ этакой рокзвезды,\n" +
                            "гениального распиздяя, которому всё давалось легко и непринуждённо, этакого Марадоны от мира русской\n" +
                            "классики. Но гораздо меньше людей знают о другой стороне личности великого писателя: Пушкин был\n" +
                            "историческим гиком, скрупулёзно изучавшим тысячи архивных документов, чтобы составить своё\n" +
                            "представление о минувшей эпохе и выполнить поставленные перед собой творческие задачи. А во время\n" +
                            "работы над «Капитанской дочкой» он отправился на южный Урал (нехуёвая поездочка для начала XIX века\n" +
                            "!), дабы лично пообщаться со свидетелями исторических события, увидеть и почувствовать среду, в\n" +
                            "которой жили люди и подготовить материал для, пожалуй, главного шедевра в своей литературной карьере.",
                        "price", "1560 р."
                ),
                Map.of(
                        "title", "«Герой нашего времени»",
                        "years", "1840",
                        "author", "Михаил Лермонтов",
                        "category", "Роман",
                        "descriptions",
                        "Сюжет этой книги пересказать довольно непросто. Она представляет из себя деконструкцию романтической\n" +
                            "приключенческой повести и обладает передовой для своего времени структурой: нелинейностью,\n" +
                            "бессвязностью глав, сочетанием авторского повествования и «POV»-дневников от первого лица. Главный\n" +
                            "герой на страницах произведения даже «встречается» с самим Лермонтовым ! Интеллигентный, но циничный\n" +
                            "прапорщик Печорин по долгу службы и в поиске острых ощущений оказывается в разных живописных местах\n" +
                            "на юге России, но везде чувствует лишь тоску и бессмысленность своего существования…",
                        "price", "1560 р."
                ),
                Map.of(
                        "title", "«Обломов»",
                        "years", "1859",
                        "author", "Иван Гончаров",
                        "category", "Роман",
                        "descriptions",
                        "Лермонтовский образ Печорина был отнюдь не единственным вариантом «лишнего человека» в русской классике.\n" +
                            "Если его циничный офицер ещё пытался вести со своей судьбой торг, то персонаж, созданный писателем и\n" +
                            "литературным критиком Иваном Гончаровым в своём главном произведении, встречает читателя на стадии\n" +
                            "«принятия»",
                        "price", "1560 р."
                )
        );

        List<Map<String, Object>> sortedArticles = articles.stream()
                .sorted((a1, a2) -> {
                    String author1 = (String) a1.get("author");
                    String author2 = (String) a2.get("author");
                    return author1.compareTo(author2);
                })
                .collect(Collectors.toList());

        Iterable<BooksEntity> bookEntities = booksEntityRepository.findAll();
        List<BooksEntity> filteredBooks = StreamSupport.stream(bookEntities.spliterator(), false)
                .filter(book -> myBooksList.contains(book.getAuthor()))
                .sorted(Comparator.comparing(BooksEntity::getAuthor))
                .toList();

        model.addAttribute("myBooksList", myBooksList);
        model.addAttribute("articles", sortedArticles);
        model.addAttribute("books", filteredBooks);
        System.out.println(sortedArticles);

        return "my-books";
    }

    @GetMapping("/years/sort")
    public String sortByYears(Model model) {

        List<String> myBooksList = subscribeService.getAllSubscribe();

        List<Map<String, Object>> articles = List.of(
                Map.of(
                        "title", "«Ревизор»",
                        "years", "1836",
                        "author", "Николай Гоголь",
                        "category", "Комедия",
                        "descriptions",
                        "Прокутивший всё состояние дворянин, у которого от блеска прошлой жизни остались только дорогие шмотки,\n" +
                            "приезжает в сонный провинциальной городок. Там местные чиновники и простые жители принимают парня за\n" +
                            "большую государственную шишку, ответственную за проверку состояния дел в городе («ревизора»), и начинают\n" +
                            "всячески его задабривать…",
                        "price", "1590 р."
                ),
                Map.of(
                        "title", "«Горе от ума»",
                        "years", "1825",
                        "author", "Александр Грибоедов",
                        "category", "Комедия",
                        "descriptions",
                        "Остроумная, лёгкая, виртуозно сочетающая смешное с грустным, эта стихотворная комедия повествует об\n" +
                            "инициативном и честном молодом человеке, который наведывается в имение к старым знакомым после\n" +
                            "продолжительной учёбы в Европе. Героя раздражает лицемерие и праздность московской тусовки, но в\n" +
                            "доме живёт любовь его юности, с которой он долгое время был разлучён и теперь снова желает сойтись.\n" +
                            "Но девушку не привлекает интеллект, искренность и дерзость парня по отношению к устоям, она «вросла»\n" +
                            "в светское общество и не желает расставаться с его блеском и пороками…",
                        "price", "2300 р."
                ),
                Map.of(
                        "title", "«Капитанская дочка»",
                        "years", "1836",
                        "author", "Александр Пушкин",
                        "category", "Роман",
                        "descriptions",
                        "Первый бабник Петербурга, светский тусовщик, любитель шампанского, крепкого словца и дуэлей -\n" +
                            "вспоминая про «Наше всё», в голове у обывателя наверняка возникает образ этакой рокзвезды,\n" +
                            "гениального распиздяя, которому всё давалось легко и непринуждённо, этакого Марадоны от мира русской\n" +
                            "классики. Но гораздо меньше людей знают о другой стороне личности великого писателя: Пушкин был\n" +
                            "историческим гиком, скрупулёзно изучавшим тысячи архивных документов, чтобы составить своё\n" +
                            "представление о минувшей эпохе и выполнить поставленные перед собой творческие задачи. А во время\n" +
                            "работы над «Капитанской дочкой» он отправился на южный Урал (нехуёвая поездочка для начала XIX века\n" +
                            "!), дабы лично пообщаться со свидетелями исторических события, увидеть и почувствовать среду, в\n" +
                            "которой жили люди и подготовить материал для, пожалуй, главного шедевра в своей литературной карьере.",
                        "price", "1560 р."
                ),
                Map.of(
                        "title", "«Герой нашего времени»",
                        "years", "1840",
                        "author", "Михаил Лермонтов",
                        "category", "Роман",
                        "descriptions",
                        "Сюжет этой книги пересказать довольно непросто. Она представляет из себя деконструкцию романтической\n" +
                            "приключенческой повести и обладает передовой для своего времени структурой: нелинейностью,\n" +
                            "бессвязностью глав, сочетанием авторского повествования и «POV»-дневников от первого лица. Главный\n" +
                            "герой на страницах произведения даже «встречается» с самим Лермонтовым ! Интеллигентный, но циничный\n" +
                            "прапорщик Печорин по долгу службы и в поиске острых ощущений оказывается в разных живописных местах\n" +
                            "на юге России, но везде чувствует лишь тоску и бессмысленность своего существования…",
                        "price", "1560 р."
                ),
                Map.of(
                        "title", "«Обломов»",
                        "years", "1859",
                        "author", "Иван Гончаров",
                        "category", "Роман",
                        "descriptions",
                        "Лермонтовский образ Печорина был отнюдь не единственным вариантом «лишнего человека» в русской классике.\n" +
                            "Если его циничный офицер ещё пытался вести со своей судьбой торг, то персонаж, созданный писателем и\n" +
                            "литературным критиком Иваном Гончаровым в своём главном произведении, встречает читателя на стадии\n" +
                            "«принятия»",
                        "price", "1560 р."
                )
        );

        List<Map<String, Object>> sortedArticles = articles.stream()
                .sorted((a1, a2) -> {
                    String years1 = (String) a1.get("years");
                    String years2 = (String) a2.get("years");
                    return years1.compareTo(years2);
                })
                .collect(Collectors.toList());

        Iterable<BooksEntity> bookEntities = booksEntityRepository.findAll();
        List<BooksEntity> filteredBooks = StreamSupport.stream(bookEntities.spliterator(), false)
                .filter(book -> myBooksList.contains(book.getAuthor()))
                .sorted(Comparator.comparing(BooksEntity::getYears))
                .toList();

        model.addAttribute("myBooksList", myBooksList);
        model.addAttribute("articles", sortedArticles);
        model.addAttribute("books", filteredBooks);
        System.out.println(sortedArticles);

        return "my-books";
    }

    @GetMapping("/category/sort")
    public String sortByCategory(Model model) {

        List<String> myBooksList = subscribeService.getAllSubscribe();

        List<Map<String, Object>> articles = List.of(
                Map.of(
                        "title", "«Ревизор»",
                        "years", "1836",
                        "author", "Николай Гоголь",
                        "category", "Комедия",
                        "descriptions",
                        "Прокутивший всё состояние дворянин, у которого от блеска прошлой жизни остались только дорогие шмотки,\n" +
                            "приезжает в сонный провинциальной городок. Там местные чиновники и простые жители принимают парня за\n" +
                            "большую государственную шишку, ответственную за проверку состояния дел в городе («ревизора»), и начинают\n" +
                            "всячески его задабривать…",
                        "price", "1590 р."
                ),
                Map.of(
                        "title", "«Горе от ума»",
                        "years", "1825",
                        "author", "Александр Грибоедов",
                        "category", "Комедия",
                        "descriptions",
                        "Остроумная, лёгкая, виртуозно сочетающая смешное с грустным, эта стихотворная комедия повествует об\n" +
                            "инициативном и честном молодом человеке, который наведывается в имение к старым знакомым после\n" +
                            "продолжительной учёбы в Европе. Героя раздражает лицемерие и праздность московской тусовки, но в\n" +
                            "доме живёт любовь его юности, с которой он долгое время был разлучён и теперь снова желает сойтись.\n" +
                            "Но девушку не привлекает интеллект, искренность и дерзость парня по отношению к устоям, она «вросла»\n" +
                            "в светское общество и не желает расставаться с его блеском и пороками…",
                        "price", "2300 р."
                ),
                Map.of(
                        "title", "«Капитанская дочка»",
                        "years", "1836",
                        "author", "Александр Пушкин",
                        "category", "Роман",
                        "descriptions",
                        "Первый бабник Петербурга, светский тусовщик, любитель шампанского, крепкого словца и дуэлей -\n" +
                            "вспоминая про «Наше всё», в голове у обывателя наверняка возникает образ этакой рокзвезды,\n" +
                            "гениального распиздяя, которому всё давалось легко и непринуждённо, этакого Марадоны от мира русской\n" +
                            "классики. Но гораздо меньше людей знают о другой стороне личности великого писателя: Пушкин был\n" +
                            "историческим гиком, скрупулёзно изучавшим тысячи архивных документов, чтобы составить своё\n" +
                            "представление о минувшей эпохе и выполнить поставленные перед собой творческие задачи. А во время\n" +
                            "работы над «Капитанской дочкой» он отправился на южный Урал (нехуёвая поездочка для начала XIX века\n" +
                            "!), дабы лично пообщаться со свидетелями исторических события, увидеть и почувствовать среду, в\n" +
                            "которой жили люди и подготовить материал для, пожалуй, главного шедевра в своей литературной карьере.",
                        "price", "1560 р."
                ),
                Map.of(
                        "title", "«Герой нашего времени»",
                        "years", "1840",
                        "author", "Михаил Лермонтов",
                        "category", "Роман",
                        "descriptions",
                        "Сюжет этой книги пересказать довольно непросто. Она представляет из себя деконструкцию романтической\n" +
                            "приключенческой повести и обладает передовой для своего времени структурой: нелинейностью,\n" +
                            "бессвязностью глав, сочетанием авторского повествования и «POV»-дневников от первого лица. Главный\n" +
                            "герой на страницах произведения даже «встречается» с самим Лермонтовым ! Интеллигентный, но циничный\n" +
                            "прапорщик Печорин по долгу службы и в поиске острых ощущений оказывается в разных живописных местах\n" +
                            "на юге России, но везде чувствует лишь тоску и бессмысленность своего существования…",
                        "price", "1560 р."
                ),
                Map.of(
                        "title", "«Обломов»",
                        "years", "1859",
                        "author", "Иван Гончаров",
                        "category", "Роман",
                        "descriptions",
                        "Лермонтовский образ Печорина был отнюдь не единственным вариантом «лишнего человека» в русской классике.\n" +
                            "Если его циничный офицер ещё пытался вести со своей судьбой торг, то персонаж, созданный писателем и\n" +
                            "литературным критиком Иваном Гончаровым в своём главном произведении, встречает читателя на стадии\n" +
                            "«принятия»",
                        "price", "1560 р."
                )
        );

        List<Map<String, Object>> sortedArticles = articles.stream()
                .sorted((a1, a2) -> {
                    String category1 = (String) a1.get("category");
                    String category2 = (String) a2.get("category");
                    return category1.compareTo(category2);
                })
                .collect(Collectors.toList());

        Iterable<BooksEntity> bookEntities = booksEntityRepository.findAll();
        List<BooksEntity> filteredBooks = StreamSupport.stream(bookEntities.spliterator(), false)
                .filter(book -> myBooksList.contains(book.getAuthor()))
                .sorted(Comparator.comparing(BooksEntity::getCategory))
                .toList();

        model.addAttribute("myBooksList", myBooksList);
        model.addAttribute("articles", sortedArticles);
        model.addAttribute("books", filteredBooks);
        System.out.println(sortedArticles);

        return "my-books";
    }

    @PostMapping("/inBasket")
    public String subscribe(
            @RequestParam("userLogin") String userLogin,
            Model model) {

        subscribeService.addSubscribeObj(userLogin);
        return "redirect:allBooks";
    }
}