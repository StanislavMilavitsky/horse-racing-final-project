package by.milavitsky.horseracing.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class  Race implements Serializable {
    private static final long serialVersionUID = 4834209940652886210L;

    private Long id;
    private String raceType;
    private LocalDateTime date;
    private String hippodrome;
    private Set<Long> horse;
    private Long betCount;
    private BigDecimal betCountCash;

    public Race() {
    }

    public Race(Long id, String hippodrome, String raceTyp, Set<Long> horse, LocalDateTime date) {
        this.id = id;
        this.hippodrome = hippodrome;
        this.raceType = raceTyp;
        this.horse = horse;
        this.date = date;
    }

    public Race(Long id, String hippodrome, String raceType, LocalDateTime date) {
        this.id = id;
        this.hippodrome = hippodrome;
        this.raceType = raceType;
        this.date = date;
    }


    public Set<Long> getHorse() {
        return horse;
    }

    public void setHorse(Set<Long> horse) {
        this.horse = horse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getHippodrome() {
        return hippodrome;
    }

    public void setHippodrome(String hippodrome) {
        this.hippodrome = hippodrome;
    }

    public String getRaceType() {
        return raceType;
    }

    public void setRaceType(String raceType) {
        this.raceType = raceType;
    }

    public Long getBetCount() {
        return betCount;
    }

    public void setBetCount(Long betCount) {
        this.betCount = betCount;
    }

    public BigDecimal getBetCountCash() {
        return betCountCash;
    }

    public void setBetCountCash(BigDecimal betCountCash) {
        this.betCountCash = betCountCash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return Objects.equals(id, race.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id) * 23;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
        builder.append("id=").append(id);
        builder.append(", hippodromeName='").append(hippodrome);
        builder.append(", raceTyp='").append(raceType);
        builder.append(", horse='").append(horse);
        builder.append(", date='").append(date);
        builder.append(", betCount='").append(betCount);
        builder.append(", betCountCash='").append(betCountCash);
        builder.append('}');
        return builder.toString();
    }
}
