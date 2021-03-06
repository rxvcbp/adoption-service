package ec.animal.adoption.api.model.animal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.animal.adoption.TestUtils;
import ec.animal.adoption.api.model.characteristics.CharacteristicsResponse;
import ec.animal.adoption.api.model.media.LinkPictureResponse;
import ec.animal.adoption.api.model.state.StateResponse;
import ec.animal.adoption.api.model.story.StoryResponse;
import ec.animal.adoption.domain.animal.Animal;
import ec.animal.adoption.domain.animal.AnimalFactory;
import ec.animal.adoption.domain.characteristics.Characteristics;
import ec.animal.adoption.domain.characteristics.CharacteristicsFactory;
import ec.animal.adoption.domain.media.LinkPicture;
import ec.animal.adoption.domain.media.LinkPictureFactory;
import ec.animal.adoption.domain.media.PictureType;
import ec.animal.adoption.domain.story.Story;
import ec.animal.adoption.domain.story.StoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class AnimalResponseTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = TestUtils.getObjectMapper();
    }

    @Test
    public void shouldSerializeAnimalResponse() throws JsonProcessingException {
        LinkPicture primaryLinkPicture = LinkPictureFactory.random().withPictureType(PictureType.PRIMARY).build();
        Characteristics characteristics = CharacteristicsFactory.random().build();
        Story story = StoryFactory.random().build();
        Animal animal = AnimalFactory.random()
                                     .withIdentifier(UUID.randomUUID())
                                     .withPrimaryLinkPicture(primaryLinkPicture)
                                     .withCharacteristics(characteristics)
                                     .withStory(story)
                                     .withRegistrationDate(LocalDateTime.now())
                                     .build();
        AnimalResponse animalResponse = AnimalResponse.from(animal);
        String expectedSerializedState = objectMapper.writeValueAsString(StateResponse.from(animal.getState()));
        String expectedRegistrationDate = objectMapper.writeValueAsString(animal.getRegistrationDate());
        String expectedPrimaryLinkPicture = objectMapper.writeValueAsString(LinkPictureResponse.from(primaryLinkPicture));
        String expectedCharacteristics = objectMapper.writeValueAsString(CharacteristicsResponse.from(characteristics));
        String expectedStory = objectMapper.writeValueAsString(StoryResponse.from(story));

        String serializedAnimalResponse = objectMapper.writeValueAsString(animalResponse);

        assertThat(serializedAnimalResponse, containsString(String.format("\"id\":\"%s\"", animal.getIdentifier().toString())));
        assertThat(serializedAnimalResponse, containsString(String.format("\"registrationDate\":%s", expectedRegistrationDate)));
        assertThat(serializedAnimalResponse, containsString(String.format("\"clinicalRecord\":\"%s\"", animal.getClinicalRecord())));
        assertThat(serializedAnimalResponse, containsString(String.format("\"name\":\"%s\"", animal.getName())));
        assertThat(serializedAnimalResponse, containsString(String.format("\"species\":\"%s\"", animal.getSpecies().name())));
        assertThat(serializedAnimalResponse, containsString(String.format("\"estimatedAge\":\"%s\"", animal.getEstimatedAge().name())));
        assertThat(serializedAnimalResponse, containsString(String.format("\"sex\":\"%s\"", animal.getSex().name())));
        assertThat(serializedAnimalResponse, containsString(String.format("\"state\":%s", expectedSerializedState)));
        assertThat(serializedAnimalResponse, containsString(String.format("\"primaryLinkPicture\":%s", expectedPrimaryLinkPicture)));
        assertThat(serializedAnimalResponse, containsString(String.format("\"characteristics\":%s", expectedCharacteristics)));
        assertThat(serializedAnimalResponse, containsString(String.format("\"story\":%s", expectedStory)));
    }
}