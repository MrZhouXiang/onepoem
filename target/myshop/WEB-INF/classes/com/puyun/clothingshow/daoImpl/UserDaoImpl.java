package com.puyun.clothingshow.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.puyun.clothingshow.dao.UserDao;
import com.puyun.clothingshow.entity.Buyer;
import com.puyun.clothingshow.entity.LVBean;
import com.puyun.clothingshow.entity.Saler;
import com.puyun.clothingshow.entity.UserSize;
import com.puyun.clothingshow.entity.UserType;

/**
 * 用户管理相关数据访问接口实现类
 * 
 * @author  ldz
 * 创建时间:  2015-2-28
 */
public class UserDaoImpl implements UserDao
{
    private JdbcTemplate jdbcTemplate;
    
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate()
    {
        return this.jdbcTemplate;
    }
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
    }
    
    @Override
    public List<Buyer> getBuyerList(String keyword, int start, int num, int status)
    {
        String key = "%" + keyword + "%";
        String sql = "";
        List<Buyer> list = null;
        if(status == -1){
        	sql = " select ID as id,UserName as name,Dengji lv,Jingyan as jingyan,IF(NOW() < unlock_time,1,0) AS status from loginkehu where UserName like ? " +
                    " limit " + start + "," + num;
        	list = jdbcTemplate.query(sql, new Object[] {key}, new BeanPropertyRowMapper<Buyer>(Buyer.class));
        }else{
        	sql = " select ID as id,UserName as name,Dengji lv,Jingyan as jingyan from loginkehu where UserName like ? "
        			+ "AND NOW() < unlock_time "
        			+ " limit " + start + "," + num;
        	list = jdbcTemplate.query(sql, new Object[] {key}, new BeanPropertyRowMapper<Buyer>(Buyer.class));
        	for(Buyer buyer : list){
        		buyer.setStatus(1);
        	}
        }
        return list;
    }
    
    @Override
    public int getBuyerListCount(String keyword)
    {
        String key = "%" + keyword + "%";
        String sql ="select ID,UserName,Dengji,Jingyan,Zhuangtai from loginkehu where UserName like ?  ";
        @SuppressWarnings("deprecation")
        int num = jdbcTemplate.queryForInt("select count(*) from(" + sql + ") t", key);
        return num;
    }
    
    @Override
    public Map<String, Object> getBuyerCount() {
    	// TODO Auto-generated method stub
    	Map<String, Object> map = new HashMap<String, Object>();
    	int allCount = jdbcTemplate.queryForInt("select count(*) from loginkehu");
    	int lockCount = jdbcTemplate.queryForInt("select count(*) from loginkehu where NOW() < unlock_time ");
    	map.put("allCount", allCount);
    	map.put("lockCount", lockCount);
    	return map;
    }
    
    @Override
    public boolean updateBuyerInfo(Buyer buyer) {
    	// TODO Auto-generated method stub
		String sql = "update loginkehu set UserName=:name,Xingbie=:xingbie ,Dengji=:lv,Jingyan=:jingyan,Zhiye=:zhiye,weiboName=:weibo_name "
				+ " ,weixinName=:weixin_name,Zhuangtai=:status,identity=:identity,belong=:belong,province=:province,city=:city,is_international_academy=:is_international_academy,company_type=:company_type "
				+ "  where ID=:id ";
		int num = namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(buyer));
		return num > 0;
    }
    
    @Override
    public Buyer getBuyerInfo(int id) {
    	// TODO Auto-generated method stub
    	String sql = "select ID as id,UserName as name,Xingbie as xingbie,Dengji as lv,Jingyan as jingyan,Quyu as quyu,Zhiye as zhiye,weiboName as weibo_name,"
    			+ "weixinName as weixin_name,IF(NOW() < unlock_time,1,0) AS status,identity,belong,province,city,is_international_academy,company_type from loginkehu where id=?";
    	List<Buyer> list = jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<Buyer>(Buyer.class));
    	return list.size() > 0 ? list.get(0) : null;
    }
    
	@Override
	public List<Saler> getSalerList(String keyword, int start, int num,
			int status) {
		// TODO Auto-generated method stub
		String key = "%" + keyword + "%";
		String sql = "";
		List<Saler> list = null;
		if(status == -1){
			sql = " select ID as id,UserName as name,Dengji as lv,Jingyan as jingyan,IF(NOW() < unlock_time,1,0) AS status,ZhifuZhanghu as zhifuzhanghu,VIPDengji as vipdengji,bank_card_no,issuing_bank from loginshanghu where UserName like ? "
	               + " limit " + start + "," + num;
			list = jdbcTemplate.query(sql, new Object[] {key}, new BeanPropertyRowMapper<Saler>(Saler.class));
		}else{
			sql = " select ID as id,UserName as name,Dengji as lv,Jingyan as jingyan,IF(NOW() < unlock_time,1,0) AS status,ZhifuZhanghu as zhifuzhanghu,VIPDengji as vipdengji,bank_card_no,issuing_bank from loginshanghu where UserName like ? "
	                + " AND NOW() < unlock_time "
					+" limit " + start + "," + num;
			list = jdbcTemplate.query(sql, new Object[] {key}, new BeanPropertyRowMapper<Saler>(Saler.class));
			for(Saler saler : list){
				saler.setStatus(1);
			}
		}
        
        return list;
	}

	@Override
	public int getSalerListCount(String keyword) {
		// TODO Auto-generated method stub
        String key = "%" + keyword + "%";
        String sql ="select ID,UserName,Dengji,Jingyan,Zhuangtai from loginshanghu where UserName like ?  ";
        @SuppressWarnings("deprecation")
        int num = jdbcTemplate.queryForInt("select count(*) from(" + sql + ") t", key);
        return num;
	}

	@Override
	public Map<String, Object> getSalerCount() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
    	int allCount = jdbcTemplate.queryForInt("select count(*) from loginshanghu");
    	int lockCount = jdbcTemplate.queryForInt("select count(*) from loginshanghu where NOW() < unlock_time ");
    	map.put("allCount", allCount);
    	map.put("lockCount", lockCount);
    	return map;
	}

	@Override
	public boolean updateSalerInfo(Saler saler) {
		// TODO Auto-generated method stub
		String sql = "update loginshanghu set UserName=:name,Xingbie=:xingbie ,Dengji=:lv,Jingyan=:jingyan,Quyu=:quyu,Zhiye=:zhiye,weiboName=:weibo_name "
				+ " ,weixinName=:weixin_name,Zhuangtai=:status,ZhifuZhanghu=:zhifuzhanghu,VIPDengji=:vipdengji,bank_card_no=:bank_card_no,issuing_bank=:issuing_bank, "
				+ " identity=:identity,belong=:belong,province=:province,city=:city,is_international_academy=:is_international_academy,company_type=:company_type "
				+ " where ID=:id";
		int num = namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(saler));
		return num > 0;
	}

	@Override
	public Saler getSalerInfo(int id) {
		// TODO Auto-generated method stub
		String sql = "select ID as id,UserName as name,Xingbie as xingbie,Dengji as lv,Jingyan as jingyan,Quyu as quyu,Zhiye as zhiye,weiboName as weibo_name,"
    			+ "weixinName as weixin_name,IF(NOW() < unlock_time,1,0) AS status,ZhifuZhanghu as zhifuzhanghu,VIPDengji as vipdengji,identity,belong,province,city,is_international_academy,company_type,bank_card_no,issuing_bank from loginshanghu where id=?";
    	List<Saler> list = jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<Saler>(Saler.class));
    	return list.size() > 0 ? list.get(0) : null;
	}

    @Override
    public boolean frozenUserForDays(int userId, int days, String userType) {
    	// TODO Auto-generated method stub
    	String sql = "";
    	int num = 0;
    	if(UserType.BUYER.getValue().equals(userType)){
    		sql = "UPDATE loginkehu SET unlock_time=DATE_ADD(NOW(),INTERVAL ? DAY) WHERE ID=?";
    		num = jdbcTemplate.update(sql, new Object[]{days, userId});
    	}else{
    		sql = "UPDATE loginshanghu SET unlock_time=DATE_ADD(NOW(),INTERVAL ? DAY) WHERE ID=?";
    		num = jdbcTemplate.update(sql, new Object[]{days, userId});
    	}
    	return num > 0;
    }
    
    @Override
    public boolean unlockUser(int userId, String userType) {
    	// TODO Auto-generated method stub
    	String sql = "";
    	int num = 0;
    	if(UserType.BUYER.getValue().equals(userType)){
    		sql = "UPDATE loginkehu SET unlock_time=NOW() WHERE ID=?";
    		num = jdbcTemplate.update(sql, new Object[]{userId});
    	}else{
    		sql = "UPDATE loginshanghu SET unlock_time=NOW() WHERE ID=?";
    		num = jdbcTemplate.update(sql, new Object[]{userId});
    	}
    	return num > 0;
    }
    
    @Override
    public int getLoginId(int userId) {
    	// TODO Auto-generated method stub
    	String sql = "SELECT LoginID FROM loginkehu WHERE ID=?";
    	Integer loginId = jdbcTemplate.queryForObject(sql, new Object[]{userId}, Integer.class);
    	return loginId == null ? 0 : loginId;
    }
    
    @Override
    public UserSize getUserSizeDetail(int id) {
    	// TODO Auto-generated method stub
    	String sql = "select ID as id,LoginID as loginId,Name as name,Chima as normal_size,Shengao as height,Tizhong as weight,"
    			+ "Yaowei as waist,Tunwei as hip,Xiongwei as bust,Miaoshu as description from yonghuchima where ID=?";
        List<UserSize> list =
                jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<UserSize>(UserSize.class));
        return list.size() > 0 ? list.get(0) : null;
    }
    
    @Override
    public boolean updateUserSize(UserSize usersize) {
    	// TODO Auto-generated method stub
    	String sql = "UPDATE yonghuchima SET Name=:name,Chima=:normal_size,Shengao=:height,Tizhong=:weight,"
    			+ "Yaowei=:waist,Tunwei=:hip,Xiongwei=:bust,Miaoshu=:description where ID=:id";
    	int num = namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(usersize));
    	return num > 0;
    }
    
    @Override
    public List<LVBean> getUserLvList(int userId) {
    	// TODO Auto-generated method stub
    	String sql1 = "SELECT jy.*,lx.`Name` as ZhizuoLxName FROM zhizuojingyan as jy Left JOIN zhizuoleixing as lx ON jy.ZhizuoLxID = lx.ID WHERE YonghuID = ? AND YonghuLx = 'shanghu' ORDER BY jy.ZhizuoLxID";
        List<LVBean> resultList =
            jdbcTemplate.query(sql1, new Object[] {userId}, new BeanPropertyRowMapper<LVBean>(LVBean.class));
        for(LVBean l : resultList){
        	String sql3 = "SELECT jishu FROM zhizuolxdj WHERE ? >= JingyanMin and ? <= JingyanMax";
        	int lv = jdbcTemplate.queryForObject(sql3, Integer.class, new Object[]{l.getJingyan(), l.getJingyan()});
        	l.setDengji(lv);
        }
        String sql2 = " SELECT ID AS ZhizuoLxID,Name AS ZhizuoLxName "
        					  + " FROM zhizuoleixing "
        					  + " WHERE "
        					  + " ID NOT IN ( "
        					  + " SELECT "
        					  + " ZhizuoLxID "
        					  + " FROM zhizuojingyan "
        					  + " WHERE "
        					  + " YonghuLx = 'shanghu' "
        					  + " AND YonghuID = ?) ";
        List<LVBean> list =
                jdbcTemplate.query(sql2, new Object[] {userId}, new BeanPropertyRowMapper<LVBean>(LVBean.class));
        for(LVBean lv : list){
        	lv.setID("-1");
        	lv.setJingyan(0);
        	lv.setDengji(0);
        }
        resultList.addAll(list);
        return resultList;
    }
    
    @Override
    public LVBean getLvDetail(int id) {
    	// TODO Auto-generated method stub
    	LVBean result = null;
    	String sql = "SELECT jy.*,lx.Name as ZhizuoLxName FROM zhizuojingyan as jy RIGHT JOIN zhizuoleixing as lx ON jy.ZhizuoLxID = lx.ID WHERE jy.ID = ?  ";
        List<LVBean> list =
            jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<LVBean>(LVBean.class));
        if(list != null && list.size() > 0){
        	result = list.get(0);
        	int lv = getLvByExp(result.getJingyan());
        	result.setDengji(lv);
        }
    	return result;
    }
    
    @Override
    public boolean updateLv(LVBean lvBean) {
    	// TODO Auto-generated method stub
    	if("-1".equals(lvBean.getID())){
    		int pk = getPK(lvBean.getYonghuID(), lvBean.getZhizuoLxID());
    		lvBean.setID(String.valueOf(pk));
    	}
    	
    	//查询出该制作类型的原经验
    	String sql1 = "SELECT Jingyan FROM zhizuojingyan WHERE YonghuID=? AND ZhizuoLxID=? AND YonghuLx='shanghu' ";
    	int oldExp = jdbcTemplate.queryForObject(sql1, new Object[]{lvBean.getYonghuID(), lvBean.getZhizuoLxID()}, Integer.class);
    	//改变的经验=现在的经验-旧的经验
    	int changeExp = lvBean.getJingyan() - oldExp;
    	//更新细分经验
    	String sql2 = "UPDATE zhizuojingyan SET Jingyan=:Jingyan,Dengji=:Dengji WHERE ID=:ID";
    	int num1 = namedJdbcTemplate.update(sql2, new BeanPropertySqlParameterSource(lvBean));
    	//更新总经验
    	String sql3 = "UPDATE zhizuojingyan SET Jingyan=Jingyan+? WHERE YonghuID=? AND YonghuLx='shanghu' AND ZhizuoLxID='0' ";
    	int num2 = jdbcTemplate.update(sql3, new Object[]{changeExp,lvBean.getYonghuID()});
    	return num1 > 0 && num2 > 0;
    }
    
    @Override
    public int addLv(LVBean lvBean) {
    	// TODO Auto-generated method stub
    	String countSql = " SELECT COUNT(*) FROM zhizuojingyan WHERE YonghuID=? AND ZhizuoLxID=? ";
    	int num = jdbcTemplate.queryForObject(countSql, new Object[]{lvBean.getYonghuID(), lvBean.getZhizuoLxID()},Integer.class);
    	int pk = 0;
    	if(num == 0){
    		String sql = " INSERT INTO zhizuojingyan(YonghuID,YonghuLx,ZhizuoLxID,Jingyan,Dengji) "
        			+ " VALUES(:YonghuID,'shanghu',:ZhizuoLxID,0,0) ";
            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            this.namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(lvBean), generatedKeyHolder);
            pk = generatedKeyHolder.getKey().intValue();
    	}else{
    		pk = getPK(lvBean.getYonghuID(), lvBean.getZhizuoLxID());
    	}
    	return pk;
    }
    
    @Override
    public int getPK(String userId, String madeTypeId) {
    	// TODO Auto-generated method stub
    	String pkSql = " SELECT ID FROM zhizuojingyan WHERE YonghuID=? AND ZhizuoLxID=? ";
    	Integer pk = jdbcTemplate.queryForObject(pkSql, new Object[]{userId, madeTypeId}, Integer.class);
    	return pk;
    }
    
    @Override
    public int getLvByExp(int exp) {
    	// TODO Auto-generated method stub
    	String sql = "SELECT jishu FROM zhizuolxdj WHERE ? >= JingyanMin and ? <= JingyanMax";
    	List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class, new Object[]{exp, exp});
    	return list.size() > 0 ? list.get(0) : 0;
    }
}
