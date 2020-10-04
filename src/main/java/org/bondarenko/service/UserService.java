package org.bondarenko.service;

import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.core.sorting.SortingOptions;
import org.bondarenko.core.filtering.FilteringOptions;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<PublishingHouse> getPublishingHouses(int page);
    List<PublishingHouse> getAllPublishingHouses(FilteringOptions filteringOptions, SortingOptions sortingOptions);
    Optional<PublishingHouse> getByName(String name);
    void subscribe(HttpSession session, long id);
    void deposit(HttpSession session, int amount);
    List<PublishingHouse> getAllSubscriptions(HttpSession session);
    int getUserBalanceUsdById(long id);
}