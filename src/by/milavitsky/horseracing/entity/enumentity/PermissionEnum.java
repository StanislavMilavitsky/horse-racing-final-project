package by.milavitsky.horseracing.entity.enums;

public enum PermissionEnum {
    GUEST_BASIC,
    USER_BASIC,
    CUSTOMER_BASIC,
    ADMIN_BASIC,
    PLACE_BET,
    PLACE_RESULT,
    PLACE_RATIO,
    BAN_USER;

    public static PermissionEnum getPermission(String permission) {
        return PermissionEnum.valueOf(permission.toUpperCase());
    }
}
