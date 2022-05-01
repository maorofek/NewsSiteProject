package iob.boundary.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Data includes equals, hashCode, getters, setters, toString
@AllArgsConstructor
@NoArgsConstructor
public class InstanceID {
    private String domain;
    private String id;
}
