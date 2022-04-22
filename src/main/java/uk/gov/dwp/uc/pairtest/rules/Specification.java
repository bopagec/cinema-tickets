package uk.gov.dwp.uc.pairtest.rules;

import lombok.AllArgsConstructor;

import java.util.function.Predicate;

public interface Specification<T> extends Predicate<T> {
    Specification<T> and(Specification<T> other);

    Specification<T> or(Specification<T> other);
}

abstract class AbstractSpecification<T> implements Specification<T> {
    @Override
    public Specification<T> and(Specification<T> other) {
        return new AndSpecification<T>(this, other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return new OrSpecification<T>(this, other);
    }
}

@AllArgsConstructor
class AndSpecification<T> extends AbstractSpecification<T> {
    private Specification<T> first;
    private Specification<T> second;

    @Override
    public boolean test(T o) {
        return first.test(o) && second.test(o);
    }
}

@AllArgsConstructor
class OrSpecification<T> extends AbstractSpecification<T> {
    private Specification<T> first;
    private Specification<T> second;

    @Override
    public boolean test(T o) {
        return first.test(o) || second.test(o);
    }
}

