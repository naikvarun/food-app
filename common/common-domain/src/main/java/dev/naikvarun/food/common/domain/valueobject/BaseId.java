package dev.naikvarun.food.common.domain.valueobject;

public abstract class BaseId<ID> {
    private final ID value;

    public BaseId(ID value) {
        this.value = value;
    }

    public ID getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
