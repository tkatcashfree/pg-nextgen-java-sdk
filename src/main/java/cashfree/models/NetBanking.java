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
@JsonTypeName("netbanking")
public class NetBanking implements PaymentMethod {

    @NonNull
    @JsonProperty("channel")
    final String channel = "link";

    @NonNull
    @JsonProperty("netbanking_bank_code")
    int netBankingCode;

}
