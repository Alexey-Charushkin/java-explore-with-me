package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatsDtoToGetStats {
    String start;
    String end;
    List<String> uris;
    boolean unique;
    Integer from;
    Integer size;
}
