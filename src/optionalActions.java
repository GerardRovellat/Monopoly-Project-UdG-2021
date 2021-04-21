
public interface optionalActions {
// General actions interface

    private ArrayList<Player> players_list;

    public optionalActions (ArrayList<Player> players_list){
        this->players_list = players_list;
    }

    public execute();
}