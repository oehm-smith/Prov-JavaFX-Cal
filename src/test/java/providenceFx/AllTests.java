package providenceFx;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import db.csv.CsvTest;
import db.csv.DataBeanTest;
import db.impl.DbTestSearchIndexAlgo;
import db.DbTest;

@RunWith(Suite.class)
@SuiteClasses({CsvTest.class, DataBeanTest.class, DbTest.class, DbTestSearchIndexAlgo.class})
public class AllTests {
}
