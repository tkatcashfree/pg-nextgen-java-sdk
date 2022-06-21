package cashfree.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.net.URL;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderInvoice {

    @NonNull
    @Pattern(regexp = "^[0-9]*$", message = "numeric string only")
    String gst;

    @NonNull
    String gstin;

    @NonNull
    Date invoiceDate;

    @NonNull
    String invoiceNumber;

    URL invoiceLink;

    @NonNull
    String invoiceName;

    @Pattern(regexp = "^[0-9]*$", message = "numeric string only")
    String cgst;

    @Pattern(regexp = "^[0-9]*$", message = "numeric string only")
    String sgst;

    @Pattern(regexp = "^[0-9]*$", message = "numeric string only")
    String igst;

    @Pattern(regexp = "^[0-9]*$", message = "numeric string only")
    String cess;

    @Pattern(regexp = "^[0-9]*$", message = "numeric string only")
    String gstIncentive;

    @Pattern(regexp = "^[0-9]*$", message = "numeric string only")
    String gstPercentage;

    @Pattern(regexp = "^[0-9]*$", message = "numeric 6 digit string only")
    @Size(min = 6, max = 6, message = "numeric 6 digit string only")
    String pincode;

    CityTier cityTier;
}
