package by.milavitsky.horseracing.entity;

import by.milavitsky.horseracing.entity.enumentity.PermissionEnum;

import java.io.Serializable;
import java.util.Set;

public class Role implements Serializable {
    private static final long serialVersionUID = 4404594547884224613L;

    private Long id;
    private String name;
    private Set<PermissionEnum> permissions;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PermissionEnum> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionEnum> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Role{");
        builder.append("id=").append(id);
        builder.append(", name='").append(name).append('\'');
        builder.append(", permissions=").append(permissions);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        if (id != null ? !id.equals(role.id) : role.id != null) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;
        return permissions != null ? permissions.equals(role.permissions) : role.permissions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (permissions != null ? permissions.hashCode() : 0);
        return result;
    }
}
