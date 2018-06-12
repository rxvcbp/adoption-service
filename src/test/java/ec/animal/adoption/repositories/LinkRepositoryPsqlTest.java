package ec.animal.adoption.repositories;

import ec.animal.adoption.domain.media.Link;
import ec.animal.adoption.exceptions.EntityAlreadyExistsException;
import ec.animal.adoption.models.jpa.JpaMediaLink;
import ec.animal.adoption.repositories.jpa.JpaMediaLinkRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.postgresql.util.PSQLException;

import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LinkRepositoryPsqlTest {

    @Mock
    private JpaMediaLinkRepository jpaMediaLinkRepository;

    private Link link;
    private MediaLinkLinkRepositoryPsql mediaLinkRepositoryPsql;


    @Before
    public void setUp() {
        link = new Link(UUID.randomUUID(), randomAlphabetic(10), randomAlphabetic(10));
        mediaLinkRepositoryPsql = new MediaLinkLinkRepositoryPsql(jpaMediaLinkRepository);
    }

    @Test
    public void shouldBeAnInstanceOfMediaLinkRepository() {
        assertThat(mediaLinkRepositoryPsql, is(instanceOf(MediaLinkRepository.class)));
    }

    @Test
    public void shouldSaveJpaMediaLink() throws EntityAlreadyExistsException {
        ArgumentCaptor<JpaMediaLink> jpaImageMediaArgumentCaptor = ArgumentCaptor.forClass(JpaMediaLink.class);
        JpaMediaLink expectedJpaMediaLink = new JpaMediaLink(this.link);
        when(jpaMediaLinkRepository.save(any(JpaMediaLink.class))).thenReturn(expectedJpaMediaLink);

        Link savedLink = mediaLinkRepositoryPsql.save(this.link);

        verify(jpaMediaLinkRepository).save(jpaImageMediaArgumentCaptor.capture());
        JpaMediaLink jpaMediaLink = jpaImageMediaArgumentCaptor.getValue();
        Link link = jpaMediaLink.toMediaLink();

        assertThat(link, is(this.link));
        assertThat(expectedJpaMediaLink.toMediaLink(), is(savedLink));
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void shouldThrowEntityAlreadyExistException() throws EntityAlreadyExistsException {
        doAnswer((Answer<Object>) invocation -> {
            throw mock(PSQLException.class);
        }).when(jpaMediaLinkRepository).save(any(JpaMediaLink.class));

        mediaLinkRepositoryPsql.save(this.link);
    }
}