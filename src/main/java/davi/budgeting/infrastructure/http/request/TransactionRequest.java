package davi.budgeting.infrastructure.http.request;

import davi.budgeting.application.input.PersistTransactionInput;
import davi.budgeting.domain.Category;

public record TransactionRequest(String description, Category category, long amount) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description, amount, category);
    }
}