package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatsDtoToGetStats {
    String start;
    String end;
    String[] uris;
    boolean unique;
    Integer from;
    Integer size;
}
