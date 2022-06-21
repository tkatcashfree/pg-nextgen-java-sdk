package cashfree.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("upi")
public class UpiCollect implements Upi {

    @NonNull
    @JsonProperty("channel")
    final String channel = "collect";

    @NonNull
    @JsonProperty("upi_id")
    String upiId;

}
