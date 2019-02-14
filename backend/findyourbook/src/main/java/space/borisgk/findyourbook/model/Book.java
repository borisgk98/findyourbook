package space.borisgk.findyourbook.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import space.borisgk.findyourbook.model.annatation.DBField;
import space.borisgk.findyourbook.model.annatation.DBManyToMany;
import space.borisgk.findyourbook.model.annatation.DBTable;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@DBTable(table = "library.book")
public class Book implements Model {
    public Book() {
    }

    @DBField(constraint = "PRIMARY KEY")
    protected Integer id;
    @DBField
    protected String name;
    @DBField(field = "originalurl")
    protected String originalUrl;
    @DBField
    protected Integer year;
    @DBField
    protected String description;
    @DBField(field = "poster")
    protected Integer posterId;

    @DBManyToMany(relationTable = "library.book__tag", firstColName = "book", secondColName = "tag")
    protected Integer[] tags;
    @DBManyToMany(relationTable = "library.book__genre", firstColName = "book", secondColName = "genre")
    protected Integer[] genres;
    @DBManyToMany(relationTable = "library.book__time_of_event", firstColName = "book", secondColName = "time_of_event")
    protected Integer[] timeOfEvents;
    @DBManyToMany(relationTable = "library.book__place_of_event", firstColName = "book", secondColName = "place_of_event")
    protected Integer[] placeOfEvents;
    @DBManyToMany(relationTable = "library.book__protagonist", firstColName = "book", secondColName = "protagonist")
    protected Integer[] protagonists;
    @DBManyToMany(relationTable = "library.book__author", firstColName = "book", secondColName = "author")
    protected Integer[] authors;
}
