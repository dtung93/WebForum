package com.example.demo.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
public class PageDataOffset implements Pageable {
  private final int offset;
  private final int pageSize;
  private final Sort sort;

  public PageDataOffset(int offset, int pageSize, Sort sort) {
    this.offset = offset;
    this.pageSize = pageSize;
    this.sort = sort;
  }

  @Override
  public boolean isPaged() {
    return Pageable.super.isPaged();
  }

  @Override
  public boolean isUnpaged() {
    return Pageable.super.isUnpaged();
  }

  @Override
  public int getPageNumber() {
    return offset / pageSize;
  }

  @Override
  public int getPageSize() {
    return pageSize;
  }

  @Override
  public long getOffset() {
    return offset;
  }

  @Override
  public Sort getSort() {
    return sort;
  }

  @Override
  public Sort getSortOr(Sort sort) {
    return Pageable.super.getSortOr(sort);
  }

  @Override
  public Pageable next() {
    return new PageDataOffset((int) (getOffset() + getPageSize()), getPageSize(), getSort());
  }

  @Override
  public Pageable previousOrFirst() {
    int newOffset = (int) Math.max(getOffset() - getPageSize(), 0);
    return new PageDataOffset(newOffset, getPageSize(), getSort());
  }

  @Override
  public Pageable first() {
    return new PageDataOffset(0, getPageSize(), getSort());
  }

  @Override
  public Pageable withPage(int pageNumber) {
    return null;
  }

  @Override
  public boolean hasPrevious() {
    return offset > pageSize;
  }

  @Override
  public Optional<Pageable> toOptional() {
    return Pageable.super.toOptional();
  }
}
