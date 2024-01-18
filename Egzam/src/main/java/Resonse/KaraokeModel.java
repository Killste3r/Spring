package Resonse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KaraokeModel {

    public String name;

    @JsonProperty("key")
    @Id
    public String id;

}
