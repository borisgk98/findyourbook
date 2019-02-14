package space.borisgk.findyourbook.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import space.borisgk.findyourbook.model.annatation.DBField;
import space.borisgk.findyourbook.model.annatation.DBManyToMany;
import space.borisgk.findyourbook.model.annatation.DBTable;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@DBTable(table = "library.author")
public class Author implements Model {

    @DBField
    protected String name;
    @DBField(constraint = "PRIMARY KEY")
    protected Integer id;
    @DBField
    protected String year_begin;
    @DBField
    protected String year_end;
    @DBField
    protected String biography;
    @DBField
    protected String img_url;

    @DBManyToMany(relationTable = "library.book__author", firstColName = "author", secondColName = "book")
    protected Integer[] books;
}
