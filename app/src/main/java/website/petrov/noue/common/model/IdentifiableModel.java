package website.petrov.noue.common.model;

import org.jetbrains.annotations.Contract;

public abstract class IdentifiableModel implements Model {
    protected Integer id;

    @Contract(pure = true)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
