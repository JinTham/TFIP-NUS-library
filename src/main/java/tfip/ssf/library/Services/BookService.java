package tfip.ssf.library.Services;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import tfip.ssf.library.Models.Book;
import tfip.ssf.library.Models.searchResult;
import tfip.ssf.library.Repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    public void saveToRedis(Book book) {
        bookRepo.saveToRedis(book);
    }

    public Book getBookById(String workId) {
        Book book = bookRepo.getBookById(workId);
        return book;
    }

    public Optional<searchResult> search(String searchTerm){

        //Send request entity to News API website
        //https://openlibrary.org/search.json?title={searchTerm}&limit={int}
        String url = UriComponentsBuilder.fromUriString("https://openlibrary.org/search.json")
                .queryParam("title",searchTerm.replace(" ","+"))
                .queryParam("limit",20)
                .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();

        //Receive response entity from News API website
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        String payload = "";
        int statusCode = 500;
        try {
            resp = template.exchange(req, String.class);
            payload = resp.getBody();
            statusCode = resp.getStatusCode().value();
        } catch (HttpClientErrorException ex) {
            payload = ex.getResponseBodyAsString();
            statusCode = ex.getStatusCode().value();
            return null;
        } finally {
            System.out.printf("Status code: %d\n", statusCode);
            //System.out.printf("Payload: %s\n", payload);
        }

        //Read the content from the JsonObject
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject json = reader.readObject();

        //Parse the JsonObject into local object
        searchResult searchResult = new searchResult(searchTerm);
        searchResult.addJson(json);

        return Optional.of(searchResult);
    }

    public Optional<Book> getBook(String workId){

        //Send request entity to News API website
        //https://openlibrary.org/works/{workId}.json
        String url = UriComponentsBuilder.fromUriString("https://openlibrary.org/works/")
                .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url+workId+".json").accept(MediaType.APPLICATION_JSON).build();

        //Receive response entity from News API website
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        String payload = "";
        int statusCode = 500;
        try {
            resp = template.exchange(req, String.class);
            payload = resp.getBody();
            statusCode = resp.getStatusCode().value();
        } catch (HttpClientErrorException ex) {
            payload = ex.getResponseBodyAsString();
            statusCode = ex.getStatusCode().value();
            return null;
        } finally {
            System.out.printf("Status code: %d\n", statusCode);
            //System.out.printf("Payload: %s\n", payload);
        }

        //Read the content from the JsonObject
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject json = reader.readObject();

        //Parse the JsonObject into local object
        Book book = new Book(workId);
        book.addJson(json);

        return Optional.of(book);
    }

}
