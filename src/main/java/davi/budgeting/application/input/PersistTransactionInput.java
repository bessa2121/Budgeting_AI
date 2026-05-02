package davi.budgeting.application.input;

import davi.budgeting.domain.Category;

public record PersistTransactionInput(String description, long amount, Category category) {
}
