import java.util.List;

public class Profile{
	User user;
	List<Bet> bets;
	
	Profile(User user, List<Bet> bets){
		this.user = user;
		this.bets = bets;
	}
}