package sn.unchk.banckApi.service;





import com.bankapi.model.Account;
import com.bankapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> getBalance(String accountId) {
        return accountRepository.findById(accountId);
    }
}


