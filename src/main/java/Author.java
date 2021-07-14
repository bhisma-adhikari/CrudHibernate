import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "author")
public class Author extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_detail_id", referencedColumnName = "id", nullable = false)
    private AuthorDetail authorDetail;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "author_book",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private Set<Book> books = new HashSet<>(); // initialize here to make sure this.getBooks() never returns null

    public Author() {
    }

    public Author(String firstName, String lastName, AuthorDetail authorDetail, Set<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorDetail = authorDetail;
        this.books = books != null ? books : new HashSet<>();
    }

    @Override
    public String toString() {
        List<Integer> bookIds = books.stream().map(book -> book.getId()).collect(Collectors.toList());
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", authorDetail.id=" + authorDetail.getId() +
                ", id=" + id +
                ", book.ids=" + bookIds +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AuthorDetail getAuthorDetail() {
        return authorDetail;
    }

    public void setAuthorDetail(AuthorDetail authorDetail) {
        this.authorDetail = authorDetail;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
