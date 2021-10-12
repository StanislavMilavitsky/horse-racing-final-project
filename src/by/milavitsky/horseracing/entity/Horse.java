package by.milavitsky.horseracing.entity;


import by.milavitsky.horseracing.entity.enums.SexEnum;

import java.io.Serializable;
import java.util.Objects;

public class Horse implements Serializable {

    private static final long serialVersionUID = 7900753060569150881L;

    private Long id;
    private String name;
    private SexEnum sex;
    private Double weight;
    private String breed;
    private Integer age;
    private String status;
    private Double perсentageOfWins;
    private Integer participation;
    private String jockey;

    public Horse() {
    }

    public Horse(Long id, String name, SexEnum sex, Double weight, String breed, Integer age, String status,
                 Double perсentageOfWins, Integer participation, String jockey) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.weight = weight;
        this.breed = breed;
        this.age = age;
        this.status = status;
        this.perсentageOfWins = perсentageOfWins;
        this.participation = participation;
        this.jockey = jockey;
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

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPerсentageOfWins() {
        return perсentageOfWins;
    }

    public void setPerсentageOfWins(Double perсentageOfWins) {
        this.perсentageOfWins = perсentageOfWins;
    }

    public Integer getParticipation() {
        return participation;
    }

    public void setParticipation(Integer participation) {
        this.participation = participation;
    }

    public String getJockey() {
        return jockey;
    }

    public void setJockey(String jockey) {
        this.jockey = jockey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return Objects.equals(id, horse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id) * 17;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
        builder.append("id=").append(id);
        builder.append(", name='").append(name );
        builder.append(", sex='").append(sex);
        builder.append(", weight='").append(weight);
        builder.append(", breed='").append(breed);
        builder.append(", age='").append(age);
        builder.append(", status='").append(status);
        builder.append(", perсentageOfWins=").append(perсentageOfWins);
        builder.append(", participation='").append(participation);
        builder.append(", jockey='").append(jockey);
        builder.append('}');
        return builder.toString();
    }
}
