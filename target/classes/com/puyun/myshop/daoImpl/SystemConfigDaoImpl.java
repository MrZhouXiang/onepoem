package com.puyun.myshop.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.puyun.myshop.base.util.Utils;
import com.puyun.myshop.dao.SystemConfigDao;
import com.puyun.myshop.entity.AdminUser;
import com.puyun.myshop.entity.Apk;
import com.puyun.myshop.entity.MadeTypeLvBean;

public class SystemConfigDaoImpl implements SystemConfigDao
{
    private JdbcTemplate jdbcTemplate;
    
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplate;
    }
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
    }
    
    @Override
    public int add(String key, String value)
    {
        String sql = "insert into t_system_config(c_key, c_value) values(?,?)";
        int num = jdbcTemplate.update(sql, key, value);
        return num;
    }
    
    @Override
    public int delete(String key)
    {
        String sql = "delete from t_system_config where c_key=?";
        int num = jdbcTemplate.update(sql, key);
        return num;
    }
    
    @Override
    public int update(String key, String value)
    {
        String sql = "update t_system_config set c_value=? where c_key=?";
        int num = jdbcTemplate.update(sql, value,key);
        return num;
    }
    
    @Override
    public String get(String key)
    {
        String sql = "select c_value from t_system_config where c_key=? order by id desc limit 1";
        String value = jdbcTemplate.queryForObject(sql, new Object[]{key},new RowMapper<String>()
        {

            @Override
            public String mapRow(ResultSet rs, int arg1)
                throws SQLException
            {
                String value = rs.getString("c_value");
                return value;
            }
            
        });
        return value;
    }

    @Override
    public int addKV(String key, String value)
    {
        String sql = "insert into t_kv(k, v) values(?,?)";
        int num = jdbcTemplate.update(sql, key, value);
        return num;
    }

    @Override
    public int deleteKV(String key)
    {
        String sql = "delete from t_kv where k=?";
        int num = jdbcTemplate.update(sql, key);
        return num;
    }

    @Override
    public int updateKV(String key, String value)
    {
        String sql = "update t_kv set v=? where k=?";
        int num = jdbcTemplate.update(sql, value,key);
        return num;
    }

    @Override
    public String getKV(String key)
    {
        String sql = "select v from t_kv where k=? order by id desc limit 1";
        String value = jdbcTemplate.queryForObject(sql, new Object[]{key},new RowMapper<String>()
        {

            @Override
            public String mapRow(ResultSet rs, int arg1)
                throws SQLException
            {
                String value = rs.getString("v");
                return value;
            }
            
        });
        return value;
    }
    
    @Override
    public Apk getApk()
    {
        String sql =
            "select  apkid,url,version_code as versionCode,version_name as versionName,version_desc as versionDesc,forced_update as forcedUpdate "
                + "from t_apk t  order by apkid desc limit 1";
        List<Apk> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Apk>(Apk.class));
        return list.size() > 0 ? list.get(0) : null;
    }
    
    @Override
    public AdminUser doLogin(String username, String password) {
    	// TODO Auto-generated method stub
    	String sql = "select * from laadmin where LoginName=? and Pwd=?";
    	List<AdminUser> list = jdbcTemplate.query(sql, new Object[]{username, password}, new BeanPropertyRowMapper<AdminUser>(AdminUser.class));
    	return list.size() > 0 ? list.get(0) : null;
    }
    
    @Override
    public boolean updateSystemParameter(String platformFee, String buyoutCount, String tradeAmount, String discussCount) {
    	// TODO Auto-generated method stub
    	String platformFeeSql = "UPDATE laconfig SET Content=? WHERE Ename='平台费用' AND Name='platform_fee'";
    	String buyoutCountSql= "UPDATE laconfig SET Content=? WHERE Ename='设计图买断数量' AND Name='buyout_count'";
    	String tradeAmountSql = "UPDATE laconfig SET Content=? WHERE Ename='成衣秀交易额' AND Name='trade_amount'";
    	String discussCountSql = "UPDATE laconfig SET Content=? WHERE Ename='累计评论数量' AND Name='discuss_count'";
    	int num1 = jdbcTemplate.update(platformFeeSql, new Object[]{platformFee});
    	int num2 = jdbcTemplate.update(buyoutCountSql, new Object[]{buyoutCount});
    	int num3 = jdbcTemplate.update(tradeAmountSql, new Object[]{tradeAmount});
    	int num4 = jdbcTemplate.update(discussCountSql, new Object[]{discussCount});
    	return num1 > 0 && num2 > 0 && num3 > 0 && num4 > 0;
    }
    
    @Override
    public Map<String, Object> getSystemParameter() {
    	// TODO Auto-generated method stub
    	Map<String, Object> map = new HashMap<String, Object>();
    	String platformFeeSql = "SELECT Content FROM laconfig WHERE Ename='平台费用' AND Name='platform_fee'";
    	String buyoutCountSql= "SELECT Content FROM laconfig WHERE Ename='设计图买断数量' AND Name='buyout_count'";
    	String tradeAmountSql = "SELECT Content FROM laconfig WHERE Ename='成衣秀交易额' AND Name='trade_amount'";
    	String discussCountSql = "SELECT Content FROM laconfig WHERE Ename='累计评论数量' AND Name='discuss_count'";
    	double platformFee = Double.valueOf(jdbcTemplate.queryForObject(platformFeeSql, String.class));
    	int buyoutCount = jdbcTemplate.queryForObject(buyoutCountSql, Integer.class);
    	double tradeAmount = Double.valueOf(jdbcTemplate.queryForObject(tradeAmountSql, String.class));
    	int discussCount = jdbcTemplate.queryForObject(discussCountSql, Integer.class);
    	map.put("platformFee", platformFee);
    	map.put("buyoutCount", buyoutCount);
    	map.put("tradeAmount", tradeAmount);
    	map.put("discussCount", discussCount);
    	return map;
    }
    
    @Override
    public boolean modifyMadeTypeLv(int id) {
    	// TODO Auto-generated method stub
    	return false;
    }
    
    @Override
    public List<MadeTypeLvBean> getMadeTypeLvBeanList(int start, int num) {
    	// TODO Auto-generated method stub
    	String sql = "SELECT * FROM zhizuolxdj ORDER BY ID ASC limit " + start + "," + num;
    	List<MadeTypeLvBean> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<MadeTypeLvBean>(MadeTypeLvBean.class));
    	return list;
    }
    
    @Override
    public int getListCount() {
    	// TODO Auto-generated method stub
    	String sql = "SELECT COUNT(*) FROM zhizuolxdj";
    	int count = jdbcTemplate.queryForObject(sql, Integer.class);
    	return count;
    }
    
    @Override
    public MadeTypeLvBean getMadeTypeLvDetail(int id) {
    	// TODO Auto-generated method stub
    	String sql = "SELECT * FROM zhizuolxdj WHERE ID=?";
    	List<MadeTypeLvBean> list = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<MadeTypeLvBean>(MadeTypeLvBean.class));
    	return list.size() > 0 ? list.get(0) : null;
    }
    
    @Override
    public boolean updateMadeTypeLv(MadeTypeLvBean lv) {
    	// TODO Auto-generated method stub
    	String sql = "UPDATE zhizuolxdj SET Jishu=:jishu,JingyanMin=:jingyanmin,JingyanMax=:jingyanmax,Jiagongfei=:jiagongfei WHERE ID=:id";
    	int num = namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(lv));
    	return num > 0;
    }
    
    @Override
    public String getOldPwd(String account) {
    	// TODO Auto-generated method stub
    	String sql = " SELECT Pwd FROM laadmin WHERE LoginName=? AND IsLock='0'";
    	String pwd = jdbcTemplate.queryForObject(sql, new Object[]{account},String.class);
    	return pwd;
    }
    
    @Override
    public boolean updatePwd(String account, String newPwd) {
    	// TODO Auto-generated method stub
    	String sql = " UPDATE laadmin SET Pwd=? WHERE LoginName=? ";
    	int num = jdbcTemplate.update(sql, new Object[]{newPwd, account});
    	return num > 0;
    }
    
    @Override
    public List<AdminUser> getManagerList() {
    	// TODO Auto-generated method stub
    	String sql = "SELECT * FROM laadmin WHERE IsLock='0'";
    	List<AdminUser> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<AdminUser>(AdminUser.class));
    	return list;
    }
    
    @Override
    public boolean resetPwd(String loginName) {
    	// TODO Auto-generated method stub
    	String sql = "UPDATE laadmin SET Pwd=? WHERE LoginName=? ";
    	int num = jdbcTemplate.update(sql, new Object[]{Utils.md5Encode("123456"),loginName});
    	return num > 0;
    }
    
    @Override
    public boolean modifyManager(AdminUser user) {
    	// TODO Auto-generated method stub
    	String sql = " UPDATE  laadmin SET LoginName=:loginName,Name=:name,Role=:role WHERE ID=:id";
    	int num = namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
    	return num > 0;
    }
    
    @Override
    public void addManager(AdminUser user) {
    	// TODO Auto-generated method stub
    	user.setPwd(Utils.md5Encode("123456"));
    	String sql = "INSERT INTO laadmin(LoginName,Name,Pwd,IsLock,Addtime,Role) VALUES(:loginName,:name,:pwd,'0',NOW(),'2')";
    	namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
    }
    
    @Override
    public boolean uploadApk(Apk apk) {
    	// TODO Auto-generated method stub
    	String sql = "INSERT INTO t_apk(url,version_code,version_name,version_desc,status,forced_update) VALUES(:url,:versionCode,"
    			+ ":versionName,:versionDesc,'0',:forcedUpdate)";
    	int num = namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(apk));
    	return num > 0;
    }
    
    @Override
    public boolean isManagerExist(String loginName) {
    	// TODO Auto-generated method stub
    	String sql = "SELECT COUNT(*) FROM laadmin WHERE LoginName=? ";
    	int num = jdbcTemplate.queryForObject(sql, new Object[]{loginName}, Integer.class);
    	return num > 0;
    }
    
    @Override
    public boolean deleteManager(int id) {
    	// TODO Auto-generated method stub
    	String sql = "DELETE FROM laadmin WHERE ID=? ";
    	int num = jdbcTemplate.update(sql, new Object[]{id});
    	return num > 0;
    }
    
    @Override
    public List<Map<String,Object>> getSalerIdList() {
    	// TODO Auto-generated method stub
    	String sql = "SELECT YonghuID,Jingyan FROM zhizuojingyan WHERE YonghuLx='shanghu' AND ZhizuoLxID='0'";
    	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
    	return list;
    }
}
