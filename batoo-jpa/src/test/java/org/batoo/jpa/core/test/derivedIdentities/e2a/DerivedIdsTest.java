/*
 * Copyright (c) 2012 - Batoo Software ve Consultancy Ltd.
 * 
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.batoo.jpa.core.test.derivedIdentities.e2a;

import javax.persistence.TypedQuery;

import junit.framework.Assert;

import org.batoo.jpa.core.test.BaseCoreTest;
import org.junit.Test;

/**
 * JPA Spec 2.4.1.3 test.
 * 
 * @author asimarslan
 * @since $version
 */
public class DerivedIdsTest extends BaseCoreTest {

	/**
	 * 
	 * Example-2 Case (a):
	 * <p>
	 * The parent entity uses IdClass.
	 * <p>
	 * The dependent entity uses IdClass.
	 * 
	 * @author asimarslan
	 * @since $version
	 */
	@Test
	public void test2a() {
		final Employee employee = new Employee("Sam", "Doe");

		final Dependent dependent1 = new Dependent("Joe", employee);

		this.persist(employee);
		this.persist(dependent1);

		this.commit();
		this.close();

		final TypedQuery<Dependent> q = this.cq("select d FROM Dependent d where d.name = 'Joe' AND d.emp.firstName = 'Sam'", Dependent.class);

		Assert.assertNotNull(q.getResultList());
		Assert.assertEquals(1, q.getResultList().size());

		Assert.assertEquals("Joe", q.getResultList().get(0).getName());
		Assert.assertEquals("Sam", q.getResultList().get(0).getEmp().getFirstName());
		Assert.assertEquals("Doe", q.getResultList().get(0).getEmp().getLastName());
	}
}
