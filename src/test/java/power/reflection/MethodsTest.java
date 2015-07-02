package power.reflection;

import static org.junit.Assert.assertEquals;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import lombok.experimental.Accessors;

import org.junit.Assert;
import org.junit.Test;

import power.util.Util;

public class MethodsTest {

	@Test
	@SneakyThrows
	public void ensureThatCouldFindAMethodToAppPanelIntoFrame(){
		val hero = new Hero();
			
		val knife = new Weapon();
		Reflection.getAnyMethod(hero, "add", Weapon.class).invoke( knife );
		val gun = new Weapon();
		Reflection.getAnyMethod(hero, "add", Item.class).invoke( gun );
		
		assertEquals( 2, hero.items().size() );
	}
	
	@Test
	public void ensureThatCouldInstantiateAConstructor(){
		val weapon1 = (Weapon)Reflection.getConstructor( Weapon.class, Integer.TYPE).convertParamsAndInvoke( "1" );
		assertEquals( 1, weapon1.damage());
		val weapon2 = (Weapon)Reflection.getConstructor( Weapon.class, 1).convertParamsAndInvoke( "1" );
		assertEquals( weapon2.damage(), weapon1.damage());
		val weapon3 = (Weapon)Reflection.getConstructor( Weapon.class, 0).invoke();
		assertEquals( 0, weapon3.damage());
	}
	
	@Test
	public void ensureThatCouldFindCorrectMethods(){
		val method1 = Reflection.getAnyMethod(new TwoMethods(), "getName");
		Assert.assertEquals(1, method1.executable().getParameterCount());
		val method2 = Reflection.getMethod(new TwoMethods(), "getName");
		Assert.assertEquals(0, method2.executable().getParameterCount());
		val method3 = Reflection.getMethod(new TwoMethods(), "getName", String.class);
		Assert.assertEquals(1, method3.executable().getParameterCount());
		val method4 = Reflection.getMethod(new TwoMethods(), "getName", Integer.class);
		Assert.assertNull(method4.executable());
	}

}

interface Item {}

@Getter
@Accessors( fluent=true )
class Hero {
	
	final List<Item> items = Util.list();
	
	public void add( Item item ) {
		items.add(item);
	}
}

@Getter
@Accessors( fluent=true )
@RequiredArgsConstructor
class Weapon implements Item {
	
	final int damage;
	
	public Weapon() {
		damage = 0;
	}
}

class TwoMethods{
	
	public String getName(String parameter){
		return null;
	}
	
	public String getName(){
		return null;
	}
	
}
