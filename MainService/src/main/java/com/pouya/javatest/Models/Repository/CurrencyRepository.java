package com.pouya.javatest.Models.Repository;

import com.pouya.javatest.Models.Currency;
import com.pouya.javatest.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    @Query("select c from Currency c")
    List<Currency> getAll();
}
