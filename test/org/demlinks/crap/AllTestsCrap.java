
package org.demlinks.crap;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith( Suite.class )
@Suite.SuiteClasses( value = {
		NodeTest.class, NodeListTest.class, PointerNodeTest.class,
} )
public class AllTestsCrap {
}
