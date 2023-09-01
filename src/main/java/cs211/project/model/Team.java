package cs211.project.model;

public class Team {
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public void changeName(String name) {
        if (!name.trim().equals("")) {
            this.name = name.trim();
        }
    }
    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "{" +
                "name: '" + name +
                '}';
    }
}
