package com.tindy.app.model.entity;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class UserContactPK {
    private Integer user;
    private Integer contact;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserContactPK)) return false;
        UserContactPK that = (UserContactPK) o;
        return Objects.equals(user, that.user) && Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, contact);
    }
}
