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

package ec.animal.adoption.domain.animal;

import ec.animal.adoption.domain.animal.dto.AnimalDto;
import ec.animal.adoption.domain.animal.dto.AnimalDtoFactory;
import ec.animal.adoption.domain.characteristics.Characteristics;
import ec.animal.adoption.domain.characteristics.CharacteristicsFactory;
import ec.animal.adoption.domain.media.LinkPicture;
import ec.animal.adoption.domain.media.LinkPictureFactory;
import ec.animal.adoption.domain.media.PictureType;
import ec.animal.adoption.domain.organization.Organization;
import ec.animal.adoption.domain.organization.OrganizationFactory;
import ec.animal.adoption.domain.state.State;
import ec.animal.adoption.domain.story.Story;
import ec.animal.adoption.domain.story.StoryFactory;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static ec.animal.adoption.TestUtils.getRandomEstimatedAge;
import static ec.animal.adoption.TestUtils.getRandomSex;
import static ec.animal.adoption.TestUtils.getRandomSpecies;
import static ec.animal.adoption.TestUtils.getRandomState;
import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalTest {

    @Test
    public void shouldReturnStateName() {
        State randomState = getRandomState();
        Animal animal = AnimalFactory.random().withState(randomState).build();

        assertThat(animal.getStateName(), is(randomState.getName().name()));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenSettingAnAlternateLinkPicture() {
        LinkPicture alternateLinkPicture = LinkPictureFactory.random().withPictureType(PictureType.ALTERNATE).build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal(UUID.randomUUID(),
                       LocalDateTime.now(),
                       randomAlphabetic(10),
                       randomAlphabetic(10),
                       getRandomSpecies(),
                       getRandomEstimatedAge(),
                       getRandomSex(),
                       getRandomState(),
                       alternateLinkPicture,
                       CharacteristicsFactory.random().build(),
                       StoryFactory.random().build(),
                       OrganizationFactory.random().build());
        });
        assertEquals("Picture type should be PRIMARY", exception.getMessage());
    }

    @Test
    public void shouldSetOrganization() {
        Organization organization = OrganizationFactory.random().build();

        Animal animal = new Animal(UUID.randomUUID(),
                                   LocalDateTime.now(),
                                   randomAlphabetic(10),
                                   randomAlphabetic(10),
                                   getRandomSpecies(),
                                   getRandomEstimatedAge(),
                                   getRandomSex(),
                                   getRandomState(),
                                   LinkPictureFactory.random().withPictureType(PictureType.PRIMARY).build(),
                                   CharacteristicsFactory.random().build(),
                                   StoryFactory.random().build(),
                                   organization);

        assertEquals(organization, animal.getOrganization());
    }

    @Test
    void shouldReturnTrueWhenIdentifiersFromBothAnimalsMatch() {
        UUID animalId = UUID.randomUUID();
        Animal animal = AnimalFactory.random().withIdentifier(animalId).build();
        Animal anotherAnimal = AnimalFactory.random().withIdentifier(animalId).build();

        assertTrue(animal.isSameAs(anotherAnimal));
    }

    @Test
    void shouldReturnFalseWhenIdentifiersDoNotMatch() {
        Animal animal = AnimalFactory.random().withIdentifier(UUID.randomUUID()).build();
        Animal anotherAnimal = AnimalFactory.random().withIdentifier(UUID.randomUUID()).build();

        assertFalse(animal.isSameAs(anotherAnimal));
    }

    @Test
    void shouldReturnTrueWhenAnimalAlreadyHasTheStoryProvided() {
        Story story = StoryFactory.random().build();
        Animal animal = AnimalFactory.random().withStory(story).build();

        assertTrue(animal.has(story));
    }

    @Test
    void shouldReturnFalseWhenAnimalDoesNotHaveAnyStory() {
        Story story = StoryFactory.random().build();
        Animal animal = AnimalFactory.random().withStory(null).build();

        assertFalse(animal.has(story));
    }

    @Test
    void shouldReturnFalseWhenAnimalHasADifferentStory() {
        Story story = StoryFactory.random().build();
        Animal animal = AnimalFactory.random().withStory(StoryFactory.random().build()).build();

        assertFalse(animal.has(story));
    }

    @Test
    void shouldReturnTrueWhenAnimalAlreadyHasTheCharacteristicsProvided() {
        Characteristics characteristics = CharacteristicsFactory.random().build();
        Animal animal = AnimalFactory.random().withCharacteristics(characteristics).build();

        assertTrue(animal.has(characteristics));
    }

    @Test
    void shouldReturnFalseWhenAnimalDoesNotHaveAnyCharacteristics() {
        Characteristics characteristics = CharacteristicsFactory.random().build();
        Animal animal = AnimalFactory.random().withCharacteristics(null).build();

        assertFalse(animal.has(characteristics));
    }

    @Test
    void shouldReturnFalseWhenAnimalHasDifferentCharacteristics() {
        Characteristics characteristics = CharacteristicsFactory.random().build();
        Animal animal = AnimalFactory.random().withCharacteristics(CharacteristicsFactory.random().build()).build();

        assertFalse(animal.has(characteristics));
    }

    @Test
    void shouldReturnAnimalWithClinicalRecordNameSpeciesEstimatedAgeSexAndStateFromAnimalDto() {
        Animal animal = AnimalFactory.random().build();
        AnimalDto animalDto = AnimalDtoFactory.random().build();

        Animal updatedAnimal = animal.updateWith(animalDto);

        assertEquals(animal.getIdentifier(), updatedAnimal.getIdentifier());
        assertEquals(animal.getRegistrationDate(), updatedAnimal.getRegistrationDate());
        assertEquals(animalDto.getClinicalRecord(), updatedAnimal.getClinicalRecord());
        assertEquals(animalDto.getName(), updatedAnimal.getName());
        assertEquals(animalDto.getSpecies(), updatedAnimal.getSpecies());
        assertEquals(animalDto.getEstimatedAge(), updatedAnimal.getEstimatedAge());
        assertEquals(animalDto.getSex(), updatedAnimal.getSex());
        assertEquals(animalDto.getState(), updatedAnimal.getState());
        assertEquals(animal.getPrimaryLinkPicture(), updatedAnimal.getPrimaryLinkPicture());
        assertEquals(animal.getCharacteristics(), updatedAnimal.getCharacteristics());
        assertEquals(animal.getStory(), updatedAnimal.getStory());
        assertEquals(animal.getOrganization(), updatedAnimal.getOrganization());
    }

    @Test
    public void shouldVerifyEqualsAndHashCodeMethods() {
        EqualsVerifier.forClass(Animal.class).usingGetClass().verify();
    }
}