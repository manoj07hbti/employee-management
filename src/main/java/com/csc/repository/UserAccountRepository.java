package com.csc.repository;


import com.csc.model.UserAccount;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long> {

}
