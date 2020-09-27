package org.bondarenko;

import org.bondarenko.core.filter.Role;
import org.bondarenko.db.entity.Publication;
import org.bondarenko.db.entity.PublishingHouse;
import org.bondarenko.db.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DaoTest {
    @Test
    public void test() {
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setTitle("title");
        publishingHouse.setDescription("description");
        publishingHouse.setMainImage("main image");
        new org.bondarenko.db.dao.impl.PublishingHouseDaoImpl().save(publishingHouse);

        User user = new User();
        user.setUsername("tezz");
        final byte[] bytes = new byte[10];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }
        user.setPassword(bytes);
        user.setEmail("tezzi@gmail.com");
        user.setRole(Role.ADMIN);
        user.setSubscriptions(new ArrayList<PublishingHouse>() {{
            add(publishingHouse);
        }});
        new org.bondarenko.db.dao.impl.UserDaoImpl().save(user);

        Publication publication = new Publication();
        publication.setPublishingHouse(publishingHouse);
        publication.setTitle("a title");
        publication.setContent("a content");
        publication.setMainImage("a main image");
        new org.bondarenko.db.dao.impl.PublicationDaoImpl().save(publication);

        Assertions.assertTrue(true);
    }
}
