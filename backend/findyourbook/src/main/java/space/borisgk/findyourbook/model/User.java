package space.borisgk.findyourbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import space.borisgk.findyourbook.model.annatation.DBField;
import space.borisgk.findyourbook.model.annatation.DBManyToMany;
import space.borisgk.findyourbook.model.annatation.DBTable;

import java.sql.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@DBTable(table = "fub.fub_user")
public class User implements Model {
    public User() {
    }

    @SneakyThrows
    public User(String login, String email, String hashpass) {
        this.login = login;
        this.email = email;
        this.hashpass = hashpass;
    }

    @DBField(constraint = "PRIMARY KEY")
    private Integer id = 1;
    @DBField()
    private String login;
    @DBField()
    String email;
    // используеться md5 алгоритм
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DBField()
    private String hashpass;
    // TODO как то обрабоатывать дефолтные значения
    @DBField()
    private String country = "ru";
    @DBField()
    private String gender = "male";
    @DBField()
    private Date born = new Date(0);
    @DBField()
    private Boolean news = true;

    @DBManyToMany(relationTable = "fub.user__favorite_book", firstColName = "fub_user", secondColName = "book")
    protected Integer[] favoriteBooks = new Integer[0];
    @DBManyToMany(relationTable = "fub.user__favorite_author", firstColName = "fub_user", secondColName = "author")
    protected Integer[] favoriteAuthors = new Integer[0];
}