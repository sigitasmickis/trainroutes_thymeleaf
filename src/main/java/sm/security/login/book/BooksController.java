package sm.security.login.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class BooksController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/book/{bookId}")
    public String movie(@PathVariable Long bookId, Model model ) {
        ResponseEntity<Book> booksResponseEntity = new ResponseEntity<>(
                restTemplate.getForObject("http://localhost:8082/book/" + bookId, Book.class)
                , HttpStatus.OK);
        model.addAttribute("book", booksResponseEntity.getBody());


        return "books/itemView";
    }

    @GetMapping("/books")
    public String books(Model model) {
        ResponseEntity<Book[]> response = restTemplate.getForEntity("http://localhost:8082/books", Book[].class);
        List<Book> booksResponseEntity = Arrays.asList(response.getBody());
        model.addAttribute("books", booksResponseEntity);
        return "books/books";
    }

}
