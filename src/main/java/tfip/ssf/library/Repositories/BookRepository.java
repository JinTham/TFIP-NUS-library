package tfip.ssf.library.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import tfip.ssf.library.Models.Book;

@Repository
public class BookRepository {
    
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void saveToRedis(Book book) {
        redisTemplate.opsForValue().set(book.getWorkId(), book);
    }

    public Book getBookById(String workId) {
        return (Book) redisTemplate.opsForValue().get(workId);
    }

}
