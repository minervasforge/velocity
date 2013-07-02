package com.minervasforge.velocity.persistence;

import com.minervasforge.velocity.models.Skill;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SkillRepositoryTest {
    @Test
    public void shouldAddSkillToRepository(){
        SkillRepository skillRepository = createRepositoryWithDefaultSkills();

        assertThat(skillRepository.getAll(), is(asList(new Skill("one"), new Skill("two"))));
    }

    @Test
    public void shouldTestSkillRepositoryEquality(){
        SkillRepository skillRepository = createRepositoryWithDefaultSkills();
        SkillRepository skillRepository2 = createRepositoryWithDefaultSkills();

        assertThat(skillRepository, is(skillRepository2));
    }

    private SkillRepository createRepositoryWithDefaultSkills() {
        SkillRepository skillRepository = new SkillRepository();
        skillRepository.add(new Skill("one"));
        skillRepository.add(new Skill("two"));
        return skillRepository;
    }
}
