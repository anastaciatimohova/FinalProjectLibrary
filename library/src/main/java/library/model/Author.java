package library.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Author {
    private Long id;
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
