package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema( 1, "vienan.app.journey.model");
        // 1: 数据库版本号
        // com.xxx.bean:自动生成的Bean对象会放到/java-gen/com/xxx/bean中

        schema.setDefaultJavaPackageDao("vienan.app.journey.dao");
        // DaoMaster.java、DaoSession.java、BeanDao.java会放到/java-gen/com/xxx/dao中

        // 上面这两个文件夹路径都可以自定义，也可以不设置

        initProvinceBean(schema); // 初始化Bean了
        initCityBean(schema);
        initCountyBean(schema);
        new DaoGenerator().generateAll(schema, args[0]);// 自动创建
    }

    private static void initProvinceBean(Schema schema) {
        Entity provinceBean = schema.addEntity("ProvinceBean");// 表名
        provinceBean.setTableName("province"); // 可以对表重命名
        provinceBean.addIdProperty().primaryKey().index();// 主键，索引
        provinceBean.addStringProperty("province_name");
        provinceBean.addStringProperty("province_code");
    }
    private static void initCityBean(Schema schema) {
        Entity cityBean = schema.addEntity("CityBean");// 表名
        cityBean.setTableName("city"); // 可以对表重命名
        cityBean.addIdProperty().primaryKey().index();// 主键，索引
        cityBean.addStringProperty("city_name");
        cityBean.addStringProperty("city_code");
        cityBean.addIntProperty("province_id");
    }
    private static void initCountyBean(Schema schema) {
        Entity countyBean = schema.addEntity("CountyBean");// 表名
        countyBean.setTableName("county"); // 可以对表重命名
        countyBean.addIdProperty().primaryKey().index();// 主键，索引
        countyBean.addStringProperty("county_name");
        countyBean.addStringProperty("county_code");
        countyBean.addIntProperty("city_id");
    }
}
