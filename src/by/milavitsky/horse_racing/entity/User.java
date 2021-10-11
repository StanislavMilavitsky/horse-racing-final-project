package by.milavitsky.horse_racing.entity;


import by.milavitsky.horse_racing.entity.enums.PermissionEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 4834209940652886210L;

    private Long id;
    private String name;
    private String passport;
    private String email;
    private String phone;
    private String  bankAccount;
    private String  password;
    private String status;
    private String nickname;
    private BigDecimal persentageOfWin;
    private String avatar;
    private LocalDateTime dateOfRegister;
    private Integer rates;
    private String surname;
    private Role role;
    private BigDecimal cash;

    public User() {
    }

    public User(Long id, String name, String passport, String email, String phone, String bankAccount, String password,
                String status, String nickname, BigDecimal persentageOfWin, String avatar, LocalDateTime dateOfRegister, Integer rates, String surname, Role role, BigDecimal cash) {
        this.id = id;
        this.name = name;
        this.passport = passport;
        this.email = email;
        this.phone = phone;
        this.bankAccount = bankAccount;
        this.password = password;
        this.status = status;
        this.nickname = nickname;
        this.persentageOfWin = persentageOfWin;
        this.avatar = avatar;
        this.dateOfRegister = dateOfRegister;
        this.rates = rates;
        this.surname = surname;
        this.role = role;
        this.cash = cash;
    }

    public User(String name, String surname, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPersentageOfWin(BigDecimal persentageOfWin) {
        this.persentageOfWin = persentageOfWin;
    }


    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDateOfRegister(LocalDateTime dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }

    public void setRates(Integer rates) {
        this.rates = rates;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassport() {
        return passport;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getNickname() {
        return nickname;
    }

    public BigDecimal getPersentageOfWin() {
        return persentageOfWin;
    }

    public String getAvatar() {
        return avatar;
    }

    public LocalDateTime getDateOfRegister() {
        return dateOfRegister;
    }

    public Integer getRates() {
        return rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
            builder.append("id=").append(id);
            builder.append(", surname='").append(surname );
            builder.append(", name='").append(name);
            builder.append(", passport='").append(passport);
            builder.append(", email='").append(email);
            builder.append(", phone='").append(phone);
            builder.append(", bankAccount='").append(bankAccount);
            builder.append(", role=").append(role);
            builder.append(", avatar='").append(avatar);
            builder.append(", dateOfRegister=").append(dateOfRegister);
            builder.append(", rates=").append(rates);
            builder.append(", status='").append(status);
            builder.append(", nickname='").append(nickname);
            builder.append(", persentageOfWin=").append(persentageOfWin);
            builder.append(", cash=").append(cash);
            builder.append('}');
            return builder.toString();
    }
}
