import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
public class Book extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private Set<Author> authors = new HashSet<>();  // initialize here to make sure this.getAuthors() never returns null

    @ManyToOne()
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Publisher publisher;

    public Book() {
    }

    public Book(String name, Double price, Set<Author> authors, Publisher publisher) {
        this.name = name;
        this.price = price;
        this.authors = authors != null ? authors : new HashSet<>();
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        List<Integer> authorIds = authors.stream().map(author -> author.getId()).collect(Collectors.toList());
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", authorIds=" + authorIds +
                ", publisher=" + publisher.getName() +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
