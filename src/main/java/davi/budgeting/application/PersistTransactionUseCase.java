package davi.budgeting.application;

import davi.budgeting.application.input.PersistTransactionInput;
import davi.budgeting.application.output.TransactionOutput;
import davi.budgeting.domain.Category;
import davi.budgeting.domain.Transaction;
import davi.budgeting.domain.TransactionRepository;

public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction execute(PersistTransactionInput input){
        var transaction = transactionRepository.save(
                new Transaction(input.description(),input.amount(),input.category()));

        return TransactionOutput.from(transaction);
    }
}
