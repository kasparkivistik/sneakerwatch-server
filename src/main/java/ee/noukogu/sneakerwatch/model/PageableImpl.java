package ee.noukogu.sneakerwatch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableImpl implements Pageable {

    private int pageSize;
    private Sort sort;
    private int pageNumber;
    private int offset;

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public long getOffset() {
       return this.offset;
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }

    @Override
    public Pageable next() {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public Pageable previousOrFirst() {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public Pageable first() {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public boolean hasPrevious() {
        throw new IllegalArgumentException("Not implemented");
    }
}
