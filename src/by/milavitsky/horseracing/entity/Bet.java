package by.milavitsky.horseracing.entity;

import by.milavitsky.horseracing.entity.enums.BetType;
import by.milavitsky.horseracing.entity.enums.TotalResultEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bet implements Serializable {

    private static final long serialVersionUID = -7442275899833735176L;

    private Long id;
    private BigDecimal amountBet;
    private BigDecimal ratio;
    private Long racesId;
    private String transferStatus;
    private LocalDateTime date;
    private Long userId;
    private Long horseId;
    private BetType betType;
    private Horse horse;
    private Race race;
    private TotalResultEnum resultStatus;

    public Bet() {
    }

    public Bet(Long id, BigDecimal amountBet, BigDecimal ratio, Long racesId, String transferStatus,
               LocalDateTime date, Long userId, Long horseId, BetType betType, Horse horse, Race race) {
        this.id = id;
        this.amountBet = amountBet;
        this.ratio = ratio;
        this.racesId = racesId;
        this.transferStatus = transferStatus;
        this.date = date;
        this.userId = userId;
        this.horseId = horseId;
        this.betType = betType;
        this.horse = horse;
        this.race = race;
    }

    public TotalResultEnum getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(TotalResultEnum resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmountBet() {
        return amountBet;
    }

    public void setAmountBet(BigDecimal amountBet) {
        this.amountBet = amountBet;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public Long getRacesId() {
        return racesId;
    }

    public void setRacesId(Long racesId) {
        this.racesId = racesId;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHorseId() {
        return horseId;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(id, bet.id) &&
                Objects.equals(amountBet, bet.amountBet) &&
                Objects.equals(ratio, bet.ratio) &&
                Objects.equals(racesId, bet.racesId) &&
                transferStatus == bet.transferStatus &&
                Objects.equals(date, bet.date) &&
                Objects.equals(userId, bet.userId) &&
                Objects.equals(horseId, bet.horseId)&&
                betType == bet.betType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amountBet, ratio, racesId, transferStatus, date, userId, horseId) * 21 - 123;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
        builder.append("id=").append(id);
        builder.append(", amountBet='").append(amountBet );
        builder.append(", coefficient='").append(ratio);
        builder.append(", passport='").append(racesId);
        builder.append(", transferStatus='").append(transferStatus);
        builder.append(", dateDeadline='").append(date);
        builder.append(", userId=").append(userId);
        builder.append(", horseId='").append(horseId);
        builder.append(", bet type='").append(betType);
        builder.append('}');
        return builder.toString();
    }
}
