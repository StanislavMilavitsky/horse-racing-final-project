package by.milavitsky.horse_racing.entity;

import by.milavitsky.horse_racing.entity.enums.BetType;
import by.milavitsky.horse_racing.entity.enums.TransferStatusEnum;

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
    private TransferStatusEnum transferStatus;
    private LocalDateTime time;
    private Long userId;
    private Long horseId;
    private BetType betType;
    private Horse horse;
    private Race race;
    private Long betTypeId;

    public Bet() {
    }


    public Bet(Long id, BigDecimal amountBet, BigDecimal ratio, Long racesId, TransferStatusEnum transferStatus,
               LocalDateTime time, Long userId, Long horseId, BetType betType, Horse horse, Race race) {
        this.id = id;
        this.amountBet = amountBet;
        this.ratio = ratio;
        this.racesId = racesId;
        this.transferStatus = transferStatus;
        this.time = time;
        this.userId = userId;
        this.horseId = horseId;
        this.betType = betType;
        this.horse = horse;
        this.race = race;
    }

    public Bet(Long id, BigDecimal amountBet, BigDecimal ratio, Long racesId, TransferStatusEnum transferStatus,
               LocalDateTime time, Long userId, Long horseId, BetType betType) {
        this.id = id;
        this.amountBet = amountBet;
        this.ratio = ratio;
        this.racesId = racesId;
        this.transferStatus = transferStatus;
        this.time = time;
        this.userId = userId;
        this.horseId = horseId;
        this.betType = betType;
    }

    public Bet(Long id, BigDecimal amountBet, BigDecimal ratio, Long racesId, TransferStatusEnum transferStatus,
               LocalDateTime time, Long userId, Long horseId, Horse horse, Race race) {
        this.id = id;
        this.amountBet = amountBet;
        this.ratio = ratio;
        this.racesId = racesId;
        this.transferStatus = transferStatus;
        this.time = time;
        this.userId = userId;
        this.horseId = horseId;
        this.horse = horse;
        this.race = race;

    }

    public Bet(Long id, BigDecimal amountBet, BigDecimal ratio, Long racesId, TransferStatusEnum transferStatus,
               LocalDateTime time, Long userId, Long horseId, Horse horse, Race race, BetType betType) {
        this.id = id;
        this.amountBet = amountBet;
        this.ratio = ratio;
        this.racesId = racesId;
        this.transferStatus = transferStatus;
        this.time = time;
        this.userId = userId;
        this.horseId = horseId;
        this.horse = horse;
        this.race = race;
        this.betType = betType;

    }

    public Long getBetTypeId() {
        return betTypeId;
    }

    public void setBetTypeId(Long betTypeId) {
        this.betTypeId = betTypeId;
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

    public TransferStatusEnum getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatusEnum transferStatus) {
        this.transferStatus = transferStatus;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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
                Objects.equals(time, bet.time) &&
                Objects.equals(userId, bet.userId) &&
                Objects.equals(horseId, bet.horseId)&&
                betType == bet.betType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amountBet, ratio, racesId, transferStatus, time, userId, horseId) * 21 - 123;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
        builder.append("id=").append(id);
        builder.append(", amountBet='").append(amountBet );
        builder.append(", coefficient='").append(ratio);
        builder.append(", passport='").append(racesId);
        builder.append(", transferStatus='").append(transferStatus);
        builder.append(", dateDeadline='").append(time);
        builder.append(", userId=").append(userId);
        builder.append(", horseId='").append(horseId);
        builder.append(", bet type='").append(betType);
        builder.append('}');
        return builder.toString();
    }
}
