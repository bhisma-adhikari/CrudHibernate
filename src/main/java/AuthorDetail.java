import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "author_detail")
public class AuthorDetail extends BaseEntity {
    @Column(name = "genre")
    private String genre;

    @Column(name = "nationality")
    private String nationality;

    @OneToOne(mappedBy = "authorDetail")  // "authorDetail" is the name of property of Author class
    private Author author;

    public AuthorDetail() {
    }

    public AuthorDetail(String genre, String nationality, Author author) {
        this.genre = genre;
        this.nationality = nationality;
        this.author = author;
    }

    @Override
    public String toString() {
        return "AuthorDetail{" +
                "genre='" + genre + '\'' +
                ", nationality='" + nationality + '\'' +
                ", author.id=" + author.getId() +
                ", id=" + id +
                '}';
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
