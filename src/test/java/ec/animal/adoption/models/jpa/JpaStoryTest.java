/*
    Copyright © 2018 Luisa Emme

    This file is part of Adoption Service in the Rescued Animals Platform.

    Adoption Service is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Adoption Service is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with Adoption Service.  If not, see <https://www.gnu.org/licenses/>.
 */

package ec.animal.adoption.models.jpa;

import ec.animal.adoption.builders.StoryBuilder;
import ec.animal.adoption.domain.Story;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class JpaStoryTest {

    @Test
    public void shouldGenerateAnUuidWhenCreatingAJpaStoryForAStoryWithNoUuid() {
        Story story = StoryBuilder.random().withUuid(null).build();
        JpaStory jpaStory = new JpaStory(story, mock(JpaAnimal.class));

        Story jpaStoryToStory = jpaStory.toStory();

        assertNotNull(jpaStoryToStory.getUuid());
    }

    @Test
    public void shouldGenerateARegistrationDateWhenCreatingAJpaStoryForAStoryWithNoRegistrationDate() {
        Story story = StoryBuilder.random().withRegistrationDate(null).build();
        JpaStory jpaStory = new JpaStory(story, mock(JpaAnimal.class));

        Story jpaStoryToStory = jpaStory.toStory();

        assertNotNull(jpaStoryToStory.getRegistrationDate());
    }

    @Test
    public void shouldCreateAStoryWithUuid() {
        UUID uuid = UUID.randomUUID();
        Story story = StoryBuilder.random().withUuid(uuid).build();
        JpaStory jpaStory = new JpaStory(story, mock(JpaAnimal.class));

        Story jpaStoryToStory = jpaStory.toStory();

        assertThat(jpaStoryToStory.getUuid(), is(uuid));
    }

    @Test
    public void shouldCreateAStoryWithRegistrationDate() {
        LocalDateTime registrationDate = LocalDateTime.now();
        Story story = StoryBuilder.random().withRegistrationDate(registrationDate).build();
        JpaStory jpaStory = new JpaStory(story, mock(JpaAnimal.class));

        Story jpaStoryToStory = jpaStory.toStory();

        assertThat(jpaStoryToStory.getRegistrationDate(), is(registrationDate));
    }

    @Test
    public void shouldCreateJpaStoryFromStory() {
        String text = randomAlphabetic(100);
        Story story = StoryBuilder.random().withText(text).build();
        JpaStory jpaStory = new JpaStory(story, mock(JpaAnimal.class));

        Story jpaStoryToStory = jpaStory.toStory();

        assertThat(jpaStoryToStory.getText(), is(story.getText()));
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void shouldVerifyEqualsAndHashCodeMethods() {
        EqualsVerifier.forClass(JpaStory.class).usingGetClass().withPrefabValues(
                Timestamp.class,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now().minusDays(2))
        ).withPrefabValues(JpaAnimal.class, mock(JpaAnimal.class), mock(JpaAnimal.class))
                .suppress(Warning.NONFINAL_FIELDS).verify();
    }
}