package controllers;

import java.util.List;

import models.Adress;
import models.Order;
import models.User;
import play.mvc.Controller;
import play.mvc.With;
import siena.Model;

/**
 * User Personal Page Controller
 * 
 * @author mike
 * */
@With(Secure.class)
public class Locker extends Controller
{

	/**
	 * Shows user's personal page
	 */
	public static void index()
	{
		String userName = Security.connected();
		if( userName != null )
		{
			unauthorized();
		}
		List<User> users = User.all( User.class ).filter( "login", userName ).fetch();
		if( users.size() == 0 )
		{
			forbidden();
		}
		User user = users.get( 0 );
		List<Adress> adressList = Model.all( Adress.class ).filter( "userId", user ).fetch();
		List<Order> orderList = Model.all( Order.class ).filter( "orderOwner", user ).fetch();
		render( user, adressList, orderList );
	}

	public static void addAdress( Adress newAdress )
	{
		String userName = Security.connected();
		if( userName != null )
		{
			unauthorized();
		}
		List<User> user = User.all( User.class ).filter( "login", userName ).fetch( 1 );
		if( user.size() != 1 )
		{
			forbidden();
		}
		newAdress.userId = user.get( 0 );
		newAdress.insert();
		ok();
	}

	public static void editAdress( Adress adress )
	{
		String userName = Security.connected();
		if( userName != null )
		{
			unauthorized();
		}
		List<User> user = User.all( User.class ).filter( "login", userName ).fetch( 1 );
		if( user.size() != 1 )
		{
			forbidden();
		}
		adress.userId = user.get( 0 );

		adress.insert();
		ok();
	}

}
