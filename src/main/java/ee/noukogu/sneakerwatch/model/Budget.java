package ee.noukogu.sneakerwatch.model;

import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

@Data
public class Budget {
    public int start;
    public int end;

    public Budget() {
        this.start = ThreadLocalRandom.current().nextInt(60, 100 + 1);
        this.end = ThreadLocalRandom.current().nextInt(start, 150 + 1);
    }

    public boolean allows(int price) {
        return price >= start && price <= end;
    }

    public boolean priceIsInBudget(Integer price) {
        return this.start <= price && this.end >= price;
    }
}
