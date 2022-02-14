package dev.peertosir.storagecrm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StorageAddItemRequestTo {
    @NotNull
    @Positive(message = "Amount should be positive")
    private final Integer amount;

    @JsonCreator
    public StorageAddItemRequestTo(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageAddItemRequestTo that = (StorageAddItemRequestTo) o;

        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
