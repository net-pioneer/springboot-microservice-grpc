package com.pouya.javatest.Models.Service;

import com.pouya.javatest.Models.Currency;
import com.pouya.javatest.Models.Repository.CurrencyRepository;
import com.pouya.javatest.Models.Service.Interfaces.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    CurrencyRepository _repo;

    @Override
    public List<Currency> getCurrencies() {
        return _repo.getAll();
    }
}
