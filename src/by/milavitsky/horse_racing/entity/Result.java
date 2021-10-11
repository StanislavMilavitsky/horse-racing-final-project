package by.milavitsky.horse_racing.entity;


import by.milavitsky.horse_racing.entity.enums.TotalResultEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Result implements Serializable {
    private static final long serialVersionUID = 4834209940652886210L;

    private Long id;
    private Long userId;
    private BigDecimal winingAmount;
    private Long betsId;
    private TotalResultEnum totalResult;

    public Result() {
    }

    public Result(Long id, Long userId, BigDecimal winingAmount, Long betsId, TotalResultEnum totalResult) {
        this.id = id;
        this.userId = userId;
        this.winingAmount = winingAmount;
        this.betsId = betsId;
        this.totalResult = totalResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getWiningAmount() {
        return winingAmount;
    }

    public void setWiningAmount(BigDecimal winingAmount) {
        this.winingAmount = winingAmount;
    }

    public Long getBetsId() {
        return betsId;
    }

    public void setBetsId(Long betsId) {
        this.betsId = betsId;
    }

    public TotalResultEnum getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(TotalResultEnum totalResult) {
        this.totalResult = totalResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(id, result.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id) * 14;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
        builder.append("id=").append(id);
        builder.append(", userId='").append(userId );
        builder.append(", winingAmount='").append(winingAmount);
        builder.append(", betsId='").append(betsId);
        builder.append(", totalResult='").append(totalResult);
        builder.append('}');
        return builder.toString();

    }
}
