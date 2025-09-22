package entidades;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@SuperBuilder
public class UnidadMedida {
    private Long id;
    private String denominacion;
}
