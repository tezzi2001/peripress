package org.bondarenko;

import org.bondarenko.core.filter.Role;
import org.bondarenko.db.dao.impl.PublicationDaoImpl;
import org.bondarenko.db.dao.impl.PublishingHouseDaoImpl;
import org.bondarenko.db.dao.impl.UserDaoImpl;
import org.bondarenko.db.entity.Publication;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.db.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DaoTest {
    @Test
    void test() {
        User user = new User();
        user.setUsername("tezz");
        final byte[] bytes = new byte[10];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }
        user.setPassword(bytes);
        user.setEmail("tezzi@gmail.com");
        user.setRole(Role.ADMIN);
        new UserDaoImpl().save(user);

        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setTitle("title");
        publishingHouse.setDescription("description");
        publishingHouse.setMainImage("main image");
        publishingHouse.setPublisher(user);
        new PublishingHouseDaoImpl().save(publishingHouse);


        Publication publication = new Publication();
        publication.setPublishingHouse(publishingHouse);
        publication.setTitle("a title");
        publication.setContent("a content");
        publication.setMainImage("a main image");
        new PublicationDaoImpl().save(publication);

        Assertions.assertTrue(true);
    }
}
