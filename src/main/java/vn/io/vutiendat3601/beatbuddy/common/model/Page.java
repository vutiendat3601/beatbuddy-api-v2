package vn.io.vutiendat3601.beatbuddy.common.model;

import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Page<T> {
  private final List<T> items;

  private final int page;

  private final int size;

  private final int totalPages;

  private final long totalItems;

  public <R> Page<R> map(Function<T, R> mapper) {
    final List<R> items = this.items.stream().map(mapper).toList();
    return new Page<>(items, page, size, totalPages, totalItems);
  }

  public static <R> Page<R> from(org.springframework.data.domain.Page<R> page) {
    return new Page<>(
        page.getContent(),
        page.getNumber() + 1,
        page.getSize(),
        page.getTotalPages(),
        page.getTotalElements());
  }
}
