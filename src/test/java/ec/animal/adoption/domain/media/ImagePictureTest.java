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

package ec.animal.adoption.domain.media;

import ec.animal.adoption.builders.ImagePictureBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static ec.animal.adoption.TestUtils.getRandomPictureType;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImagePictureTest {

    @Test
    public void shouldCreateAnImagePicture() {
        UUID animalUuid = UUID.randomUUID();
        String name = randomAlphabetic(10);
        PictureType pictureType = getRandomPictureType();
        ImagePicture imagePicture = ImagePictureBuilder.random().withAnimalUuid(animalUuid).
                withName(name).withPictureType(pictureType).build();

        assertThat(imagePicture.getAnimalUuid(), is(animalUuid));
        assertThat(imagePicture.getName(), is(name));
        assertThat(imagePicture.getPictureType(), is(pictureType));
    }

    @Test
    public void shouldReturnLargeImagePath() {
        Image largeImage = mock(Image.class);
        String extension = randomAlphabetic(3);
        when(largeImage.getExtension()).thenReturn(extension);
        UUID animalUuid = UUID.randomUUID();
        String name = randomAlphabetic(10);
        ImagePicture imagePicture = ImagePictureBuilder.random().withAnimalUuid(animalUuid).
                withName(name).withLargeImage(largeImage).build();
        String expectedLargeImagePath = animalUuid + "/" + name + "_LARGE." + extension;

        assertThat(imagePicture.getLargeImagePath(), is(expectedLargeImagePath));
    }

    @Test
    public void shouldReturnLargeImageContent() {
        Image largeImage = mock(Image.class);
        byte[] content = {};
        when(largeImage.getContent()).thenReturn(content);
        ImagePicture imagePicture = ImagePictureBuilder.random().withLargeImage(largeImage).build();

        assertThat(imagePicture.getLargeImageContent(), is(content));
    }

    @Test
    public void shouldReturnSmallImagePath() {
        Image smallImage = mock(Image.class);
        String extension = randomAlphabetic(3);
        when(smallImage.getExtension()).thenReturn(extension);
        UUID animalUuid = UUID.randomUUID();
        String name = randomAlphabetic(10);
        ImagePicture imagePicture = ImagePictureBuilder.random().withAnimalUuid(animalUuid).
                withName(name).withSmallImage(smallImage).build();
        String expectedSmallImagePath = animalUuid + "/" + name + "_SMALL." + extension;

        assertThat(imagePicture.getSmallImagePath(), is(expectedSmallImagePath));
    }

    @Test
    public void shouldReturnSmallImageContent() {
        Image smallImage = mock(Image.class);
        byte[] content = {};
        when(smallImage.getContent()).thenReturn(content);
        ImagePicture imagePicture = ImagePictureBuilder.random().withSmallImage(smallImage).build();

        assertThat(imagePicture.getSmallImageContent(), is(content));
    }

    @Test
    public void shouldVerifyEqualsAndHashCodeMethods() {
        EqualsVerifier.forClass(ImagePicture.class).suppress(Warning.NONFINAL_FIELDS).usingGetClass().verify();
    }

    @Test
    public void shouldReturnTrueForValidImagePicture() {
        ImagePicture imagePicture = ImagePictureBuilder.random().build();

        assertThat(imagePicture.isValid(), is(true));
    }

    @Test
    public void shouldReturnFalseWhenPictureImageHasAnInvalidLargeImage() {
        Image invalidLargeImage = mock(Image.class);
        when(invalidLargeImage.isValid()).thenReturn(false);
        ImagePicture imagePicture = ImagePictureBuilder.random().withLargeImage(invalidLargeImage).build();

        assertThat(imagePicture.isValid(), is(false));
    }

    @Test
    public void shouldReturnFalseWhenPictureImageHasAnInvalidSmallImage() {
        Image invalidSmallImage = mock(Image.class);
        when(invalidSmallImage.isValid()).thenReturn(false);
        ImagePicture imagePicture = ImagePictureBuilder.random().withSmallImage(invalidSmallImage).build();

        assertThat(imagePicture.isValid(), is(false));
    }

    @Test
    public void shouldReturnFalseWhenBothLargeAndSmallImageInsidePictureImageAreInvalid() {
        Image invalidImage = mock(Image.class);
        when(invalidImage.isValid()).thenReturn(false);
        ImagePicture imagePicture = ImagePictureBuilder.random().withLargeImage(invalidImage).
                withSmallImage(invalidImage).build();

        assertThat(imagePicture.isValid(), is(false));
    }
}