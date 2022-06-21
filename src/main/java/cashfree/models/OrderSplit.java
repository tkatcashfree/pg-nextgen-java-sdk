package cashfree.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Positive;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderSplit {
    @NonNull
    @JsonProperty("vendor_id")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "vendor Id should be alphanumeric with only _ allowed")
    String vendorId;

    @JsonProperty("amount")
    @Builder.Default
    @DecimalMin(value = "1", message = "split amount should be greater than 1")
    @Digits(integer = 100, fraction = 2, message = "split amount can have at max 2 decimal places")
    BigDecimal amount = null;

    @JsonProperty("percentage")
    @Builder.Default
    @DecimalMin(value = "1", message = "split percentage should be greater than 1")
    @DecimalMax(value = "100", message = "split percentage should be less than 100")
    @Digits(integer = 3, fraction = 2, message = "split amount can have at max 2 decimal places")
    BigDecimal percentage = null;
}
