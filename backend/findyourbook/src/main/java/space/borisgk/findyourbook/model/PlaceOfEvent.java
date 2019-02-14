package space.borisgk.findyourbook.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import space.borisgk.findyourbook.model.annatation.DBField;
import space.borisgk.findyourbook.model.annatation.DBTable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@DBTable(table = "library.place_of_event")
public class PlaceOfEvent implements Model {
    @DBField(constraint = "PRIMARY KEY")
    protected Integer id;
    @DBField
    protected String name;
}
