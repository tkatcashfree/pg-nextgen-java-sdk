package cashfree.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("upi")
public class UpiQrCode implements Upi {

    @NonNull
    @JsonProperty("channel")
    final String channel = "qrcode";

    @JsonProperty("add_invoice")
    @Builder.Default
    boolean addInvoice = false;

}
