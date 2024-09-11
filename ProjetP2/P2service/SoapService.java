package sn.unchk.banckApi.P2service;

package sn.unchk.banckApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.unchk.banckApi.model.Account;
import sn.unchk.banckApi.model.Transaction;
import sn.unchk.banckApi.repository.AccountRepository;
import sn.unchk.banckApi.repository.TransactionRepository;

import java.util.List;

@Service
public class SoapService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public double getBalance(String accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    public List<Transaction> getTransactions(String accountId, int pageNumber, int pageSize) {
        return transactionRepository.findByAccountId(accountId, PageRequest.of(pageNumber, pageSize)).getContent();
    }

    public void transferFunds(String creditor, String debtor, double amount, String currency) {
        Account creditorAccount = accountRepository.findById(creditor).orElseThrow(() -> new RuntimeException("Creditor not found"));
        Account debtorAccount = accountRepository.findById(debtor).orElseThrow(() -> new RuntimeException("Debtor not found"));

        creditorAccount.setBalance(creditorAccount.getBalance() - amount);
        debtorAccount.setBalance(debtorAccount.getBalance() + amount);

        accountRepository.save(creditorAccount);
        accountRepository.save(debtorAccount);

        // Enregistrer la transaction
        Transaction transaction = new Transaction(null, amount, currency, "date", debtorAccount);
        transactionRepository.save(transaction);
    }
}

