package tfip.ssf.library.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tfip.ssf.library.Models.Book;
import tfip.ssf.library.Services.BookService;

@Controller
@RequestMapping
public class BookController {

    @Autowired
    private BookService bookSvc;

    @GetMapping(path="book/{workId}")
    public String getBook(@PathVariable String workId, Model model) {
        Book book = bookSvc.getBookById(workId);
        if (book == null){
            Optional<Book> opt = bookSvc.getBook(workId);
            book = opt.get();
            book.setCached(false);
            bookSvc.saveToRedis(book);
        } else {
            book.setCached(true);
        }
        model.addAttribute("book", book);
        return "book";
    }
}
