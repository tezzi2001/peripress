package org.bondarenko.service;

import org.bondarenko.core.filtering.FilteringOptions;
import org.bondarenko.core.sorting.SortingOptions;
import org.bondarenko.db.entity.PublishingHouse;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<PublishingHouse> getPublishingHouses(int page);
    List<PublishingHouse> getAllPublishingHouses(FilteringOptions filteringOptions, SortingOptions sortingOptions, int page);
    Optional<PublishingHouse> getByName(String name);
    void subscribe(HttpSession session, long id);
    void deposit(HttpSession session, int amount);
    List<PublishingHouse> getAllSubscriptions(HttpSession session);
    int getUserBalanceUsdById(long id);
}
