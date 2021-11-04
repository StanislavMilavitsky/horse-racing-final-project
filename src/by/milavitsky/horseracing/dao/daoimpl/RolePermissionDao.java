package by.milavitsky.horseracing.dao.daoimpl;

import by.milavitsky.horseracing.dao.daoabstract.RolePermissionsDaoAbstract;
import by.milavitsky.horseracing.dao.pool.ConnectionManager;
import by.milavitsky.horseracing.entity.Role;
import by.milavitsky.horseracing.entity.enumentity.PermissionEnum;
import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class RolePermissionDao extends RolePermissionsDaoAbstract {
    private static final Logger logger = LogManager.getLogger(RolePermissionDao.class);

    private static final String ROLE_PERMISSIONS_SQL = "SELECT r.id, r.name, p.name FROM roles r JOIN role_permissions rp ON rp.role_id = r.id " +
                    "JOIN permissions p ON rp.permission_id = p.id";

    private RolePermissionDao() {
    }

    @Override
    public List<Role> findAll() throws DaoException {
        List<Role> roles = new ArrayList<>();
        try(var proxyConnection = ConnectionManager.get();
            var statement = proxyConnection.createStatement()) {
             var resultSet = statement.executeQuery(ROLE_PERMISSIONS_SQL);
            while (resultSet.next()) {
                Long roleId = resultSet.getLong("r.id");
                String roleName = resultSet.getString("r.name");
                String permissionName = resultSet.getString("p.name");
                PermissionEnum permission = PermissionEnum.getPermission(permissionName);
                int roleIndex = roleExist(roles, roleId);
                if (roleIndex < 0) {
                    Role role = new Role();
                    role.setId(roleId);
                    role.setName(roleName);
                    Set<PermissionEnum> permissions = EnumSet.of(permission);
                    role.setPermissions(permissions);
                    roles.add(role);
                } else {
                    Role role = roles.get(roleIndex);
                    role.getPermissions().add(permission);
                }
            }
            return roles;
        } catch (SQLException e) {
            logger.error("Role permissions dao fail!", e);
            throw new DaoException("Role permissions dao fail!", e);
        }
    }

    private int roleExist(List<Role> roles, Long roleId) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getId().equals(roleId)) {
                return i;
            }
        }
        return -1;
    }

    private static class RolePermissionDaoHolder{
        private static final RolePermissionDao HOLDER_INSTANCE = new RolePermissionDao();
    }

    public static RolePermissionDao getInstance() {
        return RolePermissionDaoHolder.HOLDER_INSTANCE;
    }
}
