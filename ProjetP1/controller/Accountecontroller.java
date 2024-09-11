package sn.unchk.banckApi.controller;



import com.bankapi.model.Account;
import com.bankapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Account> getBalance(@PathVariable String accountId) {
        Optional<Account> account = accountService.getBalance(accountId);
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/{accountId}/transactions")
    public Page<Transaction> getTransactions(
            @PathVariable String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return transactionService.getTransactions(accountId, page, size);
    }


}

