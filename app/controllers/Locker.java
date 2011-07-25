package controllers;

import java.util.List;

import models.Address;
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
		if( userName == null )
		{
			try
			{
				Secure.login();
			} catch( Throwable e )
			{
				//TODO checkout how to redirect back here !
				forbidden();
				e.printStackTrace();

			}
		}
		List<User> users = User.all( User.class ).filter( "login", userName ).fetch();
		if( users.size() == 0 )
		{
			forbidden();
		}
		User user = users.get( 0 );
		List<Address> addressList = Model.all( Address.class ).filter( "userId", user ).fetch();
		List<Order> orderList = Model.all( Order.class ).filter( "orderOwner", user ).fetch();
		render( user, addressList, orderList );
	}

	public static void addAddress( Address newAddress )
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
		newAddress.userId = user.get( 0 );
		newAddress.insert();
		ok();
	}

	public static void editAddress( Address address )
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
		address.userId = user.get( 0 );

		address.insert();
		ok();
	}

}
