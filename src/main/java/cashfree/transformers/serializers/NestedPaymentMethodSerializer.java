package cashfree.transformers.serializers;

import cashfree.models.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class NestedPaymentMethodSerializer extends StdSerializer<PaymentMethod> {

    public NestedPaymentMethodSerializer() {
        this(null);
    }

    public NestedPaymentMethodSerializer(Class<PaymentMethod> t) {
        super(t);
    }

    @Override
    public void serialize(
            PaymentMethod paymentMethod, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        if(paymentMethod instanceof Card || paymentMethod instanceof CardEmi) {
            serializeToCard(paymentMethod, jgen, provider);
        } else if(paymentMethod instanceof NetBanking) {
            NetBanking nb = (NetBanking) paymentMethod;
            serializeToNetBanking(nb, jgen, provider);
        } else if(paymentMethod instanceof Upi) {
            serializeToUpiMethods(paymentMethod, jgen, provider);
        } else if(paymentMethod instanceof AppPayment) {
            AppPayment ap = (AppPayment) paymentMethod;
            serializeToAppPayment(ap, jgen, provider);
        }
    }

    public void serializeToCard(PaymentMethod pm, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if(pm instanceof Card) {
            jgen.writeObjectFieldStart("card");
            Card card = (Card) pm;
            if (card.getCardAlias() != null) {
                jgen.writeStringField("card_cvv", card.getCardCvv());
                jgen.writeStringField("card_alias", card.getCardAlias());
                jgen.writeStringField("channel", card.getChannel());
            } else {
                jgen.writeStringField("card_cvv", card.getCardCvv());
                jgen.writeStringField("card_number", card.getCardNumber());
                jgen.writeStringField("card_expiry_mm", card.getExpiryMonth());
                jgen.writeStringField("card_expiry_yy", card.getExpiryYear());
                jgen.writeStringField("channel", card.getChannel());

                if (card.getCardHolderName() != null)
                    jgen.writeStringField("card_holder_name", card.getCardHolderName());
            }
        } else {
            jgen.writeObjectFieldStart("emi");
            CardEmi cardEmi = (CardEmi) pm;
            jgen.writeStringField("card_cvv", cardEmi.getCardCvv());
            jgen.writeStringField("card_number", cardEmi.getCardNumber());
            jgen.writeStringField("card_expiry_mm", cardEmi.getExpiryMonth());
            jgen.writeStringField("card_expiry_yy", cardEmi.getExpiryYear());
            jgen.writeStringField("card_bank_name", cardEmi.getCardBankName());
            jgen.writeNumberField("emi_tenure", cardEmi.getEmiTenure());
            jgen.writeStringField("channel", cardEmi.getChannel());
        }
        jgen.writeEndObject();
        jgen.writeEndObject();
    }

    public void serializeToNetBanking(NetBanking nb, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeObjectFieldStart("netbanking");
        jgen.writeStringField("channel", nb.getChannel());
        jgen.writeNumberField("netbanking_bank_code", nb.getNetBankingCode());
        jgen.writeEndObject();
        jgen.writeEndObject();
    }

    public void serializeToUpiMethods(PaymentMethod upi, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeObjectFieldStart("upi");
        if(upi instanceof UpiCollect) {
            UpiCollect upiCollect = (UpiCollect) upi;
            jgen.writeStringField("channel", upiCollect.getChannel());
            jgen.writeStringField("upi_id", upiCollect.getUpiId());
        } else if(upi instanceof UpiLinks) {
            UpiLinks upiLinks = (UpiLinks) upi;
            jgen.writeStringField("channel", upiLinks.getChannel());
        } else if(upi instanceof UpiQrCode) {
            UpiQrCode upiQrCode = (UpiQrCode) upi;
            jgen.writeStringField("channel", upiQrCode.getChannel());
            jgen.writeBooleanField("add_invoice", upiQrCode.isAddInvoice());
        }
        jgen.writeEndObject();
        jgen.writeEndObject();
    }

    public void serializeToAppPayment(AppPayment ap, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeObjectFieldStart("app");
        jgen.writeStringField("channel", ap.getChannel().label);
        jgen.writeStringField("phone", ap.getPhone());
        jgen.writeEndObject();
        jgen.writeEndObject();
    }

}