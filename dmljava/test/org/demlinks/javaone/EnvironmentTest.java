/*  Copyright (C) 2005-2008 AtKaaZ <atkaaz@sourceforge.net>
 	
 	This file and its contents are part of DeMLinks.

    DeMLinks is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DeMLinks is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DeMLinks.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.demlinks.javaone;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class EnvironmentTest {

	@Test
	public void testLink() {
		Environment a = new Environment();
		assertTrue(null == a.getNode("C"));
		a.link("A", "B");
		assertTrue(a.isLink("A","B"));
		assertTrue(a.size() == 2);
		Node _par = a.getNode("A");
		Node _chi = a.getNode("B");
		assertTrue(_chi.isLinkFrom(_par));
		assertTrue(_par.isLinkTo(_chi));
		
		a.unlink("A","B");
		assertTrue(0 == _chi.getChildrenListSize());
		assertTrue(0 == _chi.getParentsListSize());
		assertTrue(_chi.isDead());
		assertTrue(null == a.getNode("B"));
		assertTrue(null == a.getNode("A"));
		
		a.link("A", "B");
		a.link("B", "C");
		assertTrue(a.isLink("A", "B"));
		assertTrue(a.isLink("B", "C"));
		assertFalse(a.isLink("A", "C"));
		assertFalse(a.isLink("B", "A"));
		assertFalse(a.isLink("C", "B"));
		a.unlink("A", "B");
		assertFalse(a.isLink("A", "B"));
		assertTrue(a.isLink("B", "C"));
		assertTrue(a.getID(a.getNode("B")).equals("B"));
		assertTrue(a.getID(a.getNode("C")).equals("C"));
		assertTrue(null == a.getNode("A"));
		
		a.link("AllWords", "dood");
		a.link("dood", "d");
		a.link("dood", "o");
		a.link("dood", "o"); // already exists hehe, no DUPs supported like that
		a.link("dood", "d"); // same here
		assertTrue(2 == a.getNode("dood").getChildrenListSize());
		
		a.link("AllWords", "DOOD");
		a.link("DOOD", "RND_2180");
		a.link("DOOD", "RND_7521");
		a.link("DOOD", "RND_1288");
		a.link("DOOD", "RND_1129");
		a.link("RND_2180", "D");
		a.link("RND_7521", "O");
		a.link("RND_1288", "O");
		a.link("RND_1129", "D");
		assertTrue( a.getNode("DOOD").getChildrenListSize() == 4 );
		Iterator<Node> itr = a.getNode("DOOD").getChildrenListIterator();
		while (itr.hasNext()) {
			Node cur = itr.next();
			System.out.print("DOOD->"+a.getID(cur));
			Iterator<Node> citr = cur.getChildrenListIterator();
			while (citr.hasNext()) {
				Node ccur = citr.next();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("->");
				stringBuilder.append(a.getID(ccur));
				System.out.println(stringBuilder.toString());
			}
		}
	}

}
