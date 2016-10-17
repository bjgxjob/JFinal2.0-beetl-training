package net.dreamlu;

import net.dreamlu.base.BaseActiveRecordTest;
import net.dreamlu.model.TUser;

import org.junit.Test;

public class DbTest extends BaseActiveRecordTest {

	@Test
	public void test1() {
		TUser.dao.find("select * from t_user");
	}
}
