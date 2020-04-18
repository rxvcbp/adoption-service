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

import ec.animal.adoption.builders.AnimalBuilder;
import ec.animal.adoption.builders.ImagePictureBuilder;
import ec.animal.adoption.builders.LinkPictureBuilder;
import ec.animal.adoption.domain.animal.Animal;
import ec.animal.adoption.domain.animal.AnimalRepository;
import ec.animal.adoption.domain.exception.EntityNotFoundException;
import ec.animal.adoption.domain.exception.InvalidPictureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PictureServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private MediaRepository mediaRepository;

    private PictureService pictureService;


    @BeforeEach
    public void setUp() {
        pictureService = new PictureService(mediaRepository, animalRepository);
    }

    @Test
    public void shouldCreateAPicture() {
        ArgumentCaptor<Animal> argumentCaptor = ArgumentCaptor.forClass(Animal.class);
        ImagePicture imagePicture = ImagePictureBuilder.random().withPictureType(PictureType.PRIMARY).build();
        LinkPicture primaryLinkPicture = LinkPictureBuilder.random().withPictureType(PictureType.PRIMARY).build();
        when(mediaRepository.save(imagePicture)).thenReturn(primaryLinkPicture);
        Animal animal = AnimalBuilder.random().withUuid(imagePicture.getAnimalUuid())
                                     .withRegistrationDate(LocalDateTime.now()).build();
        when(animalRepository.getBy(imagePicture.getAnimalUuid())).thenReturn(animal);
        Animal animalWithPrimaryLinkPicture = AnimalBuilder.random().withPrimaryLinkPicture(primaryLinkPicture)
                                                           .withState(animal.getState()).withClinicalRecord(animal.getClinicalRecord()).withName(animal.getName())
                                                           .withEstimatedAge(animal.getEstimatedAge()).withSex(animal.getSex()).withSpecies(animal.getSpecies())
                                                           .withRegistrationDate(animal.getRegistrationDate()).withUuid(animal.getUuid()).build();
        when(animalRepository.save(any(Animal.class))).thenReturn(animalWithPrimaryLinkPicture);

        LinkPicture createdPicture = pictureService.createPrimaryPicture(imagePicture);

        verify(animalRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getPrimaryLinkPicture(), is(primaryLinkPicture));
        assertThat(createdPicture, is(primaryLinkPicture));
    }

    @Test
    public void shouldThrowInvalidPictureExceptionIfPictureTypeIsNotPrimary() {
        ImagePicture imagePicture = ImagePictureBuilder.random().withPictureType(PictureType.ALTERNATE).build();

        assertThrows(InvalidPictureException.class, () -> {
            pictureService.createPrimaryPicture(imagePicture);
        });
    }

    @Test
    public void shouldThrowInvalidPictureExceptionWhenImagePictureIsInvalid() {
        ImagePicture imagePicture = mock(ImagePicture.class);
        when(imagePicture.getPictureType()).thenReturn(PictureType.PRIMARY);
        when(imagePicture.isValid()).thenReturn(false);

        assertThrows(InvalidPictureException.class, () -> {
            pictureService.createPrimaryPicture(imagePicture);
        });
    }

    @Test
    public void shouldGetPrimaryLinkPictureByAnimalUuid() {
        LinkPicture expectedPrimaryLinkPicture = LinkPictureBuilder.random()
                                                                   .withPictureType(PictureType.PRIMARY).build();
        UUID animalUuid = UUID.randomUUID();
        Animal animal = AnimalBuilder.random().withUuid(animalUuid)
                                     .withPrimaryLinkPicture(expectedPrimaryLinkPicture).build();
        when(animalRepository.getBy(animalUuid)).thenReturn(animal);

        LinkPicture linkPicture = pictureService.getBy(animalUuid);

        assertThat(linkPicture, is(expectedPrimaryLinkPicture));
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenThereIsNoPrimaryLinkPictureForAnimal() {
        UUID animalUuid = UUID.randomUUID();
        Animal animal = AnimalBuilder.random().build();
        when(animalRepository.getBy(animalUuid)).thenReturn(animal);

        assertThrows(EntityNotFoundException.class, () -> {
            pictureService.getBy(animalUuid);
        });
    }
}