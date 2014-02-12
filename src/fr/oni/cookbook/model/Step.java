package fr.oni.cookbook.model;

import java.io.Serializable;

public class Step implements Serializable {
    private static final long serialVersionUID = 164965842297445634L;

    private String order;

    public Step(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return order;
    }
}
