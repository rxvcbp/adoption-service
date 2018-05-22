package ec.animal.adoption;

import ec.animal.adoption.domain.EstimatedAge;
import ec.animal.adoption.domain.Sex;
import ec.animal.adoption.domain.Type;
import ec.animal.adoption.domain.characteristics.FriendlyWith;
import ec.animal.adoption.domain.characteristics.PhysicalActivity;
import ec.animal.adoption.domain.characteristics.Size;
import ec.animal.adoption.domain.characteristics.temperament.Balance;
import ec.animal.adoption.domain.characteristics.temperament.Docility;
import ec.animal.adoption.domain.characteristics.temperament.Sociability;
import ec.animal.adoption.domain.characteristics.temperament.Temperament;
import ec.animal.adoption.domain.state.Adopted;
import ec.animal.adoption.domain.state.LookingForHuman;
import ec.animal.adoption.domain.state.State;
import ec.animal.adoption.domain.state.Unavailable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestUtils {

    private static final List<State> STATES = Arrays.asList(
            new LookingForHuman(LocalDateTime.now()),
            new Adopted(LocalDate.now(), randomAlphabetic(10)),
            new Unavailable(randomAlphabetic(10))
    );
    private static final Type[] TYPES = Type.values();
    private static final EstimatedAge[] ESTIMATED_AGES = EstimatedAge.values();
    private static final Sex[] SEXES = Sex.values();
    private static final Size[] SIZES = Size.values();
    private static final PhysicalActivity[] PHYSICAL_ACTIVITIES = PhysicalActivity.values();
    private static final FriendlyWith[] FRIENDLY_WITH = FriendlyWith.values();
    private static final Sociability[] SOCIABILITY = Sociability.values();
    private static final Docility[] DOCILITY = Docility.values();
    private static final Balance[] BALANCE = Balance.values();

    public static State getRandomState() {
        return STATES.get(getRandomIndex(STATES.size()));
    }

    public static Type getRandomType() {
        return TYPES[getRandomIndex(TYPES.length)];
    }

    public static EstimatedAge getRandomEstimatedAge() {
        return ESTIMATED_AGES[getRandomIndex(ESTIMATED_AGES.length)];
    }

    public static Sex getRandomSex() {
        return SEXES[getRandomIndex(SEXES.length)];
    }

    public static Size getRandomSize() {
        return SIZES[getRandomIndex(SIZES.length)];
    }

    public static PhysicalActivity getRandomPhysicalActivity() {
        return PHYSICAL_ACTIVITIES[getRandomIndex(PHYSICAL_ACTIVITIES.length)];
    }

    public static Temperament getRandomTemperament() {
        List<Temperament> temperaments = new ArrayList<>();
        Collections.addAll(temperaments, SOCIABILITY);
        Collections.addAll(temperaments, DOCILITY);
        Collections.addAll(temperaments, BALANCE);
        return temperaments.get(getRandomIndex(temperaments.size()));
    }

    public static Sociability getRandomSociability() {
        return SOCIABILITY[getRandomIndex(SOCIABILITY.length)];
    }

    public static Docility getRandomDocility() {
        return DOCILITY[getRandomIndex(DOCILITY.length)];
    }

    public static Balance getRandomBalance() {
        return BALANCE[getRandomIndex(BALANCE.length)];
    }

    public static FriendlyWith getRandomFriendlyWith() {
        return FRIENDLY_WITH[getRandomIndex(FRIENDLY_WITH.length)];
    }

    private static int getRandomIndex(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
}