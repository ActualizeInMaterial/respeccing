package org.demlinks.javaone;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class LinkedListSetTest {

	LinkedListSet<String> lls;
	String a = "a";
	String b = "b";
	String c = "c";
	
	@Before
	public void init() {
		lls = new LinkedListSet<String>();
	}

	@Test
	public void testIsEmpty() {
		assertTrue(lls.isEmpty());
		assertTrue(lls.getSize() == 0);
		
		String boo = "boo";
		assertTrue( lls.addFirst(boo) );
		assertTrue(lls.getSize() == 1);
		assertFalse(lls.isEmpty());
		
		assertTrue( boo == lls.removeLast() );
		assertTrue(lls.isEmpty());
		assertTrue(lls.getSize() == 0);
		
		assertTrue( lls.addLast("boo") );
		assertFalse(lls.isEmpty());
		assertTrue(lls.getSize() == 1);
		
		assertTrue( boo == lls.removeFirst() );
		assertTrue(lls.isEmpty());
		assertTrue(lls.getSize() == 0);
	}

	@Test
	public void testGetFirstObj() {
		boolean nsee;
			assertTrue(null == lls.getFirstObj() );
		
		nsee = false;
		try {
			lls.removeLast();
		} catch (NoSuchElementException e) {
			nsee = true;
		}
		assertTrue(nsee);
		
		String boo = "boo";
		lls.addLast(boo);
		String newBoo = new String("boo");
		assertTrue(boo != newBoo);
		String gotten = lls.getFirstObj();
		assertTrue( newBoo != gotten );
		assertTrue( newBoo.equals(gotten));
		
		assertTrue( boo == lls.removeLast() );
	}

	@Test
	public void testGetLastObj() {
		boolean nsee;
			assertTrue( null == lls.getLastObj() );
		
		nsee = false;
		try {
			lls.removeFirst();
		} catch (NoSuchElementException e) {
			nsee = true;
		}
		assertTrue(nsee);
		
		String boo = "boo";
		lls.addLast(boo);
		String newBoo = new String("boo");
		assertTrue(boo != newBoo);
		String gotten = lls.getLastObj();
		assertTrue( newBoo != gotten );
		assertTrue( newBoo.equals(gotten));
		
		assertTrue(gotten == lls.getFirstObj() );
		
		assertTrue( boo == lls.removeFirst() );
	}

	@Test
	public void testAddFirst() {

		assertTrue( lls.addFirst(a) );
		assertTrue( a == lls.getFirstObj());
		
		assertTrue( lls.addFirst(b) );
		assertTrue( b == lls.getFirstObj());
		
		assertTrue( lls.addFirst(c) );
		assertTrue( c == lls.getFirstObj());
		
		assertTrue( a == lls.getLastObj());
		assertFalse( lls.addLast(c) );//not added, already exists
		assertTrue( a == lls.getLastObj());
	}

	@Test
	public void testAddLast() {
		assertTrue(lls.addLast(a));
		assertFalse(lls.addLast(a));
		assertTrue(lls.addLast(b));
		assertTrue(lls.getFirstObj() == a);
		assertTrue(b == lls.getLastObj());
		
		assertTrue(lls.containsObj(a));
		assertTrue(lls.containsObj(b));
		assertFalse(lls.containsObj(c));
		
		assertTrue(lls.addLast(c));
		assertFalse(lls.addLast(b));
	}

	@Test
	public void testContainsObj() {
		String str=null;
		boolean npe = false;
		try {
			lls.containsObj(str);
		} catch (NullPointerException e) {
			npe = true;
		}
		assertTrue(npe);
		
		assertTrue(lls.addFirst(a));
		assertTrue(lls.containsObj(a));
		assertFalse(lls.containsObj(b));
	}

	@Test
	public void testRemoveObj() {
		lls.addFirst(a);
		lls.addLast(b);
		lls.addLast(c);
		assertTrue(lls.removeObj(b));
		assertTrue(lls.getSize() == 2);
		assertTrue(lls.removeObj(a));
		assertTrue(lls.getSize() == 1);
		assertFalse(lls.removeObj(b));
		assertTrue(lls.removeObj(c));
		assertTrue(lls.getSize() == 0);
		assertTrue(lls.addFirst(b));//not a
		
		assertFalse(lls.removeObj(a));
		assertTrue(lls.removeObj(b));
		assertTrue(lls.isEmpty());
		assertFalse(lls.removeObj(a));
	
		
		String nul=null;
		boolean npe=false;
		try {
			lls.removeObj(nul);
		}catch (NullPointerException e) {
			npe=true;
		}
		assertTrue(npe);
	}

	@Test
	public void testClear() {
		lls.addFirst(a);
		lls.addLast(b);
		lls.addLast(c);
		assertTrue(lls.getSize() == 3);
		assertTrue(lls.clear());
		assertTrue(lls.isEmpty());
		assertFalse(lls.clear());
	}

	@Test
	public void testGetObjAt() {
		lls.addFirst(a);
		lls.addLast(b);
		lls.addLast(c);
		assertTrue( a == lls.getObjAt(0));
		assertTrue( b == lls.getObjAt(1));
		assertTrue( c == lls.getObjAt(2));
		assertTrue(lls.clear());
		assertTrue(lls.isEmpty());
		boolean ioobe = false;
		try {
			lls.getObjAt(0);
		}catch (IndexOutOfBoundsException e) {
			ioobe = true;
		}
		assertTrue(ioobe);
	}

	@Test
	public void testReplace() {
		boolean ioobe = false;
		try {
			lls.replaceObjAt(0, a);
		}catch (IndexOutOfBoundsException e) {
			ioobe = true;
		}
		assertTrue(ioobe);
		
		lls.addFirst(a);
		lls.addLast(b);
		assertTrue(lls.getObjAt(0) == a);
		assertTrue( a == lls.replaceObjAt(0,c) );
		assertTrue( b == lls.replaceObjAt(1,a) );
		assertTrue( null == lls.replaceObjAt(0, a) ); //again, but fail
		assertTrue( null == lls.replaceObjAt(0, c) );//already exists too
	}

	@Test
	public void testInsertObjAt() {
		assertTrue(lls.isEmpty());
		
		//lls.insertObjAt(a, 0);
		
		String d = "d";
		String e = "e";
		String f = "f";
		
		assertTrue( lls.insertObjAt(b, Location.LAST) );
		assertTrue( lls.insertObjAt(d, Location.AFTER, b) );
		assertTrue( lls.insertObjAt(c, Location.BEFORE, d) );
		assertTrue( lls.insertObjAt(a, Location.FIRST) );
		assertTrue( lls.insertObjAt(e, Location.AFTER, d) );
		assertTrue( lls.insertObjAt(f, Location.LAST) );
		
		String obj = lls.getObjAt(Location.FIRST);
		assertTrue( a == obj);
		obj = lls.getObjAt(Location.AFTER, obj);
		assertTrue( b == obj);
		obj = lls.getObjAt(Location.LAST);
		assertTrue( f == obj);
		assertTrue( e == lls.getObjAt(Location.BEFORE, f));
		assertTrue( c == lls.getObjAt(Location.AFTER, b));
		
		// iterator style
		obj = null;//backward
		while (null != (obj = lls.getObjAt(Location.BEFORE, obj)) ) {
			System.out.print(obj+", ");
		}
		System.out.println();
		
		obj = null;//forward
		while (null != (obj = lls.getObjAt(Location.AFTER, obj)) ) {
			System.out.print(obj+", ");
		}
		System.out.println();
		
	}
	
}
