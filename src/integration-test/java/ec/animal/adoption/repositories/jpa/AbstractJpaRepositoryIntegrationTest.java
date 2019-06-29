package ec.animal.adoption.repositories.jpa;

import ec.animal.adoption.builders.AnimalBuilder;
import ec.animal.adoption.models.jpa.JpaAnimal;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class AbstractJpaRepositoryIntegrationTest {

    @Autowired
    JpaAnimalRepository jpaAnimalRepository;

    JpaAnimal createAndSaveJpaAnimal() {
        return jpaAnimalRepository.save(new JpaAnimal(AnimalBuilder.random().build()));
    }
}