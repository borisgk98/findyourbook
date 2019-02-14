package space.borisgk.findyourbook.controler.api;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@Builder
public class RestAnswer {
    protected String messange, errorType;
    protected Exception exception;
    protected Object value;
}
