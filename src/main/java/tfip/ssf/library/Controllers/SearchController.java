package tfip.ssf.library.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tfip.ssf.library.Models.searchResult;
import tfip.ssf.library.Services.BookService;

@Controller
@RequestMapping
public class SearchController {
    
    @Autowired
    private BookService bookSvc;

    @PostMapping(path="/book")
    public String searchBooks(@RequestBody MultiValueMap<String,String> form, Model model) {
        String searchTerm = form.getFirst("bookTitle");
        Optional<searchResult> opt= bookSvc.search(searchTerm);
        searchResult searchResult = opt.get();
        model.addAttribute("result", searchResult);
        return "result";
    }

}
