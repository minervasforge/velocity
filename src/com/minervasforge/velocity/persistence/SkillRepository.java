package com.minervasforge.velocity.persistence;

import com.minervasforge.velocity.models.Skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SkillRepository implements Serializable {
    private List<Skill> skills = new ArrayList<Skill>();

    public void add(Skill skill) {
        skills.add(skill);
    }

    public List<Skill> getAll() {
        return skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkillRepository that = (SkillRepository) o;

        if (!skills.equals(that.skills)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return skills.hashCode();
    }

    @Override
    public String toString() {
        return "SkillRepository{" +
                "skills=" + skills +
                '}';
    }
}
