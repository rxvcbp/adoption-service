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

import ec.animal.adoption.builders.LinkPictureBuilder;
import ec.animal.adoption.domain.media.LinkPicture;
import ec.animal.adoption.domain.media.PictureType;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class JpaPrimaryLinkPictureTest {

    @Test
    public void shouldGenerateAnUuidWhenCreatingAJpaPrimaryLinkPictureForAPrimaryLinkPictureWithNoUuid() {
        LinkPicture primaryLinkPicture = LinkPictureBuilder.random().withUuid(null)
                .withPictureType(PictureType.PRIMARY).build();
        JpaPrimaryLinkPicture jpaPrimaryLinkPicture = new JpaPrimaryLinkPicture(
                primaryLinkPicture, mock(JpaAnimal.class)
        );

        LinkPicture jpaPrimaryLinkPictureToLinkPicture = jpaPrimaryLinkPicture.toLinkPicture();

        assertNotNull(jpaPrimaryLinkPictureToLinkPicture.getUuid());
    }

    @Test
    public void shouldGenerateARegistrationDateWhenCreatingAJpaPrimaryLinkPictureForAPrimaryLinkPictureWithNoRegistrationDate() {
        LinkPicture primaryLinkPicture = LinkPictureBuilder.random().withRegistrationDate(null)
                .withPictureType(PictureType.PRIMARY).build();
        JpaPrimaryLinkPicture jpaPrimaryLinkPicture = new JpaPrimaryLinkPicture(
                primaryLinkPicture, mock(JpaAnimal.class)
        );

        LinkPicture jpaPrimaryLinkPictureToLinkPicture = jpaPrimaryLinkPicture.toLinkPicture();

        assertNotNull(jpaPrimaryLinkPictureToLinkPicture.getRegistrationDate());
    }

    @Test
    public void shouldCreateAPrimaryLinkPictureWithUuid() {
        UUID uuid = UUID.randomUUID();
        LinkPicture primaryLinkPicture = LinkPictureBuilder.random().withUuid(uuid)
                .withPictureType(PictureType.PRIMARY).build();
        JpaPrimaryLinkPicture jpaPrimaryLinkPicture = new JpaPrimaryLinkPicture(
                primaryLinkPicture, mock(JpaAnimal.class)
        );

        LinkPicture jpaPrimaryLinkPictureToLinkPicture = jpaPrimaryLinkPicture.toLinkPicture();

        assertThat(jpaPrimaryLinkPictureToLinkPicture.getUuid(), is(uuid));
    }

    @Test
    public void shouldCreateAPrimaryLinkPictureWithRegistrationDate() {
        LocalDateTime registrationDate = LocalDateTime.now();
        LinkPicture primaryLinkPicture = LinkPictureBuilder.random().withRegistrationDate(registrationDate)
                .withPictureType(PictureType.PRIMARY).build();
        JpaPrimaryLinkPicture jpaPrimaryLinkPicture = new JpaPrimaryLinkPicture(
                primaryLinkPicture, mock(JpaAnimal.class)
        );

        LinkPicture jpaPrimaryLinkPictureToLinkPicture = jpaPrimaryLinkPicture.toLinkPicture();

        assertThat(jpaPrimaryLinkPictureToLinkPicture.getRegistrationDate(), is(registrationDate));
    }

    @Test
    public void shouldCreateJpaPrimaryLinkPictureFromPrimaryLinkPicture() {
        LinkPicture primaryLinkPicture = LinkPictureBuilder.random().withPictureType(PictureType.PRIMARY).build();
        JpaPrimaryLinkPicture jpaPrimaryLinkPicture = new JpaPrimaryLinkPicture(
                primaryLinkPicture, mock(JpaAnimal.class)
        );

        LinkPicture jpaPrimaryLinkPictureToLinkPicture = jpaPrimaryLinkPicture.toLinkPicture();

        assertThat(jpaPrimaryLinkPictureToLinkPicture.getName(), is(primaryLinkPicture.getName()));
        assertThat(jpaPrimaryLinkPictureToLinkPicture.getPictureType(), is(primaryLinkPicture.getPictureType()));
        assertThat(jpaPrimaryLinkPictureToLinkPicture.getLargeImageUrl(), is(primaryLinkPicture.getLargeImageUrl()));
        assertThat(jpaPrimaryLinkPictureToLinkPicture.getSmallImageUrl(), is(primaryLinkPicture.getSmallImageUrl()));
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void shouldVerifyEqualsAndHashCodeMethods() {
        EqualsVerifier.forClass(JpaPrimaryLinkPicture.class).usingGetClass()
                .withPrefabValues(Timestamp.class, Timestamp.valueOf(LocalDateTime.now()),
                        Timestamp.valueOf(LocalDateTime.now().minusDays(2)))
                .withPrefabValues(JpaAnimal.class, mock(JpaAnimal.class), mock(JpaAnimal.class))
                .suppress(Warning.NONFINAL_FIELDS, Warning.REFERENCE_EQUALITY).verify();
    }
}