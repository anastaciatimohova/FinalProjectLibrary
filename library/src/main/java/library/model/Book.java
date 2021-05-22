package library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Book {
    private long id;
    private String title;
    private Author author;
    private Genre genre;
    private String ISBN;

    @Override
    public String toString() {
        return "Информация о книге \"" + title + "\":" + " id - " + id + ", автор - " + author.getName() + ", жанр - " + genre + ", ISBN - " + ISBN;
    }
}
