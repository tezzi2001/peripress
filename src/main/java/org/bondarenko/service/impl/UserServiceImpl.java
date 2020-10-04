package org.bondarenko.service.impl;

import org.bondarenko.db.dao.PublishingHouseDao;
import org.bondarenko.db.dao.UserDao;
import org.bondarenko.db.dao.impl.PublishingHouseDaoImpl;
import org.bondarenko.core.filtering.FilteringOptions;
import org.bondarenko.db.dao.impl.UserDaoImpl;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.core.sorting.SortingOptions;
import org.bondarenko.db.entity.User;
import org.bondarenko.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static org.bondarenko.constant.Session.USER_ID;

public class UserServiceImpl implements UserService {
    private final PublishingHouseDao publishingHouseDao;
    private final UserDao userDao;
    private static final int NUMBER_OF_PUBLISHING_HOUSES_ON_PAGE = 4;

    public UserServiceImpl() {
        this.publishingHouseDao = new PublishingHouseDaoImpl();
        userDao = new UserDaoImpl();
    }

    @Override
    public List<PublishingHouse> getPublishingHouses(int page) {
        int offset = (page - 1) * NUMBER_OF_PUBLISHING_HOUSES_ON_PAGE;
        return publishingHouseDao.findAllLimit(offset, NUMBER_OF_PUBLISHING_HOUSES_ON_PAGE);
    }

    @Override
    public List<PublishingHouse> getAllPublishingHouses(FilteringOptions filteringOptions, SortingOptions sortingOptions, int page) {
        List<PublishingHouse> publishingHouses = publishingHouseDao.findAll();
        publishingHouses = sort(sortingOptions, publishingHouses);
        publishingHouses = filter(filteringOptions, publishingHouses);
        int offset = (page - 1) * NUMBER_OF_PUBLISHING_HOUSES_ON_PAGE;
        int toIndex = offset + NUMBER_OF_PUBLISHING_HOUSES_ON_PAGE;
        if (offset > publishingHouses.size()) {
            return new ArrayList<>();
        }
        if (toIndex > publishingHouses.size()) {
            toIndex = publishingHouses.size();
        }
        return publishingHouses.subList(offset, toIndex);
    }

    @Override
    public Optional<PublishingHouse> getByName(String title) {
        return publishingHouseDao.findByTitle(title);
    }

    @Override
    public void subscribe(HttpSession session, long id) {
        long userId = (long) session.getAttribute(USER_ID);
        Optional<User> userOptional = userDao.find(userId);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            return;
        }

        Optional<PublishingHouse> publishingHouseOptional = publishingHouseDao.find(id);
        PublishingHouse publishingHouse;
        if (publishingHouseOptional.isPresent()) {
            publishingHouse = publishingHouseOptional.get();
        } else {
            return;
        }

        int difference = user.getBalance() - publishingHouse.getSubscriptionPriceUsd();
        if (difference < 0) {
            return;
        }
        user.setBalance(difference);
        List<PublishingHouse> subscriptions = user.getSubscriptions();
        subscriptions.add(publishingHouse);
        user.setSubscriptions(subscriptions);

        userDao.save(user);
    }

    @Override
    public void deposit(HttpSession session, int amount) {
        long userId = (long) session.getAttribute(USER_ID);
        Optional<User> userOptional = userDao.find(userId);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            return;
        }

        int balance = user.getBalance();
        balance += amount;
        user.setBalance(balance);

        userDao.save(user);
    }

    @Override
    public List<PublishingHouse> getAllSubscriptions(HttpSession session) {
        long userId = (long) session.getAttribute(USER_ID);
        Optional<User> userOptional = userDao.find(userId);

        if (userOptional.isPresent()) {
            return userOptional.get().getSubscriptions();
        }

        return new ArrayList<>();
    }

    @Override
    public int getUserBalanceUsdById(long id) {
        Optional<User> userOptional = userDao.find(id);
        return userOptional.map(User::getBalance).orElse(0);
    }

    private List<PublishingHouse> sort(SortingOptions sortingOptions, List<PublishingHouse> publishingHouses) {
        List<PublishingHouse> preSortedPublishingHouses = internalSort(sortingOptions, publishingHouses);
        if (sortingOptions.isDescending()) {
            Collections.reverse(preSortedPublishingHouses);
        }
        return preSortedPublishingHouses;
    }

    private List<PublishingHouse> internalSort(SortingOptions sortingOptions, List<PublishingHouse> publishingHouses) {
        switch (sortingOptions.getSortingOption()) {
            case BY_NAME:
                return publishingHouses.stream()
                        .sorted(Comparator.comparing(PublishingHouse::getName))
                        .collect(Collectors.toList());
            case BY_PRICE:
                return publishingHouses.stream()
                        .sorted(Comparator.comparing(PublishingHouse::getSubscriptionPriceUsd))
                        .collect(Collectors.toList());
            case BY_SUBSCRIPTIONS:
                return publishingHouses.stream()
                        .sorted(Comparator.comparing(PublishingHouse::getSubscriptsCount))
                        .collect(Collectors.toList());
            default: return publishingHouses;
        }
    }

    private List<PublishingHouse> filter(FilteringOptions filteringOptions, List<PublishingHouse> publishingHouses) {
        return publishingHouses.stream()
                .filter(publishingHouse -> filteringOptions.getThemes().isEmpty() || filteringOptions.getThemes().contains(publishingHouse.getTheme()))
                .filter(publishingHouse -> filteringOptions.getTypes().isEmpty() || filteringOptions.getTypes().contains(publishingHouse.getType()))
                .filter(publishingHouse -> {
                    if (filteringOptions.isFree()) {
                        return publishingHouse.getSubscriptionPriceUsd() == 0;
                    } else {
                        return true;
                    }
                }).collect(Collectors.toList());
    }
}
