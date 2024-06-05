package rookie.brain.clickerGame.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int nextlevelscore;
    private int seconds;

    public Level(Long id, String name, int nextlevelscore, int seconds) {
        this.id = id;
        this.name = name;
        this.nextlevelscore = nextlevelscore;
        this.seconds = seconds;
    }


    public Level() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNextlevelscore() {
        return nextlevelscore;
    }

    public void setNextlevelscore(int nextlevelscore) {
        this.nextlevelscore = nextlevelscore;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
