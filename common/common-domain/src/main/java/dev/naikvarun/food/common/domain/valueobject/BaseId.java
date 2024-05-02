package dev.naikvarun.food.common.domain.valueobject;

public abstract class BaseId<ID> {
    private final ID id;

    public BaseId(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }
}
