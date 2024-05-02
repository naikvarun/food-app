package dev.naikvarun.food.order.valueobject;

import java.util.Objects;

public class StreetAddress {
    private final String zip;
    private final String id;
    private final String street;
    private final String city;
    private final String state;

    public StreetAddress(String id, String street, String city, String state, String zip) {
        this.id = id;
        this.street = street;
        this.city = city    ;
        this.state = state;
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public String getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetAddress that = (StreetAddress) o;
        return Objects.equals(zip, that.zip) && Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zip, street, city, state);
    }
}
