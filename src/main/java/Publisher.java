import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "publisher")
public class Publisher extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "estd")
    private Integer estd;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<>(); // initialize here to make sure this.getBooks() never returns null

    public Publisher() {
    }

    public Publisher(String name, Integer estd, Set<Book> books) {
        this.name = name;
        this.estd = estd;
        this.books = books != null ? books : new HashSet<>();
    }

    @Override
    public String toString() {
        List<Integer> bookIds = books.stream().map(book -> book.getId()).collect(Collectors.toList());
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", estd=" + estd +
                ", bookIds=" + bookIds +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEstd() {
        return estd;
    }

    public void setEstd(Integer estd) {
        this.estd = estd;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
